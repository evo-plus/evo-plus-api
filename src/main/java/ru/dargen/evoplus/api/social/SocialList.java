package ru.dargen.evoplus.api.social;

/**
 * Списки игрока на сервере DiamondWorld.
 *
 * @since 1.10.0
 */
public enum SocialList {

    /** Друзья. */
    FRIENDS,

    /** Входящие заявки в друзья. */
    REQUESTS,

    /** ЧС личных сообщений: писать игроку в ЛС они не могут. */
    IGNORED_PRIVATE,

    /** Игнор в общем чате: их сообщений в чате не видно. */
    IGNORED_GLOBAL,

    /** Исходящие заявки в друзья, ещё без ответа. */
    OUTGOING_REQUESTS

}
