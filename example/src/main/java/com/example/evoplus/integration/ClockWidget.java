package com.example.evoplus.integration;

import com.example.evoplus.ExampleConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import ru.dargen.evoplus.api.render.RenderContext;
import ru.dargen.evoplus.api.render.WidgetRenderer;
import ru.dargen.evoplus.api.setting.SettingContainer;
import ru.dargen.evoplus.api.setting.type.WidgetElement;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Виджет HUD: часы, счётчик кликов и полоска FPS. Показывает почти всё, что умеет
 * {@link RenderContext}: фон и рамку, текст, предмет, обрезку, поворот, ванильный вызов
 * и размер под содержимое.
 * <p>
 * Игрок включает виджет в настройках аддона и двигает его в редакторе виджетов EvoPlus.
 * Позицию и масштаб EvoPlus применяет сам — рисуем в координатах виджета.
 */
final class ClockWidget implements WidgetRenderer {

    private static final double PADDING = 4;
    private static final double ICON = 16;

    private final WidgetElement element;

    // ItemStack нельзя создать до захода на сервер (компоненты предметов ещё не привязаны),
    // поэтому создаём его при первом удачном рендере, а не в поле.
    private ItemStack icon;

    private ClockWidget(SettingContainer container) {
        element = container.widget("clock", "example.widget.clock", 100, 30, this);
        element.description("example.widget.clock.description");
    }

    static WidgetElement register(SettingContainer container) {
        return new ClockWidget(container).element;
    }

    @Override
    public void render(RenderContext ctx) {
        boolean active = ExampleEvoPlusAddon.enabled.get();
        // Выключенный аддон в HUD ничего не рисует, но в редакторе виджет должно быть видно.
        if (!active && !ctx.isEditing()) return;

        int accent = ExampleEvoPlusAddon.accent.get();
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern(ExampleEvoPlusAddon.mode.get().pattern));
        String clicks = "§7Кликов: §f" + ExampleConfig.get().clicks;

        // Размер под содержимое. Применится со следующего кадра, поэтому ниже рисуем
        // по ctx.getWidth()/getHeight(), а не по только что посчитанному.
        double textWidth = Math.max(ctx.textWidth(time), ctx.textWidth(clicks));
        element.size(PADDING * 3 + ICON + textWidth, PADDING * 2 + ctx.lineHeight() * 2 + 6);

        double width = ctx.getWidth();
        double height = ctx.getHeight();

        ctx.rect(0, 0, width, height, 0xB0101018);
        ctx.outline(0, 0, width, height, 1, ctx.isEditing() ? 0xFFFFAA00 : accent);

        if (icon == null) icon = tryCreateIcon();
        if (icon != null) ctx.item(icon, PADDING, (height - ICON) / 2);

        double textX = PADDING * 2 + ICON;
        ctx.text(time, textX, PADDING, accent, true);
        ctx.text(clicks, textX, PADDING + ctx.lineHeight() + 2, 0xFFFFFFFF, false);

        // Индикатор в углу: поворот вокруг своего центра.
        ctx.push();
        ctx.translate(width - 5, 5);
        ctx.rotate((System.currentTimeMillis() % 3600) / 10.0);
        ctx.rect(-2, -2, 4, 4, accent);
        ctx.pop();

        // Полоска FPS по низу: обрезаем по рамке, а рисуем ванильным fill.
        // Значение забираем заранее — действие выполнится позже в этом же кадре.
        int fps = Minecraft.getInstance().getFps();
        int barWidth = (int) Math.min(width - 2, fps / 2.0);
        ctx.scissor(1, height - 3, width - 2, 2);
        ctx.<GuiGraphicsExtractor>vanilla(graphics -> graphics.fill(1, (int) height - 3, 1 + barWidth, (int) height - 1, 0xFF55FF55));
        ctx.popScissor();

        if (!active) ctx.rect(0, 0, width, height, 0x80000000);
    }

    private static ItemStack tryCreateIcon() {
        try {
            return new ItemStack(Items.CLOCK);
        } catch (RuntimeException notInWorldYet) {
            return null;
        }
    }

}
