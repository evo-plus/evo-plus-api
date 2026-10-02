package ru.dargen.evoplus.api.setting;

import java.util.function.BooleanSupplier;

/**
 * Строка экрана настроек: имя, описание под ним и элемент управления справа.
 *
 * @param <S> собственный тип — чтобы цепочки вызовов не теряли его
 */
public interface SettingElement<S extends SettingElement<S>> {

    String getId();

    String getName();

    String getDescription();

    /** Описание под именем. Ключ локализации или готовая строка; длинное переносится по словам. */
    S description(String description);

    /** Показывать ли строку. Спрашивается при каждом показе экрана и при поиске. */
    S visible(BooleanSupplier visible);

}
