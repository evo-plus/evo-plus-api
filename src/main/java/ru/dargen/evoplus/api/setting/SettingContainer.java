package ru.dargen.evoplus.api.setting;

import ru.dargen.evoplus.api.render.WidgetRenderer;
import ru.dargen.evoplus.api.setting.type.BooleanSetting;
import ru.dargen.evoplus.api.setting.type.ButtonElement;
import ru.dargen.evoplus.api.setting.type.ChoiceSetting;
import ru.dargen.evoplus.api.setting.type.ColorSetting;
import ru.dargen.evoplus.api.setting.type.DecimalSetting;
import ru.dargen.evoplus.api.setting.type.EnumSetting;
import ru.dargen.evoplus.api.setting.type.IntSetting;
import ru.dargen.evoplus.api.setting.type.KeyBind;
import ru.dargen.evoplus.api.setting.type.KeySetting;
import ru.dargen.evoplus.api.setting.type.TextSetting;
import ru.dargen.evoplus.api.setting.type.WidgetElement;

import java.util.List;
import java.util.function.Supplier;

/**
 * Всё, во что можно класть настройки: категория и её секции.
 * <p>
 * id настройки уникален в пределах контейнера и служит ключом в json, имя — ключ
 * локализации или готовая строка. Повторная регистрация того же id бросает
 * {@link IllegalArgumentException}.
 */
public interface SettingContainer {

    String getId();

    String getName();

    List<SettingElement<?>> getElements();

    /** Переключатель. */
    BooleanSetting toggle(String id, String name, boolean value);

    /** Целое число ползунком с шагом 1. */
    default IntSetting intSlider(String id, String name, int value, int min, int max) {
        return intSlider(id, name, value, min, max, 1);
    }

    /** Целое число ползунком. */
    IntSetting intSlider(String id, String name, int value, int min, int max, int step);

    /** Целое число полем ввода: для широких диапазонов, где ползунок неудобен. */
    IntSetting intField(String id, String name, int value, int min, int max);

    /** Дробное число ползунком; {@code decimals} — знаков после запятой в подписи. */
    DecimalSetting decimalSlider(String id, String name, double value, double min, double max, double step, int decimals);

    /** Строка. */
    TextSetting text(String id, String name, String value);

    /** Выбор из констант перечисления. Набор констант берётся из класса {@code value}. */
    <E extends Enum<E>> EnumSetting<E> enumSelect(String id, String name, E value);

    /** Выбор строки из списка; список спрашивается при каждом показе. */
    ChoiceSetting choice(String id, String name, String value, Supplier<List<String>> options);

    /** Цвет в ARGB. */
    ColorSetting color(String id, String name, int argb);

    /** Клавиша или кнопка мыши, с модификаторами. */
    KeySetting key(String id, String name, KeyBind value);

    /** Кнопка с действием. Значения не хранит. */
    ButtonElement button(String id, String name, String label, Runnable action);

    /**
     * Виджет HUD размером {@code width x height}: строка с переключателем в настройках и
     * элемент в редакторе виджетов EvoPlus. По умолчанию выключен и стоит в левом верхнем углу.
     */
    WidgetElement widget(String id, String name, double width, double height, WidgetRenderer renderer);

}
