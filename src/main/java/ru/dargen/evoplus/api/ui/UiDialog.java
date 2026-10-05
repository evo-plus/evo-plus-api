package ru.dargen.evoplus.api.ui;

/**
 * Маленькое окно по центру — колонка по содержимому с крестиком в углу: спросить, ввести,
 * подтвердить. Открывается поверх текущего экрана, без экрана — своим экраном.
 */
public interface UiDialog {

    /** Колонка содержимого, дети по центру. */
    UiContainer getContent();

    UiDialog onClose(Runnable handler);

    UiDialog open();

    void close();

    boolean isOpen();

}
