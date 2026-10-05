package ru.dargen.evoplus.api.addon;

import ru.dargen.evoplus.api.setting.AddonSettings;
import ru.dargen.evoplus.api.ui.Ui;

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

    /**
     * Стандартные элементы интерфейса EvoPlus — для своих окон, меню и модалок. Ошибки в
     * обработчиках элементов пишутся в лог этого аддона.
     */
    Ui getUi();

}
