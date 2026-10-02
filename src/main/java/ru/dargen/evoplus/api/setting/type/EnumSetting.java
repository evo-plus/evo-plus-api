package ru.dargen.evoplus.api.setting.type;

import ru.dargen.evoplus.api.setting.Setting;

import java.util.List;
import java.util.function.Function;

/** Выбор из констант перечисления. В json хранится имя константы. */
public interface EnumSetting<E extends Enum<E>> extends Setting<E, EnumSetting<E>> {

    List<E> getValues();

    /** Подпись константы: ключ локализации или готовая строка. По умолчанию — имя константы. */
    EnumSetting<E> names(Function<E, String> names);

}
