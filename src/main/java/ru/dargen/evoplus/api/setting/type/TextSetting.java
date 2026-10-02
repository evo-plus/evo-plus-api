package ru.dargen.evoplus.api.setting.type;

import ru.dargen.evoplus.api.setting.Setting;

import java.util.function.Predicate;

/** Строка в поле ввода. */
public interface TextSetting extends Setting<String, TextSetting> {

    /** Подсказка в пустом поле. Ключ локализации или готовая строка. */
    TextSetting placeholder(String placeholder);

    /** Предел длины; по умолчанию 256. */
    TextSetting maxLength(int maxLength);

    /**
     * Проверка значения. Пока введённое не проходит её, поле подсвечено красным,
     * а у настройки остаётся последнее подходящее значение.
     */
    TextSetting validator(Predicate<String> validator);

}
