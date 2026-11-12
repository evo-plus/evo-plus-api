package ru.dargen.evoplus.api.social;

import java.util.UUID;

/**
 * Ответ сервера на {@link Social#sendMessage}.
 *
 * @param status    итог
 * @param messageId при {@link Status#OK} — id сообщения, с которым придёт {@link SocialListener#onPrivateMessage}
 * @since 1.10.0
 */
public record PrivateMessageResult(Status status, UUID messageId) {

    public boolean isOk() {
        return status == Status.OK;
    }

    public enum Status {

        OK,

        /** Получатель не в сети или скрыт. */
        OFFLINE,

        SELF,

        /** Получатель держит отправителя в ЧС. */
        IGNORED,

        /** Получатель не принимает ЛС. */
        PM_DISABLED,

        /** Пустое или длиннее {@link Social#MAX_MESSAGE_LENGTH} символов. */
        INVALID,

        /** Сервер отправку пакетом не поддерживает или ещё не готов — можно командой. */
        UNAVAILABLE,

        ERROR,

        /**
         * Сервер не ответил вовремя. Сообщение могло уйти — тогда придёт
         * {@link SocialListener#onPrivateMessage}; повторять автоматически нельзя, получится дубль.
         */
        TIMEOUT,

        /** Запрос не ушёл: не в игре, соединение сменилось или списков нет ({@link Social#isAvailable()}). */
        NOT_SENT

    }

}
