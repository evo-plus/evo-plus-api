package ru.dargen.evoplus.api.ui;

/** Текстура целиком, растянутая на размер элемента. */
public interface UiImage extends UiElement<UiImage> {

    /** Идентификатор текстуры, например {@code "mymod:textures/gui/icon.png"}. */
    UiImage texture(String texture);

    /** Цвет, на который умножается текстура (ARGB). */
    UiImage color(int argb);

}
