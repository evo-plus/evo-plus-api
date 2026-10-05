package ru.dargen.evoplus.api.ui;

/**
 * Голова игрока (лицо со слоем шляпы). Скин берётся из таба сервера, а если игрока там
 * нет — у Mojang по нику; пока скин грузится, видно стандартное лицо.
 */
public interface UiHead extends UiElement<UiHead> {

    String getPlayer();

    UiHead player(String name);

}
