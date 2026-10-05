package ru.dargen.evoplus.api.ui;

import java.util.function.Consumer;

/**
 * Элемент интерфейса EvoPlus. Создаётся через {@link Ui}, кладётся в контейнер
 * ({@link UiContainer#add}) и живёт, пока лежит в открытом окне.
 * <p>
 * Размеры — в единицах интерфейса EvoPlus: окно само применяет масштаб «Размер интерфейса».
 * Ряды и колонки раскладывают детей сами; {@link #position} и {@link #anchor} нужны в
 * свободных областях ({@link Ui#stack}, тело окна без прокрутки), в раскладке позиция — лишь
 * сдвиг от места, которое выдала раскладка.
 * <p>
 * Работать с элементами можно только с клиентского потока. Исключения из обработчиков
 * аддона EvoPlus ловит и пишет в лог аддона.
 *
 * @param <E> собственный тип — чтобы цепочки вызовов не теряли его
 */
public interface UiElement<E extends UiElement<E>> {

    double getWidth();

    double getHeight();

    E size(double width, double height);

    E width(double width);

    E height(double height);

    /**
     * Ширина — доля ширины родителя плюс сдвиг: {@code widthRelative(1, -20)} — во всю
     * ширину без 20. В ряду или колонке берётся место внутри её отступов.
     */
    E widthRelative(double fraction, double offset);

    /** Высота — доля высоты родителя плюс сдвиг. */
    E heightRelative(double fraction, double offset);

    /** Во всю ширину родителя. */
    default E fillWidth() {
        return widthRelative(1, 0);
    }

    /** Во всю высоту родителя. */
    default E fillHeight() {
        return heightRelative(1, 0);
    }

    E position(double x, double y);

    E anchor(Anchor anchor);

    /** Подсказка при наведении, по строке на аргумент. Без аргументов — убрать. */
    E tooltip(String... lines);

    /** Скрытый элемент не рисуется, не ловит мышь и не занимает места в раскладке. */
    E visible(boolean visible);

    boolean isVisible();

    boolean isHovered();

    /** Заливка под элементом (ARGB); 0 — без заливки. */
    E background(int argb);

    /** Рамка по краю элемента (ARGB); 0 — без рамки. */
    E outline(int argb);

    /** Клик левой кнопкой. У кнопок — нажатие, если она не выключена. */
    E onClick(Runnable action);

    /** Клик правой кнопкой. */
    E onRightClick(Runnable action);

    /** Курсор навели ({@code true}) или увели ({@code false}). */
    E onHover(Consumer<Boolean> handler);

    /** Каждый тик игры, пока элемент в открытом окне. */
    E onTick(Runnable action);

    /** Убирает элемент из контейнера. */
    void remove();

}
