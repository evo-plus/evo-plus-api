package ru.dargen.evoplus.api.addon;

import ru.dargen.evoplus.api.setting.AddonSettings;

/**
 * Мод, подключённый к EvoPlus. Идентифицируется по id мода из fabric.mod.json:
 * по нему EvoPlus находит аддон в меню аддонов и называет файл его настроек.
 */
public interface Addon {

    String getId();

    /** Имя мода из fabric.mod.json (или id, если мода с таким id нет). */
    String getName();

    String getVersion();

    /**
     * Настройки аддона. Как только в них появляется хоть одна категория, в меню аддонов
     * EvoPlus у карточки аддона появляется кнопка «Настройки».
     */
    AddonSettings getSettings();

}
