package ru.dargen.evoplus.api.ui;

import java.util.List;

/**
 * Контейнер: колонка ({@link Ui#column}), ряд ({@link Ui#row}) или свободная область
 * ({@link Ui#stack}). Колонка и ряд по умолчанию подстраивают размер под содержимое; размер,
 * заданный явно (или {@link #fillWidth()}), фиксирует его по этой оси.
 */
public interface UiContainer extends UiElement<UiContainer> {

    UiContainer add(UiElement<?>... children);

    /** Добавляет ребёнка и отдаёт его же — удобно сразу сохранить в переменную. */
    <T extends UiElement<?>> T append(T child);

    UiContainer remove(UiElement<?> child);

    UiContainer clear();

    List<UiElement<?>> getChildren();

    /** Расстояние между детьми ряда или колонки. */
    UiContainer spacing(double spacing);

    /** Внутренние поля по горизонтали и вертикали. */
    UiContainer padding(double horizontal, double vertical);

    /** Выравнивание детей поперёк раскладки: в колонке — по горизонтали, в ряду — по вертикали. */
    UiContainer align(Align align);

}
