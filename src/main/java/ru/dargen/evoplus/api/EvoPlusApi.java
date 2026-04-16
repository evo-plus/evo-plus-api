package ru.dargen.evoplus.api;

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

    private static <T> T stub() {
        throw new UnsupportedOperationException();
    }

}
