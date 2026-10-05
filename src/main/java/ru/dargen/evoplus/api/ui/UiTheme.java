package ru.dargen.evoplus.api.ui;

/**
 * Палитра интерфейса EvoPlus (ARGB): активной темы ({@link Ui#getTheme()}) — для того, что
 * аддон рисует сам ({@link Ui#canvas}), чтобы оно не отличалось от стандартных элементов, —
 * или своей темы ({@link ru.dargen.evoplus.api.ui.theme.Theme#getPalette()}). Поверхности —
 * от самой тёмной (подложка окна) к самой светлой (наведение).
 */
public interface UiTheme {

    int surface0();

    int surface1();

    int surface2();

    int surface3();

    int surface4();

    int border();

    int borderStrong();

    /** Обводка интерактивного элемента под курсором. */
    default int borderHover() {
        return borderStrong();
    }

    /** Обводка выбранного и элемента в фокусе. */
    default int accentBorder() {
        return accent();
    }

    int accent();

    int accentHover();

    int textPrimary();

    int textSecondary();

    int textMuted();

    int success();

    int danger();

}
