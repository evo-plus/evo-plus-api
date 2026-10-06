package ru.dargen.evoplus.api.account;

import java.util.concurrent.CompletableFuture;

/**
 * Аккаунт EvoPlus игрока — чтобы аддон ходил в свои сервисы от его имени. Берётся у аддона:
 * {@link ru.dargen.evoplus.api.addon.Addon#getAccount()}.
 * <p>
 * Токен игрок получает от сервера DiamondWorld при входе и обновляет по ходу игры; до первого
 * входа на сервер его нет. Сервисы EvoPlus проверяют его открытым ключом, ник — claim {@code sub}.
 */
public interface Account {

    /** Ник из токена; {@code null}, пока токена нет. */
    String getPlayer();

    /**
     * Игровой токен (JWT) — в заголовок {@code X-Game-Token}. Брать перед каждым подключением:
     * он обновляется. {@code null}, пока токена нет.
     */
    String getToken();

    /** Регион аккаунта из токена: {@code ru} или {@code global}; {@code null}, пока токена нет. */
    String getRegion();

    /**
     * Рабочий домен категории discovery ({@code api}, {@code ws}, {@code cdn}…) для региона игрока —
     * тот же, куда ходит сам мод. Без токена или без такой категории завершается ошибкой.
     */
    CompletableFuture<String> resolveDomain(String category);

}
