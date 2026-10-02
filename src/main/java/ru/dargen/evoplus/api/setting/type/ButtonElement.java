package ru.dargen.evoplus.api.setting.type;

import ru.dargen.evoplus.api.setting.SettingElement;

/** Кнопка с действием — например, открыть свой экран для того, что не ложится в настройки. */
public interface ButtonElement extends SettingElement<ButtonElement> {

    String getLabel();

    ButtonElement label(String label);

}
