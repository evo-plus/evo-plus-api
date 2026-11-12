package ru.dargen.evoplus.api.social;

/**
 * Настройки игрока на сервере. Только для чтения: меняются игровыми меню и командами.
 *
 * @param friendLimit        сколько друзей можно при текущей привилегии
 * @param privateMessages    принимает ЛС; когда выключено, писать могут друзья и персонал
 * @param friendRequests     принимает заявки в друзья
 * @param globalChatDisabled общий чат скрыт целиком
 * @since 1.10.0
 */
public record SocialSettings(int friendLimit, boolean privateMessages, boolean friendRequests, boolean globalChatDisabled) {
}
