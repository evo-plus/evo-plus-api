package ru.dargen.evoplus.api.ui;

/** Прокручиваемая колонка фиксированного размера. Дети кладутся в {@link #getContent()}. */
public interface UiScroll extends UiElement<UiScroll> {

    UiContainer getContent();

    /** К началу списка. */
    UiScroll scrollToTop();

    /** К концу списка — например, к последнему сообщению. Срабатывает и для только что добавленных детей. */
    UiScroll scrollToBottom();

    /** Долистано ли до конца (с допуском в пару пикселей). */
    boolean isAtBottom();

}
