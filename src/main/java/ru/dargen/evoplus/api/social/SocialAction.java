package ru.dargen.evoplus.api.social;

/**
 * Действие со списками — {@link Social#act}. Проверки те же, что у команд {@code /friend},
 * {@code /ignore} и {@code /ignoreglobal}.
 *
 * @since 1.10.0
 */
public enum SocialAction {

    /** Заявка в друзья; при встречной заявке — принять её. */
    ADD,

    /** Удалить из друзей. */
    REMOVE,

    /** Принять входящую заявку. */
    ACCEPT,

    /** Отклонить входящую заявку. */
    DECLINE,

    /** В ЧС личных сообщений. Не переключает: повтор даёт {@link SocialActionResult.Status#ALREADY}. */
    IGNORE,

    UNIGNORE,

    /** В игнор общего чата. */
    IGNORE_GLOBAL,

    UNIGNORE_GLOBAL

}
