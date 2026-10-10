package ru.dargen.evoplus.api.ui.theme;

/** Подложка, которую рисует {@link SurfaceRenderer}: что это и каким его хотел нарисовать мод. */
public interface Surface {

    /** Угол: верхний левый. Маски — в {@link #getCorners()}. */
    int TOP_LEFT = 1;
    int TOP_RIGHT = 2;
    int BOTTOM_RIGHT = 4;
    int BOTTOM_LEFT = 8;
    int ALL_CORNERS = 15;

    SurfaceKind getKind();

    double getWidth();

    double getHeight();

    /** Заливка, которую выбрал мод (уже из палитры темы), ARGB. Прозрачная — заливки нет. */
    int getFill();

    /** Цвет обводки, ARGB; прозрачный — обводки нет. */
    int getOutline();

    double getOutlineWidth();

    /**
     * Обводка без заливки: рамку кладут поверх уже нарисованной подложки (рамки окон-диалогов).
     * Тема, которая рамку рисует вместе с подложкой, такую можно пропустить.
     */
    boolean isOutlineOnly();

    /** Радиус скругления по теме и настройке игрока; 0 — углы прямые. */
    double getCornerRadius();

    /**
     * Какие углы скруглять — маска из {@link #TOP_LEFT} и соседей. Подложки, которые
     * стыкуются с соседями (сайдбар и содержимое окна), скругляют только внешние углы.
     */
    int getCorners();

    /** Курсор над подложкой. */
    boolean isHovered();

    /**
     * Виджет HUD под подложкой — его id из конфига: `boss-timer/widget`, `rune/active-runes-widget`,
     * у виджетов аддонов `<modId>:<категория>/<id>`. Так тема рисует фон отдельного виджета
     * по-своему. У остальных подложек — null.
     */
    String getWidgetId();

}
