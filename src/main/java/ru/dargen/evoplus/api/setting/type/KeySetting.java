package ru.dargen.evoplus.api.setting.type;

import ru.dargen.evoplus.api.setting.Setting;

/**
 * Клавиша или кнопка мыши с модификаторами. На экране настроек: клик по кнопке и нажатие
 * нужного сочетания, Esc — снять привязку.
 */
public interface KeySetting extends Setting<KeyBind, KeySetting> {

    /** Можно ли привязывать сочетания с Ctrl/Shift/Alt; по умолчанию можно. */
    KeySetting modifiers(boolean modifiers);

    /** Зажато ли сочетание прямо сейчас. */
    boolean isPressed();

    /**
     * Обработчик нажатия: зовётся на клиентском потоке один раз на нажатие, пока не открыт
     * ни один экран (как обычные бинды игры).
     */
    KeySetting onPress(Runnable handler);

}
