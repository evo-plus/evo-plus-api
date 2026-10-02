# evo-plus-api

API для модов, которые хотят встроиться в [EvoPlus](https://modrinth.com/mod/evoplus): свои
настройки в меню EvoPlus, свои виджеты HUD в его редакторе виджетов, уведомления и данные
о сервере.

Реализацию отдаёт сам EvoPlus в рантайме, поэтому API подключается как `compileOnly`, а вся
интеграция держится за точкой входа `evo-plus`: без EvoPlus её никто не вызовет, классы API
не загрузятся, и мод работает как обычно.

API написан на Java и не зависит от Minecraft — примеры ниже тоже на Java. Из Kotlin всё
работает так же.

Готовый мод-пример со всем основным — настройками, клавишей, уведомлением и виджетом — лежит
в [`example/`](example).

## Подключение

`build.gradle`:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.evo-plus:evo-plus-api:1.2.0'
}
```

`build.gradle.kts`:

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("com.github.evo-plus:evo-plus-api:1.2.0")
}
```

Именно `compileOnly`: классы API в рантайме отдаёт EvoPlus, своя копия в jar аддона
разойдётся с ними по версии.

## Точка входа

`fabric.mod.json` аддона:

```json
"entrypoints": {
  "evo-plus": [ "my.addon.evoplus.MyEvoPlusAddon" ]
},
"suggests": {
  "evo-plus": "*"
}
```

```java
package my.addon.evoplus;

import ru.dargen.evoplus.api.addon.Addon;
import ru.dargen.evoplus.api.addon.EvoPlusAddon;

public class MyEvoPlusAddon implements EvoPlusAddon {

    @Override
    public void onInitialize(Addon addon) {
        // регистрируем настройки и виджеты
    }

}
```

EvoPlus зовёт точку входа один раз, на клиентском потоке, когда клиент уже запущен:
инициализаторы всех модов отработали, язык загружен. Аддон привязан к id мода, который
объявил точку входа. Достать его в другом месте — `EvoPlusApi.getAddon(modId)`.

Держите всё, что касается EvoPlus, в отдельном пакете и трогайте его только из точки входа:
тогда без EvoPlus ни один класс API не загрузится и мод не упадёт с `NoClassDefFoundError`.

## Настройки

```java
AddonSettings settings = addon.getSettings();
SettingCategory general = settings.category("general", "mymod.settings.general");

BooleanSetting enabled = general.toggle("enabled", "mymod.settings.enabled", true)
        .description("mymod.settings.enabled.description")
        .onChange(value -> System.out.println("enabled = " + value));

IntSetting radius = general.intSlider("radius", "mymod.settings.radius", 8, 1, 32);
ColorSetting color = general.color("color", "mymod.settings.color", 0xFF55FFFF).alpha(true);

KeySetting key = general.key("key", "mymod.settings.key", KeyBind.keyboard(GLFW.GLFW_KEY_G))
        .onPress(() -> System.out.println("pressed"));

SettingSection advanced = general.section("advanced", "mymod.settings.advanced");
advanced.text("prefix", "mymod.settings.prefix", "!").maxLength(8);
advanced.enumSelect("mode", "mymod.settings.mode", Mode.FAST);

// Значение читается в любой момент — уже сохранённое, если оно было.
if (enabled.get()) {
    int r = radius.get();
}
```

- Категория — вкладка экрана настроек аддона, секция — группа под заголовком внутри неё.
- Имена и описания — ключи локализации или готовые строки.
- id уникален внутри контейнера и служит ключом в json. Повторная регистрация того же id
  бросает `IllegalArgumentException`. Значения лежат в `evo-plus/addons/<id мода>.json` в
  папке игры и читаются сразу при создании настройки.
- Регистрировать настройки и открывать экран нужно с клиентского потока.

Как только в настройках появляется категория, у карточки аддона в меню «Аддоны» EvoPlus
появляется кнопка «Настройки». Открыть экран самому — `settings.open()`.

### Типы

| Метод | Тип | Значение |
|---|---|---|
| `toggle(id, name, value)` | `BooleanSetting` | переключатель |
| `intSlider(id, name, value, min, max[, step])` | `IntSetting` | целое ползунком |
| `intField(id, name, value, min, max)` | `IntSetting` | целое полем ввода, для широких диапазонов |
| `decimalSlider(id, name, value, min, max, step, decimals)` | `DecimalSetting` | дробное ползунком; `decimals` — знаков в подписи |
| `text(id, name, value)` | `TextSetting` | строка: `placeholder`, `maxLength`, `validator` |
| `enumSelect(id, name, value)` | `EnumSetting<E>` | константа перечисления; подписи — `names(e -> …)` |
| `choice(id, name, value, options)` | `ChoiceSetting` | строка из списка; список спрашивается при каждом показе |
| `color(id, name, argb)` | `ColorSetting` | цвет ARGB; `alpha(true)` — с прозрачностью |
| `key(id, name, keyBind)` | `KeySetting` | клавиша или кнопка мыши с модификаторами; `onPress`, `isPressed` |
| `button(id, name, label, action)` | `ButtonElement` | кнопка, значения не хранит |
| `widget(id, name, width, height, renderer)` | `WidgetElement` | виджет HUD, см. [Виджеты](#виджеты) |

Общее для всех строк экрана:

- `description(text)` — описание под именем, длинное переносится по словам.
- `visible(() -> …)` — показывать ли строку; спрашивается при каждом показе и при поиске.

Общее для настроек со значением (`get`, `set`, `getDefault`, `reset`):

- `onChange(value -> …)` — обработчик изменения, их может быть несколько.
- `set` приводит неподходящее значение к допустимому или отбрасывает его.

### Свой конфиг

Если у аддона уже есть свой конфиг, свяжите настройку с ним через `bind`. EvoPlus тогда
только показывает значение и пишет его обратно, в свой json ничего не кладёт:

```java
general.toggle("compact", "mymod.settings.compact", false)
        .bind(() -> MyConfig.get().compact, value -> MyConfig.get().compact = value);

settings.onSave(MyConfig::save);
```

`onSave` зовётся на клиентском потоке, когда игрок закрыл экран настроек аддона или аддон
сам вызвал `settings.save()`.

### Клавиши

```java
general.key("toggle-hud", "mymod.settings.toggle-hud", KeyBind.keyboard(GLFW.GLFW_KEY_H, KeyBind.CONTROL))
        .onPress(MyHud::toggle);
```

`onPress` срабатывает один раз на нажатие и не срабатывает, пока открыт экран, — как бинды
самой игры. `modifiers(false)` убирает модификаторы из выбора клавиши.

## Виджеты

Виджет — элемент HUD, который игрок включает в настройках аддона и расставляет в редакторе
виджетов EvoPlus вместе с виджетами самого мода: двигает мышью, масштабирует колесом.
В настройках у виджета строка с переключателем. Включённость, позиция и масштаб хранятся в
json аддона. По умолчанию виджет выключен и стоит в левом верхнем углу.

```java
private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");

WidgetElement clock = general.widget("clock", "mymod.widget.clock", 80, 20, ctx -> {
    ctx.rect(0, 0, ctx.getWidth(), ctx.getHeight(), 0xA0000000);
    ctx.text(LocalTime.now().format(TIME), 4, 6, 0xFFFFFFFF, true);
});

clock.size(120, 20);     // размер можно менять на ходу
clock.setEnabled(true);  // показать без участия игрока
```

Рендерер зовётся каждый кадр, пока виджет на экране: в HUD, в редакторе и в его списке.
Рисовать нужно в координатах виджета — от `(0, 0)` до `getWidth() x getHeight()`. Позицию на
экране и масштаб игрока EvoPlus применяет сам. Исключение из рендерера не роняет кадр: оно
пишется в лог аддона, один раз на каждую новую ошибку.

### Размер под содержимое

`size` можно звать прямо из рендерера: редактор держит на месте угол, к которому привязан
виджет, а рамка и зона захвата едут за размером. Новый размер применяется со следующего
кадра, поэтому фон рисуйте по `ctx.getWidth()`, а не по только что посчитанному значению:

```java
public final class StatusWidget implements WidgetRenderer {

    private WidgetElement element;

    public void register(SettingContainer container) {
        element = container.widget("status", "mymod.widget.status", 60, 20, this);
    }

    @Override
    public void render(RenderContext ctx) {
        List<String> lines = Status.lines();
        int width = 0;
        for (String line : lines) width = Math.max(width, ctx.textWidth(line));
        double step = ctx.lineHeight() + 2;
        element.size(width + 8, lines.size() * step + 6);

        ctx.rect(0, 0, ctx.getWidth(), ctx.getHeight(), 0xB0101018);
        for (int i = 0; i < lines.size(); i++) {
            ctx.text(lines.get(i), 4, 4 + i * step, 0xFFFFFFFF, true);
        }
    }

}
```

### Почему через контекст, а не через ванильный GuiGraphics

Рендер EvoPlus не ванильный. За кадр он собирает команды своих нод, сортирует их по слою
и глубине и только потом отдаёт игре одним проходом. Отрисованное мимо контекста — например,
из своего `HudElementRegistry`-колбэка — не попадёт под позицию и масштаб виджета и
разъедется с остальным интерфейсом по порядку наложения: окажется под модалками, над
тултипами и так далее.

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
| `texture(id, x, y, w, h)` | текстура целиком, например `"mymod:textures/gui/icon.png"` |
| `texture(id, x, y, w, h, u, v, regionW, regionH, texW, texH, argb)` | область текстуры, умноженная на цвет |
| `item(itemStack, x, y)` | предмет 16x16, как в инвентаре |
| `vanilla(action)` | ванильная отрисовка, см. ниже |

Цвета — ARGB: `0xFFFFFFFF` — непрозрачный белый, с нулевой альфой ничего не видно.
Наложение идёт в порядке вызовов. Незакрытые `push()` и `scissor()` EvoPlus закрывает сам
после рендерера. Сохранять контекст и звать его вне рендерера нельзя: он живёт один кадр.

```java
ctx -> {
    // Вращающийся квадрат в правом верхнем углу.
    ctx.push();
    ctx.translate(ctx.getWidth() - 10, 10);
    ctx.rotate((System.currentTimeMillis() % 3600) / 10.0);
    ctx.rect(-5, -5, 10, 10, 0xFF55FF55);
    ctx.pop();

    // Полоса прогресса: всё, что вылезает за обрезку, не рисуется.
    ctx.scissor(4, 14, ctx.getWidth() - 8, 4);
    ctx.rect(4, 14, (ctx.getWidth() - 8) * progress, 4, 0xFF55AAFF);
    ctx.popScissor();
}
```

### Предметы

`item` принимает `net.minecraft.world.item.ItemStack`: API не зависит от Minecraft, поэтому
в сигнатуре `Object`. Компоненты предметов в 26.2 привязываются только при заходе на сервер —
`new ItemStack(...)` раньше этого бросает исключение. Не создавайте предметы в статических
полях и конструкторах, создавайте их при первом рендере:

```java
private ItemStack icon;

@Override
public void render(RenderContext ctx) {
    if (icon == null) {
        try {
            icon = new ItemStack(Items.DIAMOND);
        } catch (RuntimeException notInWorldYet) {
            // ещё не на сервере — попробуем в следующем кадре
        }
    }
    if (icon != null) ctx.item(icon, 4, 4);
}
```

### Своя отрисовка: vanilla

Для того, чего нет в примитивах, — `vanilla`. Действие получает ванильный
`GuiGraphicsExtractor`, у которого `pose()` уже выставлен в трансформацию виджета, а клип —
в текущий `scissor`. Вызов встаёт в общую очередь кадра на то место, где сделан, поэтому
порядок наложения с примитивами сохраняется.

```java
int fps = Minecraft.getInstance().getFps();
ctx.<GuiGraphicsExtractor>vanilla(graphics -> graphics.fill(0, 0, Math.min(fps, 100), 4, 0xFFFF5555));
```

- Действие выполняется не сразу, а позже в этом же кадре. Значения, которые меняются по ходу
  рендерера, забирайте в локальные переменные заранее, как `fps` выше.
- Трансформации и обрезку ванильного стека внутри действия верните как было.
- Ванильная `pose()` двумерная: поворот виджета вокруг оси экрана переносится, повороты
  вокруг X и Y — нет.
- Исключение из действия так же уходит в лог аддона и не роняет кадр.

## Прочее

```java
EvoPlusApi.showNotification(Notification.builder()
        .title("MyMod")
        .message("Готово")
        .action(() -> System.out.println("clicked"))
        .duration(Duration.ofSeconds(3))
        .build());

Server server = EvoPlusApi.getServer();      // текущий сервер
Location location = EvoPlusApi.getLocation(); // текущая локация
```

## Версии

| Версия | Что нового |
|---|---|
| 1.2.0 | виджеты аддонов, `RenderContext` |
| 1.1.0 | аддоны: точка входа `evo-plus`, настройки |
| 1.0.x | уведомления, сервер и локация |
