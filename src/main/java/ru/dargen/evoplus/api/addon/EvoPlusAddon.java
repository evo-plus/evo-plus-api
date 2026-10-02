package ru.dargen.evoplus.api.addon;

/**
 * Точка входа аддона EvoPlus. Объявляется в fabric.mod.json аддона:
 * <pre>
 * "entrypoints": {
 *   "evo-plus": [ "my.addon.MyEvoPlusAddon" ]
 * }
 * </pre>
 * EvoPlus вызывает её один раз, когда клиент уже запущен (после инициализации всех модов
 * и загрузки языка), и передаёт аддон, привязанный к id мода, который объявил точку входа.
 * <p>
 * Если EvoPlus не установлен, точку входа никто не вызовет и классы API не загрузятся —
 * поэтому интеграцию стоит держать целиком за ней: тогда EvoPlus для аддона необязателен.
 * Зависимость на API подключается как {@code compileOnly}: в рантайме классы API отдаёт сам EvoPlus.
 */
@FunctionalInterface
public interface EvoPlusAddon {

    String ENTRYPOINT = "evo-plus";

    void onInitialize(Addon addon);

}
