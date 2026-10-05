package ru.dargen.evoplus.api.ui;

/** Предмет как в инвентаре: 16x16, масштаб — через {@link #size}. */
public interface UiItem extends UiElement<UiItem> {

    /** @param itemStack {@code net.minecraft.world.item.ItemStack} */
    UiItem item(Object itemStack);

}
