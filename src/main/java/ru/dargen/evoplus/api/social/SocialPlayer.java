package ru.dargen.evoplus.api.social;

import java.util.UUID;

/**
 * Игрок в одном из списков {@link SocialList}: друг, заявка или игнорируемый.
 *
 * @param uuid     UUID игрока
 * @param name     ник, без префиксов
 * @param presence в сети ли он
 * @param server   полное имя сервера, где он играет; пустая строка — не в сети, скрыт или неизвестно
 * @param afk      отошёл (AFK); только у видимых в сети
 * @param prefix   префикс перед ником с кодами {@code §} (цвет ника — в конце), отображаемое имя —
 *                 {@code prefix + name}; пустая строка — неизвестен
 * @param lastSeen когда вышел из сети, epoch millis; 0 — в сети или неизвестно
 * @since 1.10.0
 */
public record SocialPlayer(
        UUID uuid,
        String name,
        Presence presence,
        String server,
        boolean afk,
        String prefix,
        long lastSeen
) {

    /** В сети: зашёл или ещё заходит. */
    public boolean isOnline() {
        return presence != Presence.OFFLINE;
    }

    public enum Presence {

        OFFLINE,

        /** Заходит в сеть: соединение есть, сервер ещё не выбран. */
        LOGIN,

        ONLINE

    }

}
