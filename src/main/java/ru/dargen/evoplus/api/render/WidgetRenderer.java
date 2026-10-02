package ru.dargen.evoplus.api.render;

/**
 * Отрисовка виджета аддона. Зовётся каждый кадр, пока виджет на экране: в HUD, в редакторе
 * виджетов и в его списке. Рисовать нужно в координатах виджета — от {@code (0, 0)} до
 * {@link RenderContext#getWidth()} x {@link RenderContext#getHeight()}: позицию на экране
 * и масштаб, выставленные игроком в редакторе, EvoPlus применяет сам.
 * <p>
 * Исключение из рендерера не роняет кадр: EvoPlus пишет его в лог аддона и рисует дальше.
 */
@FunctionalInterface
public interface WidgetRenderer {

    void render(RenderContext context);

}
