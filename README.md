# evo-plus-api

API для модов, которые хотят встроиться в [EvoPlus](https://modrinth.com/mod/evoplus): свои
настройки в меню EvoPlus, свои виджеты HUD в его редакторе виджетов, уведомления и данные
о сервере.

Реализацию отдаёт сам EvoPlus в рантайме, поэтому API подключается как `compileOnly`, а вся
интеграция держится за точкой входа `evo-plus`: без EvoPlus её никто не вызовет, классы API
не загрузятся, и мод работает как обычно.

## Подключение

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("com.github.evo-plus:evo-plus-api:1.2.0")
}
```

`fabric.mod.json` аддона:

```json
"entrypoints": {
  "evo-plus": [ "my.addon.MyEvoPlusAddon" ]
},
"suggests": {
  "evo-plus": "*"
}
```

```kotlin
object MyEvoPlusAddon : EvoPlusAddon {
    override fun onInitialize(addon: Addon) {
        // регистрируем настройки и виджеты
    }
}
```

EvoPlus зовёт точку входа один раз, на клиентском потоке, когда клиент уже запущен:
инициализаторы всех модов отработали, язык загружен. Аддон привязан к id мода, который
объявил точку входа. Достать его в другом месте — `EvoPlusApi.getAddon(modId)`.

## Настройки

```kotlin
val settings = addon.settings
val general = settings.category("general", "mymod.settings.general")

val enabled = general.toggle("enabled", "mymod.settings.enabled", true)
    .description("mymod.settings.enabled.description")
    .onChange { println("enabled = $it") }

val radius = general.intSlider("radius", "mymod.settings.radius", 8, 1, 32)
val color = general.color("color", "mymod.settings.color", 0xFF55FFFF.toInt()).alpha(true)
val key = general.key("key", "mymod.settings.key", KeyBind.keyboard(GLFW.GLFW_KEY_G))
    .onPress { /* нажали сочетание */ }

general.section("advanced", "mymod.settings.advanced").apply {
    text("prefix", "mymod.settings.prefix", "!").maxLength(8)
    enumSelect("mode", "mymod.settings.mode", Mode.FAST)
}
```

- Категория — вкладка экрана настроек аддона, секция — группа под заголовком внутри неё.
- Имена и описания — ключи локализации или готовые строки.
- id уникален внутри контейнера и служит ключом в json. Значения лежат в
  `evo-plus/addons/<id мода>.json` в папке игры и читаются сразу при создании настройки.
- Типы: `toggle`, `intSlider`, `intField`, `decimalSlider`, `text`, `enumSelect`, `choice`,
  `color`, `key`, `button`, `widget`.
- Свой конфиг у аддона уже есть — `bind(getter, setter)`: EvoPlus только показывает значение
  и пишет его обратно, в свой json ничего не кладёт.
- `settings.onSave { }` зовётся, когда игрок закрыл экран настроек.

Как только в настройках появляется категория, у карточки аддона в меню «Аддоны» EvoPlus
появляется кнопка «Настройки».

## Виджеты

Виджет — элемент HUD, который игрок включает в настройках аддона и расставляет в редакторе
виджетов EvoPlus вместе с виджетами самого мода: двигает мышью, масштабирует колесом.
Включённость, позиция и масштаб хранятся в json аддона.

```kotlin
val clock = general.widget("clock", "mymod.widget.clock", 80.0, 20.0) { ctx ->
    ctx.rect(0.0, 0.0, ctx.width, ctx.height, 0xA0000000.toInt())
    ctx.text(LocalTime.now().toString(), 4.0, 6.0, -1, true)
}

clock.size(120.0, 20.0)   // размер можно менять на ходу
clock.isEnabled = true    // показать без участия игрока
```

Рендерер зовётся каждый кадр, пока виджет на экране: в HUD, в редакторе и в его списке.
Рисовать нужно в координатах виджета — от `(0, 0)` до `ctx.width x ctx.height`. Позицию на
экране и масштаб игрока EvoPlus применяет сам. Исключение из рендерера не роняет кадр: оно
пишется в лог аддона, один раз на каждую новую ошибку.

### Почему через контекст, а не через ванильный GuiGraphics

Рендер EvoPlus не ванильный. За кадр он собирает команды своих нод, сортирует их по слою
и глубине и только потом отдаёт игре одним проходом. Отрисованное мимо контекста не попадёт
под позицию и масштаб виджета и разъедется с остальным интерфейсом по порядку наложения:
окажется под модалками, над тултипами и так далее.

### RenderContext

| Метод | Что делает |
|---|---|
| `getWidth()`, `getHeight()` | размер виджета |
| `getTickDelta()` | доля между тиками, для плавной анимации |
| `isEditing()` | открыт редактор виджетов. Пустому виджету стоит рисовать заглушку, иначе игрок не увидит, что двигает |
| `push()` / `pop()` | сохранить и вернуть трансформацию |
| `translate(x, y)`, `scale(x, y)`, `rotate(degrees)` | трансформации; поворот по часовой стрелке |
| `scissor(x, y, w, h)` / `popScissor()` | обрезка; вложенная пересекается с внешней |
| `rect(x, y, w, h, argb)` | залитый прямоугольник |
| `outline(x, y, w, h, thickness, argb)` | рамка внутрь прямоугольника |
| `text(text, x, y, argb, shadow)` | строка шрифтом игры, понимает коды `§` |
| `textWidth(text)`, `lineHeight()` | метрики шрифта |
| `texture(id, x, y, w, h, …)` | текстура или её область, например `"mymod:textures/gui/icon.png"` |
| `item(itemStack, x, y)` | предмет 16x16, как в инвентаре |
| `vanilla(action)` | ванильная отрисовка, см. ниже |

Цвета — ARGB: `0xFFFFFFFF` — непрозрачный белый, с нулевой альфой ничего не видно.
Наложение идёт в порядке вызовов. Незакрытые `push()` и `scissor()` EvoPlus закрывает сам
после рендерера. Сохранять контекст и звать его вне рендерера нельзя: он живёт один кадр.

### Своя отрисовка: vanilla

Для того, чего нет в примитивах, — `vanilla`. Действие получает ванильный
`GuiGraphicsExtractor`, у которого `pose()` уже выставлен в трансформацию виджета, а клип —
в текущий `scissor`. Вызов встаёт в общую очередь кадра на то место, где сделан, поэтому
порядок наложения с примитивами сохраняется.

```kotlin
ctx.vanilla<GuiGraphicsExtractor> { graphics ->
    graphics.fill(0, 0, 10, 10, 0xFFFF0000.toInt())
}
```

```java
context.<GuiGraphicsExtractor>vanilla(graphics -> graphics.fill(0, 0, 10, 10, 0xFFFF0000));
```

Действие выполняется не сразу, а позже в этом же кадре. Значения, которые меняются по ходу
рендерера, забирайте в локальные переменные заранее. Трансформации ванильного стека внутри
действия верните как было. Ванильная `pose()` двумерная: поворот виджета вокруг оси экрана
переносится, повороты вокруг X и Y — нет.

## Прочее

- `EvoPlusApi.showNotification(Notification)` — уведомление EvoPlus с действием по клику.
- `EvoPlusApi.getServer()`, `EvoPlusApi.getLocation()` — текущий сервер и локация.

## Версии

| Версия | Что нового |
|---|---|
| 1.2.0 | виджеты аддонов, `RenderContext` |
| 1.1.0 | аддоны: точка входа `evo-plus`, настройки |
| 1.0.x | уведомления, сервер и локация |
