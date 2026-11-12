package ru.dargen.evoplus.api.social;

import java.util.UUID;

/**
 * Личное сообщение, которое сервер принял. Отправитель и получатель получают его с одним и тем
 * же {@code messageId} — по нему удобно отсеивать повторы.
 *
 * @param messageId       id сообщения на сервере
 * @param timestamp       когда сервер его принял, epoch millis
 * @param senderName      ник отправителя, без префиксов
 * @param recipientName   ник получателя, без префиксов
 * @param text            текст, как его написал отправитель
 * @param direction       входящее или исходящее для этого игрока
 * @param senderPrefix    префикс отправителя с кодами {@code §}, как у {@link SocialPlayer#prefix()}
 * @param recipientPrefix префикс получателя
 * @param recipientAfk    получатель отошёл; только у исходящих
 * @since 1.10.0
 */
public record PrivateMessage(
        UUID messageId,
        long timestamp,
        UUID senderUuid,
        String senderName,
        UUID recipientUuid,
        String recipientName,
        String text,
        Direction direction,
        String senderPrefix,
        String recipientPrefix,
        boolean recipientAfk
) {

    public boolean isIncoming() {
        return direction == Direction.INCOMING;
    }

    /** Собеседник: отправитель входящего, получатель исходящего. */
    public String peerName() {
        return isIncoming() ? senderName : recipientName;
    }

    public UUID peerUuid() {
        return isIncoming() ? senderUuid : recipientUuid;
    }

    public String peerPrefix() {
        return isIncoming() ? senderPrefix : recipientPrefix;
    }

    public enum Direction {
        INCOMING, OUTGOING
    }

}
