package com.example.evoplus.integration;

import com.example.evoplus.ExampleConfig;
import org.lwjgl.glfw.GLFW;
import ru.dargen.evoplus.api.EvoPlusApi;
import ru.dargen.evoplus.api.addon.Addon;
import ru.dargen.evoplus.api.addon.EvoPlusAddon;
import ru.dargen.evoplus.api.data.Notification;
import ru.dargen.evoplus.api.setting.AddonSettings;
import ru.dargen.evoplus.api.setting.SettingCategory;
import ru.dargen.evoplus.api.setting.SettingSection;
import ru.dargen.evoplus.api.setting.type.BooleanSetting;
import ru.dargen.evoplus.api.setting.type.ColorSetting;
import ru.dargen.evoplus.api.setting.type.EnumSetting;
import ru.dargen.evoplus.api.setting.type.KeyBind;
import ru.dargen.evoplus.api.setting.type.TextSetting;

import java.time.Duration;

/**
 * Точка входа {@code evo-plus}. EvoPlus зовёт её один раз на клиентском потоке, когда
 * клиент запущен и язык загружен, — здесь регистрируем всё, что аддон показывает в EvoPlus.
 * <p>
 * Настройки держим в статических полях: их читают виджет и обработчики. Значения уже
 * прочитаны из файла в момент регистрации — {@code get()} сразу отдаёт сохранённое.
 */
public class ExampleEvoPlusAddon implements EvoPlusAddon {

    static BooleanSetting enabled;
    static TextSetting greeting;
    static ColorSetting accent;
    static EnumSetting<ClockMode> mode;

    @Override
    public void onInitialize(Addon addon) {
        AddonSettings settings = addon.getSettings();
        // То же, что «Сохранить» в собственном меню мода: bind-настройки пишем в свой конфиг.
        settings.onSave(ExampleConfig::save);

        SettingCategory general = settings.category("general", "example.settings.general");

        enabled = general.toggle("enabled", "example.settings.enabled", true)
                .description("example.settings.enabled.description");

        greeting = general.text("greeting", "example.settings.greeting", "Привет из аддона!")
                .description("example.settings.greeting.description")
                .maxLength(64);

        // Клавиша: onPress срабатывает один раз на нажатие и молчит, пока открыт экран.
        general.key("notify-key", "example.settings.notify-key", KeyBind.keyboard(GLFW.GLFW_KEY_N, KeyBind.CONTROL))
                .onPress(ExampleEvoPlusAddon::notifyPlayer);

        accent = general.color("accent", "example.settings.accent", 0xFF55FFFF).alpha(true);

        mode = general.enumSelect("mode", "example.settings.mode", ClockMode.SECONDS)
                .names(value -> value.translationKey);

        // Значение из собственного конфига мода: в json EvoPlus не попадает.
        general.intField("counter", "example.settings.counter", 0, 0, Integer.MAX_VALUE)
                .description("example.settings.counter.description")
                .bind(() -> ExampleConfig.get().clicks, value -> ExampleConfig.get().clicks = value);

        general.button("reset", "example.settings.reset", "example.settings.reset.label", () -> {
            ExampleConfig.get().clicks = 0;
            ExampleConfig.save();
        });

        // Виджет — в своей секции. Строку виджета прячем, пока аддон выключен.
        SettingSection widgets = general.section("widgets", "example.widget");
        ClockWidget.register(widgets).visible(() -> enabled.get());
    }

    private static void notifyPlayer() {
        if (!enabled.get()) return;

        EvoPlusApi.showNotification(Notification.builder()
                .title("Example Addon")
                .message(greeting.get())
                .action(() -> ExampleConfig.get().clicks = 0)
                .duration(Duration.ofSeconds(4))
                .build());
    }

    enum ClockMode {

        SECONDS("example.settings.mode.seconds", "HH:mm:ss"),
        MINUTES("example.settings.mode.minutes", "HH:mm");

        final String translationKey;
        final String pattern;

        ClockMode(String translationKey, String pattern) {
            this.translationKey = translationKey;
            this.pattern = pattern;
        }

    }

}
