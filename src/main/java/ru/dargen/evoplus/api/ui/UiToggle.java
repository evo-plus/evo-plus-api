package ru.dargen.evoplus.api.ui;

import java.util.function.Consumer;

/** Переключатель, как у настроек. */
public interface UiToggle extends UiElement<UiToggle> {

    boolean getValue();

    UiToggle value(boolean value);

    /** Игрок переключил. */
    UiToggle onChange(Consumer<Boolean> listener);

}
