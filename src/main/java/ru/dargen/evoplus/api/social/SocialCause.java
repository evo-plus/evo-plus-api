package ru.dargen.evoplus.api.social;

/**
 * Почему изменился список. Кто это сделал — {@link SocialUpdate#actorUuid()}: сам игрок
 * ({@link SocialUpdate#bySelf()}) или другой — «ты удалил» и «тебя удалили» различаются им.
 *
 * @since 1.10.0
 */
public enum SocialCause {

    /** Сменились сервер, AFK или префикс, пересинхронизация или изменение не social-действием. */
    NONE,

    REQUEST_SENT,

    REQUEST_ACCEPTED,

    REQUEST_DECLINED,

    /**
     * Удаление из друзей. У удалившего при этом появляется входящая заявка от удалённого, у
     * удалённого — исходящая к удалившему, с той же причиной.
     */
    FRIEND_REMOVED,

    IGNORED,

    UNIGNORED

}
