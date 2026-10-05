package ru.dargen.evoplus.api.ui;

/** Стандартная кнопка. Без явной ширины — по подписи. */
public interface UiButton extends UiElement<UiButton> {

    String getLabel();

    UiButton label(String label);

    UiButton style(ButtonStyle style);

    /** Выключенная кнопка серая и не нажимается. */
    UiButton enabled(boolean enabled);

    boolean isEnabled();

}
