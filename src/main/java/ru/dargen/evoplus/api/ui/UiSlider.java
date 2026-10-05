package ru.dargen.evoplus.api.ui;

import java.util.function.DoubleConsumer;

/** Ползунок, как у настроек: число справа от полосы. */
public interface UiSlider extends UiElement<UiSlider> {

    double getValue();

    UiSlider value(double value);

    UiSlider onChange(DoubleConsumer listener);

}
