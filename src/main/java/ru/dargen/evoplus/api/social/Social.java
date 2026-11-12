package ru.dargen.evoplus.api.social;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Друзья, заявки, ЧС и личные сообщения игрока — пакетами сервера DiamondWorld, без разбора
 * чата и команд. Берётся у аддона: {@link ru.dargen.evoplus.api.addon.Addon#getSocial()}.
 * <p>
 * Списки сервер присылает сам: полным снимком после захода и изменениями дальше. Старые серверы
 * их не присылают — тогда {@link #isAvailable()} ложен, списки пусты, и аддону нужен свой путь
 * (команды и чат). Действия ({@link #act}, {@link #sendMessage}) тоже идут пакетами и отвечают
 * итогом; ответ {@code UNAVAILABLE} — сервер их ещё не умеет, тогда — командой.
 * <pre>
 * Social social = addon.getSocial();
 * social.addListener(new SocialListener() {
 *     public void onUpdate(SocialUpdate update) { … }
 *     public void onPrivateMessage(PrivateMessage message) { … }
 * });
 * if (social.isAvailable()) social.getFriends().forEach(…);
 * social.act(SocialAction.ADD, "Notch").thenAccept(result -> { if (!result.isOk()) … });
 * </pre>
 * Списки — неизменяемые копии на момент вызова, читать их можно с любого потока.
 *
 * @since 1.10.0
 */
public interface Social {

    /** Предел длины ЛС в символах. */
    int MAX_MESSAGE_LENGTH = 256;

    /** Сервер прислал списки после последнего захода; до этого и на старых серверах — нет. */
    boolean isAvailable();

    /** Ревизия текущего состояния; растёт с каждым изменением, 0 — снимка ещё не было. */
    long getRevision();

    /** Список целиком, в порядке сервера; пустой, пока списков нет. */
    List<SocialPlayer> getList(SocialList list);

    /** Настройки игрока; {@code null}, пока списков нет. */
    SocialSettings getSettings();

    default List<SocialPlayer> getFriends() {
        return getList(SocialList.FRIENDS);
    }

    /** Входящие заявки в друзья. */
    default List<SocialPlayer> getRequests() {
        return getList(SocialList.REQUESTS);
    }

    /** Исходящие заявки в друзья. */
    default List<SocialPlayer> getOutgoingRequests() {
        return getList(SocialList.OUTGOING_REQUESTS);
    }

    /** ЧС личных сообщений. */
    default List<SocialPlayer> getIgnoredPrivate() {
        return getList(SocialList.IGNORED_PRIVATE);
    }

    /** Игнор в общем чате. */
    default List<SocialPlayer> getIgnoredGlobal() {
        return getList(SocialList.IGNORED_GLOBAL);
    }

    /** Игрок в списке по нику без учёта регистра; {@code null} — нет такого. */
    default SocialPlayer find(SocialList list, String name) {
        for (SocialPlayer player : getList(list)) {
            if (player.name().equalsIgnoreCase(name)) return player;
        }
        return null;
    }

    /**
     * Действие с игроком по нику. Будущее завершается всегда и только нормально, на клиентском
     * потоке: ответом сервера, {@code TIMEOUT} или {@code NOT_SENT}.
     */
    CompletableFuture<SocialActionResult> act(SocialAction action, String target);

    /**
     * Личное сообщение игроку по нику. Завершается так же, как {@link #act}. Принятое сервером
     * сообщение придёт ещё и в {@link SocialListener#onPrivateMessage} — писать историю лучше по нему.
     */
    CompletableFuture<PrivateMessageResult> sendMessage(String target, String text);

    /** Подписаться на изменения и личные сообщения. Один и тот же слушатель второй раз не добавляется. */
    void addListener(SocialListener listener);

    void removeListener(SocialListener listener);

}
