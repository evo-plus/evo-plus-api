package ru.dargen.evoplus.api;

import ru.dargen.evoplus.api.game.Location;
import ru.dargen.evoplus.api.game.Server;

public interface EvoPlusApi {

    static Location getLocation() {
        return stub();
    }

    static Server getServer() {
        return stub();
    }

    private static <T> T stub() {
        throw new UnsupportedOperationException();
    }

}
