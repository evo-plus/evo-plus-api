package ru.dargen.evoplus.api.ui;

/** Счётчик-«таблетка» акцентного цвета: непрочитанные, новые. Больше 99 — «99+». */
public interface UiBadge extends UiElement<UiBadge> {

    int getCount();

    UiBadge count(int count);

    /** Текст перед числом: {@code "+"} — «+3», новые заявки поверх уже имеющегося. */
    UiBadge prefix(String prefix);

}
