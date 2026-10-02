package ru.dargen.evoplus.api.setting;

/** Секция внутри категории: группа настроек под общим заголовком. */
public interface SettingSection extends SettingContainer {

    SettingCategory getCategory();

}
