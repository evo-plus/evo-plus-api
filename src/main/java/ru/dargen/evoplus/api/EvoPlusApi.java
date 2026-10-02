package ru.dargen.evoplus.api;

import ru.dargen.evoplus.api.addon.Addon;
import ru.dargen.evoplus.api.data.Notification;
import ru.dargen.evoplus.api.game.Location;
import ru.dargen.evoplus.api.game.Server;

import java.time.Duration;
import java.util.function.Consumer;

public interface EvoPlusApi {

    static Location getLocation() {
        return stub();
    }

    static Server getServer() {
        return stub();
    }

    static void showNotification(Notification notification) {
        stub();
    }

    /**
     * Аддон по id мода: зарегистрированный или новый. Обычно аддон приходит в точку входа
     * {@link ru.dargen.evoplus.api.addon.EvoPlusAddon} — этот вызов нужен, чтобы достать его в другом месте.
     */
    static Addon getAddon(String modId) {
        return stub();
    }

    private static <T> T stub() {
        throw new UnsupportedOperationException();
    }

}
