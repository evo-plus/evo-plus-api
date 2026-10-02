package ru.dargen.evoplus.api.setting.type;

import ru.dargen.evoplus.api.setting.SettingElement;

/**
 * Виджет аддона — элемент HUD, который игрок включает в настройках аддона и расставляет
 * в редакторе виджетов EvoPlus вместе с виджетами самого мода. В настройках у него строка
 * с переключателем; включённость, позиция и масштаб хранятся в json аддона.
 * <p>
 * Что рисовать, решает {@link ru.dargen.evoplus.api.render.WidgetRenderer} аддона.
 */
public interface WidgetElement extends SettingElement<WidgetElement> {

    double getWidth();

    double getHeight();

    /**
     * Размер виджета: по нему редактор рисует рамку, ловит мышь и не даёт вытащить виджет
     * за край экрана. Можно менять на ходу — например, под длину текста.
     */
    WidgetElement size(double width, double height);

    /** Показан ли виджет на экране. */
    boolean isEnabled();

    void setEnabled(boolean enabled);

}
