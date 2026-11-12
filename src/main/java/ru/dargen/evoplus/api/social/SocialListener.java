package ru.dargen.evoplus.api.social;

/**
 * Подписка на {@link Social}. Все методы зовутся на клиентском потоке; переопределяйте нужные.
 *
 * @since 1.10.0
 */
public interface SocialListener {

    /**
     * Сервер стал присылать списки ({@code true}: пришёл первый снимок после захода) или
     * перестал (отключились или перешли на другой сервер — до нового снимка списков нет).
     */
    default void onAvailabilityChange(boolean available) {
    }

    /** Пришёл полный снимок: все списки и настройки заменены. Первый снимок — после {@link #onAvailabilityChange}. */
    default void onSnapshot(Social social) {
    }

    /** Изменился один список. */
    default void onUpdate(SocialUpdate update) {
    }

    /** Сменились настройки игрока (без снимка). */
    default void onSettings(SocialSettings settings) {
    }

    /** Сервер принял личное сообщение — входящее или отправленное этим игроком. Повторы по id не приходят. */
    default void onPrivateMessage(PrivateMessage message) {
    }

}
