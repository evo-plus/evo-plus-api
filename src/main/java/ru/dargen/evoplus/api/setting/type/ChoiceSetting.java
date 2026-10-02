package ru.dargen.evoplus.api.setting.type;

import ru.dargen.evoplus.api.setting.Setting;

import java.util.List;
import java.util.function.Function;

/**
 * Выбор строки из списка. Список спрашивается при каждом показе, поэтому может меняться
 * (например, звуки из ресурспаков). Сохранённое значение, которого в списке уже нет,
 * не сбрасывается — оно просто не выделено.
 */
public interface ChoiceSetting extends Setting<String, ChoiceSetting> {

    List<String> getOptions();

    /** Подпись варианта: ключ локализации или готовая строка. По умолчанию — сама строка. */
    ChoiceSetting names(Function<String, String> names);

}
