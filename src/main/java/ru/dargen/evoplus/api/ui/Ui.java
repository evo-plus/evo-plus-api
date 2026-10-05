package ru.dargen.evoplus.api.ui;

import ru.dargen.evoplus.api.data.Notification;
import ru.dargen.evoplus.api.render.WidgetRenderer;
import ru.dargen.evoplus.api.setting.SettingContainer;
import ru.dargen.evoplus.api.ui.theme.Theme;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Стандартные элементы интерфейса EvoPlus: окна, диалоги, кнопки, поля, списки. Из них
 * аддон собирает свои меню и модалки, и они выглядят так же, как окна самого мода, —
 * включая скругление и шрифт, которые игрок выбрал в настройках.
 * <p>
 * Берётся у аддона: {@link ru.dargen.evoplus.api.addon.Addon#getUi()}. Вызывать — с
 * клиентского потока.
 * <pre>
 * UiWindow window = ui.window("Привет", 300, 200, true, true);
 * window.getContent().add(ui.text("Окно аддона"));
 * window.getFooter().add(ui.button("Закрыть", ButtonStyle.SECONDARY, window::close));
 * window.open();
 * </pre>
 * Текст везде — ключ локализации или готовая строка, как у настроек.
 */
public interface Ui {

    /** Палитра темы, которую выбрал игрок. Тему меняют на ходу — не кешируйте цвета надолго. */
    UiTheme getTheme();

    /** Id выбранной темы: {@code graphite} и другие встроенные или {@code <id мода>:<id>}. */
    String getThemeId();

    /**
     * Добавляет тему в список тем меню «Интерфейс». Тема, сохранённая выбранной, включается,
     * как только её зарегистрируют. Повторная регистрация с тем же id заменяет тему.
     */
    void registerTheme(Theme theme);

    /**
     * Обработчик смены темы — на клиентском потоке, сразу после смены. Стандартные элементы
     * перекрашиваются сами, в том числе в окнах, которые аддон держит и открывает повторно;
     * а то, что аддон собрал из смешанных цветов ({@code blend} палитры и т.п.), стоит
     * пересобрать здесь.
     */
    void onThemeChange(Runnable listener);

    // ── окна ───────────────────────────────────────────────────────────────────────

    /**
     * Окно не больше {@code maxWidth x maxHeight} (на маленьком экране — меньше).
     *
     * @param scrollable тело — прокручиваемая колонка, а окно по высоте содержимого;
     *                   иначе тело — свободная область фиксированного размера (две панели,
     *                   свои прокрутки)
     * @param footer     подвал под кнопки
     */
    UiWindow window(String title, double maxWidth, double maxHeight, boolean scrollable, boolean footer);

    /** Прокручиваемое окно без подвала. */
    default UiWindow window(String title, double maxWidth, double maxHeight) {
        return window(title, maxWidth, maxHeight, true, false);
    }

    UiDialog dialog();

    /** Контекстное меню; пункты добавляются цепочкой, затем {@link UiMenu#open()}. */
    UiMenu menu();

    /** Кладёт текст в буфер обмена. */
    void copyToClipboard(String text);

    /**
     * Окно со строками настроек контейнера — тех же, что в меню настроек. Так настройки,
     * которым не место во вкладке, открываются своей модалкой (см. {@link SettingContainer#hidden}).
     * Значения сохраняются при закрытии окна.
     */
    UiWindow settingsWindow(String title, SettingContainer container);

    /** Уведомление в углу экрана, как у самого EvoPlus. */
    void notify(Notification notification);

    // ── контейнеры ─────────────────────────────────────────────────────────────────

    UiContainer column();

    UiContainer row();

    /** Свободная область фиксированного размера: дети по {@link UiElement#anchor} и позиции. */
    UiContainer stack(double width, double height);

    /** Панель: колонка на подложке с рамкой. */
    UiContainer panel();

    /** Карточка списка: подсвечивается при наведении, если {@code hoverable}. */
    UiContainer card(boolean hoverable);

    UiScroll scroll(double width, double height);

    // ── содержимое ─────────────────────────────────────────────────────────────────

    default UiText text(String text) {
        return text(text, TextStyle.BODY);
    }

    UiText text(String text, TextStyle style);

    UiButton button(String label, ButtonStyle style, Runnable action);

    default UiButton button(String label, Runnable action) {
        return button(label, ButtonStyle.SECONDARY, action);
    }

    /** Квадратная кнопка-иконка без подложки; подложка проявляется при наведении. */
    UiButton iconButton(String texture, double size, String tooltip, Runnable action);

    UiToggle toggle(boolean value);

    UiInput input(String value, String placeholder);

    UiSlider slider(double value, double min, double max, double step, int decimals);

    <T> UiSelect<T> select(List<T> options, T value, Function<T, String> names);

    UiImage image(String texture, double width, double height);

    UiHead head(String player, double size);

    /** @param itemStack {@code net.minecraft.world.item.ItemStack} */
    UiItem item(Object itemStack);

    UiBadge badge(int count);

    /** Своя отрисовка размером {@code width x height}; рендерер зовётся каждый кадр. */
    UiCanvas canvas(double width, double height, WidgetRenderer renderer);

    /** Горизонтальная линия во всю ширину родителя. */
    UiBlock divider();

    /** Заголовок секции капсом с линией до правого края — как в меню настроек. */
    UiBlock sectionHeader(String label);

    /** Пустое место. */
    UiBlock spacer(double width, double height);

    // ── эмодзи ─────────────────────────────────────────────────────────────────────

    /**
     * Эмодзи сервера: символ рисуется шрифтом ресурспака DiamondWorld. Список грузится в фоне
     * при запуске игры — до загрузки он пуст.
     */
    List<Emoji> getEmojis();

    /**
     * Символы эмодзи в тексте — заменить ключами {@code :id:}. Сервер понимает ключи, поэтому
     * так текст отправляют в чат и в команды (обычный чат EvoPlus заменяет сам).
     */
    String encodeEmojis(String text);

    /** Стандартная сетка эмодзи, как у чата EvoPlus; клик по эмодзи — {@code onPick}. */
    UiContainer emojiPicker(Consumer<Emoji> onPick);

}
