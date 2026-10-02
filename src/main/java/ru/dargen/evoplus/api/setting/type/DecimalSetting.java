package ru.dargen.evoplus.api.setting.type;

import ru.dargen.evoplus.api.setting.Setting;

/** Дробное число ползунком. */
public interface DecimalSetting extends Setting<Double, DecimalSetting> {

    double getMin();

    double getMax();

    double getStep();

    default double getDouble() {
        return get();
    }

    default float getFloat() {
        return get().floatValue();
    }

}
