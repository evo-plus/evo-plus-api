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

    /**
     * Фон под виджетом, пока игрок не выбрал сам (в окне настроек виджета). По умолчанию фона
     * нет. Задавайте сразу при создании виджета, до того как игрок его увидит.
     *
     * @since 1.8.0
     */
    default WidgetElement defaultBackground(boolean background) {
        return this;
    }

    /**
     * Можно ли двигать виджет мышью в открытом чате, пока игрок не выбрал сам. По умолчанию
     * можно; выключите виджету, по которому в чате кликают, — иначе перетаскивание забирает
     * клик. Задавайте сразу при создании виджета.
     *
     * @since 1.8.0
     */
    default WidgetElement chatMovable(boolean movable) {
        return this;
    }

}
