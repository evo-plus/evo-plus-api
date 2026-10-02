package ru.dargen.evoplus.api.setting;

import java.util.List;

/**
 * Категория — вкладка экрана настроек. Внутри может держать секции: на экране они идут
 * под настройками самой категории, каждая под своим заголовком.
 */
public interface SettingCategory extends SettingContainer {

    /** Секция по id: существующая или новая. */
    SettingSection section(String id, String name);

    List<SettingSection> getSections();

}
