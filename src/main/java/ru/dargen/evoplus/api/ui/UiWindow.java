package ru.dargen.evoplus.api.ui;

/**
 * Окно EvoPlus: шапка с заголовком и крестиком, тело и (если запрошен) подвал с кнопками.
 * Тело — прокручиваемая колонка или свободная область, см. {@link Ui#window}.
 * <p>
 * Окно закрывается крестиком, по Esc и через {@link #close()}; после закрытия его можно
 * открыть снова — содержимое сохраняется.
 */
public interface UiWindow {

    String getTitle();

    UiWindow title(String title);

    /**
     * Содержимое. У прокручиваемого окна — колонка внутри прокрутки; у окна со свободным
     * телом — область во всё тело, детей в ней раскладывают через
     * {@link UiElement#anchor}/{@link UiElement#position} и относительные размеры.
     */
    UiContainer getContent();

    /** Ряд кнопок по центру подвала; {@code null}, если окно создано без подвала. */
    UiContainer getFooter();

    /** Ширина тела окна в единицах интерфейса — под неё переносят текст. */
    double getBodyWidth();

    /** Высота тела окна. У окна по содержимому — высота при пределе размера. */
    double getBodyHeight();

    UiWindow onClose(Runnable handler);

    /** Поверх текущего экрана, а если экрана нет — своим экраном. */
    UiWindow open();

    /** Своим экраном — как меню: текущий экран закрывается. */
    UiWindow openScreen();

    void close();

    boolean isOpen();

}
