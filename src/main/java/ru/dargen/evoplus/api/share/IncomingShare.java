package ru.dargen.evoplus.api.share;

/** Игрок чем-то поделился — см. {@link Sharing#onReceive}. */
public interface IncomingShare {

    /** Настройки EvoPlus: принимаются по коду, код живёт в share-service час. */
    String SETTINGS = "settings";

    /** Таймеры боссов. */
    String BOSS_TIMERS = "boss";

    /** Чёрный список боссов. */
    String BOSS_BLACKLIST = "boss_filter";

    /** Ник отправителя. */
    String getFrom();

    /** Что прислали: {@link #SETTINGS}, {@link #BOSS_TIMERS}, {@link #BOSS_BLACKLIST} или вид, который появится позже. */
    String getType();

    /** Что прислали — готовой строкой на языке игры: «Настройки EvoPlus», «Таймеры боссов». */
    String getTitle();

    /** Подробности на языке игры, например «5 боссов»; может быть пустой. */
    String getDetails();

    /** Когда пришло, {@link System#currentTimeMillis()}. */
    long getReceivedAt();

    /** До какого момента можно принять; 0 — без срока. */
    long getExpiresAt();

    /** Код настроек — у {@link #SETTINGS}, у остальных {@code null}. Принять по коду и позже: {@link Sharing#openImport}. */
    String getCode();

    /** Можно ли принять сейчас: не истёк срок и не принято (повторно таймеры не применяются). */
    boolean canAccept();

    /** Принять: настройки — окно выбора, таймеры и чёрный список — применить, как кликом по уведомлению. */
    void accept();

}
