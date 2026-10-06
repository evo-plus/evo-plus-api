package ru.dargen.evoplus.api.setting.type;

import ru.dargen.evoplus.api.setting.SettingElement;

/** Строка настроек со своим элементом управления — см. {@link ru.dargen.evoplus.api.setting.SettingContainer#custom}. */
public interface CustomElement extends SettingElement<CustomElement> {

    /**
     * Строку можно закрепить: у неё появляется кнопка-булавка, и закреплённая строка встаёт
     * над списком настроек вкладки и не уезжает при прокрутке. Для превью, которое должно
     * быть видно, пока двигаешь настройки ниже. По умолчанию нельзя.
     */
    CustomElement pinnable(boolean pinnable);

    boolean isPinnable();

}
