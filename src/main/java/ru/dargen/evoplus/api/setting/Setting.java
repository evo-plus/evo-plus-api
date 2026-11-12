package ru.dargen.evoplus.api.setting;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Настройка со значением.
 *
 * @param <T> тип значения
 * @param <S> собственный тип — чтобы цепочки вызовов не теряли его
 */
public interface Setting<T, S extends Setting<T, S>> extends SettingElement<S> {

    T get();

    /**
     * Ставит значение и зовёт обработчики {@link #onChange}. Неподходящее значение
     * (вне диапазона, чужая константа) приводится к допустимому или отбрасывается.
     */
    void set(T value);

    /** Значение, с которым настройка была создана. */
    T getDefault();

    default void reset() {
        set(getDefault());
    }

    /** Обработчик изменения значения: из экрана настроек или через {@link #set}. Их может быть несколько. */
    S onChange(Consumer<T> listener);

    /**
     * Внешнее хранилище: значение читается через {@code getter} и пишется через {@code setter},
     * а в json-файл аддона не попадает. Для аддонов, у которых уже есть свой конфиг, —
     * EvoPlus тогда служит только экраном настроек.
     */
    S bind(Supplier<T> getter, Consumer<T> setter);

    boolean isBound();

    /**
     * Личная настройка: значение этого игрока или компьютера — аккаунт, токен, путь, выбранное
     * устройство, свой текст. В файл аддона пишется как обычно, но в «Поделиться» и облачные
     * пресеты не попадает и из присланного не перезаписывается. Секреты всё равно лучше держать
     * не в настройках, а в своём файле.
     *
     * @since 1.10.0 (EvoPlus 3.3.5)
     */
    S local(boolean local);

    /** @since 1.10.0 */
    boolean isLocal();

}
