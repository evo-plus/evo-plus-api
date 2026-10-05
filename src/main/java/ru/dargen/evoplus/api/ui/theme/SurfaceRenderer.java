package ru.dargen.evoplus.api.ui.theme;

import ru.dargen.evoplus.api.render.RenderContext;

/**
 * Отрисовка подложки темой. Зовётся каждый кадр на каждую подложку интерфейса — на
 * клиентском потоке, внутри кадра, поэтому быстрой и без выделений на каждый вызов.
 * <p>
 * Контекст стоит в левом верхнем углу подложки, размер — {@link Surface#getWidth()} x
 * {@link Surface#getHeight()}. Рисовать можно и за его пределами (тень), но содержимое
 * подложки ляжет поверх — оно рисуется после.
 * <p>
 * Ошибка в рендерере выключает его до перезахода: интерфейс возвращается к обычной отрисовке.
 */
@FunctionalInterface
public interface SurfaceRenderer {

    /**
     * @return true — подложка нарисована; false — пусть EvoPlus нарисует её сам, как без темы
     */
    boolean render(RenderContext context, Surface surface);

}
