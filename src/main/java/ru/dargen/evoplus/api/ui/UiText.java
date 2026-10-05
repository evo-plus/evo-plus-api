package ru.dargen.evoplus.api.ui;

/** Текст шрифтом интерфейса EvoPlus. Понимает коды {@code §} и переводы строк. */
public interface UiText extends UiElement<UiText> {

    String getText();

    UiText text(String text);

    UiText style(TextStyle style);

    /** Цвет текста без кода цвета (ARGB). */
    UiText color(int argb);

    UiText shadow(boolean shadow);

    UiText scale(double scale);

    /** Строки по центру друг под другом. */
    UiText centered(boolean centered);

    /**
     * Предел ширины: длинный текст переносится по словам ({@code wrap}) или обрезается
     * многоточием. 0 — без предела.
     */
    UiText maxWidth(double width, boolean wrap);

}
