package ru.dargen.evoplus.api.setting.type;

import ru.dargen.evoplus.api.setting.Setting;

/** Целое число в диапазоне [{@link #getMin()}, {@link #getMax()}]: ползунком или полем ввода. */
public interface IntSetting extends Setting<Integer, IntSetting> {

    int getMin();

    int getMax();

    default int getInt() {
        return get();
    }

}
