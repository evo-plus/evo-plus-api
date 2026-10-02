package com.example.evoplus;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Собственный конфиг мода — для примера {@code bind}: часть настроек живёт здесь, а
 * EvoPlus их только показывает и пишет обратно. Настройки без {@code bind} EvoPlus хранит
 * сам, в {@code evo-plus/addons/example-addon.json}.
 */
public final class ExampleConfig {

    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve(ExampleMod.MOD_ID + ".txt");
    private static ExampleConfig instance = new ExampleConfig();

    public int clicks;

    public static ExampleConfig get() {
        return instance;
    }

    public static void load() {
        instance = new ExampleConfig();
        try {
            if (Files.exists(FILE)) instance.clicks = Integer.parseInt(Files.readString(FILE).trim());
        } catch (IOException | NumberFormatException ignored) {
        }
    }

    public static void save() {
        try {
            Files.writeString(FILE, Integer.toString(instance.clicks));
        } catch (IOException ignored) {
        }
    }

}
