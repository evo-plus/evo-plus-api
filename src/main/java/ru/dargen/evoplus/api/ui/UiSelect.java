package ru.dargen.evoplus.api.ui;

import java.util.List;
import java.util.function.Consumer;

/** Выпадающий список. */
public interface UiSelect<T> extends UiElement<UiSelect<T>> {

    List<T> getOptions();

    T getValue();

    UiSelect<T> value(T value);

    UiSelect<T> onChange(Consumer<T> listener);

}
