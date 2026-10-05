package ru.dargen.evoplus.api.ui;

/**
 * Точка привязки элемента внутри свободной области ({@link Ui#stack}, тело окна без прокрутки):
 * угол или сторона родителя, к которой прижат элемент, и одновременно точка самого элемента.
 * {@link UiElement#position} — сдвиг от этой точки.
 */
public enum Anchor {
    TOP_LEFT, TOP, TOP_RIGHT,
    LEFT, CENTER, RIGHT,
    BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT
}
