package ru.dargen.evoplus.api.ui;

/**
 * Контекстное меню — список действий у курсора, обычно по правому клику
 * ({@link UiElement#onRightClick}). Закрывается выбором пункта, кликом мимо и по Esc.
 */
public interface UiMenu {

    /** Обычный пункт. */
    default UiMenu item(String label, Runnable action) {
        return item(label, null, ButtonStyle.SECONDARY, action);
    }

    /**
     * Пункт с иконкой слева; {@link ButtonStyle#DANGER} — красный (удалить),
     * {@link ButtonStyle#PRIMARY} — акцентный.
     *
     * @param icon текстура иконки или {@code null}
     */
    UiMenu item(String label, String icon, ButtonStyle style, Runnable action);

    /** Тонкая линия между группами пунктов. */
    UiMenu separator();

    /** Открывает у курсора поверх текущего экрана или окна. */
    void open();

    void close();

}
