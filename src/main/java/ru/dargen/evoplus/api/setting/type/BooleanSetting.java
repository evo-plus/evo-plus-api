package ru.dargen.evoplus.api.setting.type;

import ru.dargen.evoplus.api.setting.Setting;

/** Переключатель. */
public interface BooleanSetting extends Setting<Boolean, BooleanSetting> {

    default boolean isEnabled() {
        return get();
    }

}
