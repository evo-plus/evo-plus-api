package ru.dargen.evoplus.api.social;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Изменение одного списка, уже применённое к {@link Social}.
 *
 * @param revision  ревизия состояния после изменения
 * @param list      какой список изменился
 * @param upserted  добавленные и обновлённые игроки (например, друг сменил сервер)
 * @param removed   убранные из списка — такими, какими были до изменения
 * @param previous  прежние записи обновлённых игроков по UUID; новых в списке здесь нет
 * @param cause     почему изменился список
 * @param actorUuid кто это сделал; {@code null} при {@link SocialCause#NONE}
 * @param bySelf    сделал сам игрок (с этого или другого клиента), а не кто-то другой
 * @since 1.10.0
 */
public record SocialUpdate(
        long revision,
        SocialList list,
        List<SocialPlayer> upserted,
        List<SocialPlayer> removed,
        Map<UUID, SocialPlayer> previous,
        SocialCause cause,
        UUID actorUuid,
        boolean bySelf
) {

    /** Прежняя запись игрока из {@link #upserted()}; {@code null} — его в списке не было. */
    public SocialPlayer previous(UUID uuid) {
        return previous.get(uuid);
    }

}
