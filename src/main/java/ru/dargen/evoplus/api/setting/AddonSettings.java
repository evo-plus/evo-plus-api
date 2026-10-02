package ru.dargen.evoplus.api.setting;

import java.nio.file.Path;
import java.util.List;

/**
 * Настройки аддона: набор категорий (вкладок экрана настроек) с секциями внутри.
 * <p>
 * Значения хранятся в отдельном json-файле аддона ({@link #getFile()}). Значение настройки
 * читается из файла сразу при её создании, так что после регистрации {@link Setting#get()}
 * уже отдаёт сохранённое. Настройки с {@link Setting#bind внешним хранилищем} в файл не пишутся.
 * <p>
 * Регистрировать настройки и открывать экран нужно с клиентского потока.
 */
public interface AddonSettings {

    /** Категория по id: существующая или новая. Имя — ключ локализации или готовая строка. */
    SettingCategory category(String id, String name);

    List<SettingCategory> getCategories();

    /** Открывает экран настроек аддона. */
    void open();

    /** Сохраняет значения в файл и зовёт обработчики {@link #onSave}. */
    void save();

    /**
     * Обработчик сохранения: зовётся на клиентском потоке, когда игрок закрыл экран
     * настроек аддона или аддон сам вызвал {@link #save()}.
     */
    AddonSettings onSave(Runnable handler);

    /** Json-файл со значениями настроек аддона. */
    Path getFile();

}
