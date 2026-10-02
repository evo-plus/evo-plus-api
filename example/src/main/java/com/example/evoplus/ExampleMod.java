package com.example.evoplus;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.world.InteractionResult;

/**
 * Сам мод. Про EvoPlus здесь ничего нет: интеграция целиком в пакете {@code integration}
 * и подключается отдельной точкой входа {@code evo-plus} из fabric.mod.json. Без EvoPlus
 * её никто не вызовет, классы API не загрузятся, и мод работает как обычно.
 */
public class ExampleMod implements ClientModInitializer {

    public static final String MOD_ID = "example-addon";

    @Override
    public void onInitializeClient() {
        ExampleConfig.load();

        // Что-то, что мод делает сам по себе: считает удары по блокам.
        AttackBlockCallback.EVENT.register((player, level, hand, pos, direction) -> {
            if (level.isClientSide()) ExampleConfig.get().clicks++;
            return InteractionResult.PASS;
        });
    }

}
