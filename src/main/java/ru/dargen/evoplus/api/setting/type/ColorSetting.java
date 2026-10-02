package ru.dargen.evoplus.api.setting.type;

import ru.dargen.evoplus.api.setting.Setting;

/** Цвет в ARGB. Выбирается палитрой с ползунками каналов или вводом hex. */
public interface ColorSetting extends Setting<Integer, ColorSetting> {

    /** Можно ли менять прозрачность; по умолчанию можно. Без неё альфа всегда 255. */
    ColorSetting alpha(boolean alpha);

    boolean hasAlpha();

    default int getArgb() {
        return get();
    }

    default int getRgb() {
        return get() & 0xFFFFFF;
    }

}
