package ru.dargen.evoplus.api.ui.theme;

import ru.dargen.evoplus.api.ui.UiTheme;

/**
 * Тема интерфейса EvoPlus: палитра и, по желанию, своя отрисовка подложек. Игрок выбирает
 * тему в меню «Интерфейс»; регистрирует её аддон через
 * {@link ru.dargen.evoplus.api.ui.Ui#registerTheme(Theme)}.
 * <p>
 * Палитра — те же токены, что отдаёт {@link ru.dargen.evoplus.api.ui.Ui#getTheme()}: ими
 * красится всё — окна, кнопки, текст, виджеты. Цвета могут быть полупрозрачными: окна мода
 * открываются поверх размытого фона.
 * <pre>
 * ui.registerTheme(new Theme() {
 *     public String getId() { return "sunset"; }
 *     public String getName() { return "Закат"; }
 *     public UiTheme getPalette() { return palette; }
 * });
 * </pre>
 */
public interface Theme {

    /** Id темы внутри аддона; в настройках она хранится как {@code <id мода>:<id>}. */
    String getId();

    /** Название в списке тем — ключ локализации или готовая строка. */
    String getName();

    UiTheme getPalette();

    /**
     * Радиус скругления углов, который тема задаёт сама. Отрицательный — решает игрок
     * (переключатель «Скругление»); иначе переключатель скрыт, а радиус — этот.
     */
    default double getCornerRadius() {
        return -1;
    }

    /**
     * Своя отрисовка подложек — окон, кнопок, карточек, полей. null — подложки рисуются как
     * обычно, цветами палитры.
     */
    default SurfaceRenderer getSurfaceRenderer() {
        return null;
    }

}
