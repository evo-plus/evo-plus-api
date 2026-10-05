package ru.dargen.evoplus.api.render;

import java.util.function.Consumer;

/**
 * Отрисовка внутри виджета аддона — и подложки темы ({@link ru.dargen.evoplus.api.ui.theme.SurfaceRenderer}).
 * <p>
 * Рендер EvoPlus не ванильный: за кадр он собирает команды, сортирует их по слою и глубине
 * и только потом отдаёт игре одним проходом. Поэтому рисовать нужно через этот контекст, а не
 * напрямую через ванильный {@code GuiGraphicsExtractor} из своего HUD-колбэка, — иначе
 * отрисованное не попадёт под масштаб и позицию виджета и разъедется с остальным интерфейсом
 * по порядку наложения. Для ванильных вызовов есть {@link #vanilla(Consumer)}.
 * <p>
 * Координаты — в единицах GUI относительно левого верхнего угла виджета. Цвета — ARGB
 * ({@code 0xFFFFFFFF} — непрозрачный белый; с нулевой альфой ничего не видно). Наложение —
 * в порядке вызовов: что нарисовано позже, то сверху.
 * <p>
 * Контекст живёт один кадр: сохранять его и звать вне {@link WidgetRenderer#render} нельзя.
 * Незакрытые {@link #push()} и {@link #scissor} EvoPlus закрывает сам после рендерера.
 */
public interface RenderContext {

    /** Ширина виджета. */
    double getWidth();

    /** Высота виджета. */
    double getHeight();

    /** Доля между прошлым и текущим тиком, для плавной анимации. */
    float getTickDelta();

    /**
     * Открыт ли редактор виджетов. Виджет, которому сейчас нечего показать, в редакторе
     * стоит всё равно рисовать — хотя бы заглушкой, иначе игрок не увидит, что двигает.
     */
    boolean isEditing();

    // ── трансформации ──────────────────────────────────────────────────────────────

    /** Запоминает текущую трансформацию; вернуть — {@link #pop()}. */
    void push();

    void pop();

    void translate(double x, double y);

    void scale(double x, double y);

    /** Поворот вокруг оси экрана, в градусах по часовой стрелке. */
    void rotate(double degrees);

    // ── обрезка ────────────────────────────────────────────────────────────────────

    /**
     * Обрезает всё последующее по прямоугольнику (в текущей трансформации; поворот не
     * учитывается). Вложенная обрезка пересекается с внешней. Снять — {@link #popScissor()}.
     */
    void scissor(double x, double y, double width, double height);

    void popScissor();

    // ── примитивы ──────────────────────────────────────────────────────────────────

    /** Залитый прямоугольник. */
    void rect(double x, double y, double width, double height, int argb);

    /** Рамка внутрь прямоугольника толщиной {@code thickness}. */
    void outline(double x, double y, double width, double height, double thickness, int argb);

    /**
     * Прямоугольник со скруглёнными углами (сглаженный край). Радиус ужимается до половины
     * меньшей стороны.
     *
     * @param corners какие углы скруглять — маска {@link ru.dargen.evoplus.api.ui.theme.Surface#TOP_LEFT}
     *                и соседей; остальные прямые
     */
    void roundedRect(double x, double y, double width, double height, double radius, int argb, int corners);

    default void roundedRect(double x, double y, double width, double height, double radius, int argb) {
        roundedRect(x, y, width, height, radius, argb, 15);
    }

    /** Скруглённая рамка внутрь прямоугольника толщиной {@code thickness}, без заливки. */
    void roundedOutline(double x, double y, double width, double height, double radius, double thickness, int argb, int corners);

    default void roundedOutline(double x, double y, double width, double height, double radius, double thickness, int argb) {
        roundedOutline(x, y, width, height, radius, thickness, argb, 15);
    }

    /** Скруглённая рамка с вертикальным градиентом: {@code top} у верхнего края, {@code bottom} у нижнего. */
    void roundedOutline(
            double x, double y, double width, double height, double radius, double thickness,
            int top, int bottom, int corners
    );

    /** Вертикальный градиент: цвет {@code top} у верхнего края переходит в {@code bottom} у нижнего. */
    void gradient(double x, double y, double width, double height, int top, int bottom);

    /** Скруглённый вертикальный градиент. */
    void roundedGradient(double x, double y, double width, double height, double radius, int top, int bottom, int corners);

    /**
     * Мягкая тень вокруг скруглённого прямоугольника: темнее у края, к {@code spread} сходит
     * на нет. Под самим прямоугольником тень не рисуется — она не просвечивает сквозь
     * полупрозрачную подложку.
     */
    void shadow(double x, double y, double width, double height, double radius, double spread, int argb);

    /**
     * Строка шрифтом игры. Понимает коды форматирования {@code §}; {@code argb} — цвет
     * текста без кода цвета. Перевод строк не поддерживается — по строке на вызов.
     */
    void text(String text, double x, double y, int argb, boolean shadow);

    /** Ширина строки шрифтом игры, коды {@code §} не считаются. */
    int textWidth(String text);

    /** Высота строки шрифта игры (9). */
    int lineHeight();

    /**
     * Текстура целиком, растянутая на прямоугольник.
     *
     * @param texture идентификатор текстуры, например {@code "mymod:textures/gui/icon.png"};
     *                незагруженная текстура не рисуется
     */
    default void texture(String texture, double x, double y, double width, double height) {
        texture(texture, x, y, width, height, 0, 0, 1, 1, 1, 1, 0xFFFFFFFF);
    }

    /**
     * Область текстуры {@code (u, v, regionWidth, regionHeight)} из текстуры размером
     * {@code textureWidth x textureHeight}, растянутая на прямоугольник и умноженная на цвет.
     */
    void texture(
            String texture, double x, double y, double width, double height,
            double u, double v, double regionWidth, double regionHeight,
            int textureWidth, int textureHeight, int argb
    );

    /**
     * Предмет так же, как в инвентаре: 16x16, с эффектами зачарования. Размер меняется
     * через {@link #scale}.
     *
     * @param itemStack {@code net.minecraft.world.item.ItemStack}; API не зависит от
     *                  Minecraft, поэтому тип здесь {@code Object}
     */
    void item(Object itemStack, double x, double y);

    /**
     * Ванильная отрисовка: {@code action} получает {@code GuiGraphicsExtractor} игры, у
     * которого {@code pose()} уже выставлен в текущую трансформацию виджета, а клип — в
     * текущий {@link #scissor}. Вызов встаёт в общую очередь кадра на место, где он
     * сделан, так что порядок наложения с примитивами сохраняется.
     * <p>
     * {@code action} выполняется не сразу, а позже в этом же кадре: значения, которые
     * меняются по ходу рендерера, нужно забрать в локальные переменные заранее.
     * Трансформации ванильного стека внутри {@code action} нужно вернуть как было.
     * <pre>
     * context.&lt;GuiGraphicsExtractor&gt;vanilla(graphics -&gt; graphics.fill(0, 0, 10, 10, 0xFFFF0000));
     * </pre>
     *
     * @param <G> {@code net.minecraft.client.gui.GuiGraphicsExtractor}
     */
    <G> void vanilla(Consumer<G> action);

}
