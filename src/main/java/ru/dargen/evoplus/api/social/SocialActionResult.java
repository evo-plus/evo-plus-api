package ru.dargen.evoplus.api.social;

import java.util.UUID;

/**
 * Ответ сервера на {@link Social#act}. Это только итог действия: списки меняет
 * {@link SocialListener#onUpdate}, и изменение может прийти и до, и после ответа.
 *
 * @param status     итог
 * @param targetUuid UUID игрока, если сервер его нашёл
 * @since 1.10.0
 */
public record SocialActionResult(Status status, UUID targetUuid) {

    public boolean isOk() {
        return status == Status.OK;
    }

    public enum Status {

        OK,

        /** Уже сделано: уже друзья, заявка уже отправлена, уже в ЧС. */
        ALREADY,

        /** Игрока с таким ником нет. */
        NOT_FOUND,

        /** Нечего менять: не друзья, нет заявки, не в ЧС. */
        NO_RELATION,

        SELF,

        /** У игрока предел друзей. */
        LIMIT,

        /** У цели предел друзей. */
        TARGET_LIMIT,

        /** Цель не принимает заявки. */
        DISABLED,

        /** Персонал игнорировать нельзя. */
        STAFF,

        /** Сервер действий не поддерживает или ещё не готов — их можно сделать командой. */
        UNAVAILABLE,

        ERROR,

        /**
         * Сервер не ответил вовремя. Действие при этом могло пройти — тогда придёт
         * {@link SocialListener#onUpdate}.
         */
        TIMEOUT,

        /** Запрос не ушёл: не в игре, соединение сменилось или списков нет ({@link Social#isAvailable()}). */
        NOT_SENT

    }

}
