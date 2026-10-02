package ru.dargen.evoplus.api.setting.type;

import java.util.Objects;

/**
 * Сочетание: клавиша клавиатуры или кнопка мыши плюс модификаторы.
 * <p>
 * Коды — GLFW: {@code GLFW_KEY_*} для клавиатуры, {@code GLFW_MOUSE_BUTTON_*} для мыши.
 * Модификаторы — битовая маска в той же конвенции, что и {@code mods} у событий GLFW
 * ({@link #SHIFT}, {@link #CONTROL}, {@link #ALT}).
 */
public final class KeyBind {

    public static final int SHIFT = 0x1;
    public static final int CONTROL = 0x2;
    public static final int ALT = 0x4;

    /** Ничего не привязано. */
    public static final KeyBind NONE = new KeyBind(Type.KEYBOARD, -1, 0);

    public enum Type {
        KEYBOARD, MOUSE
    }

    private final Type type;
    private final int code;
    private final int modifiers;

    // Для Gson: значение пишется в json аддона как есть.
    private KeyBind() {
        this(Type.KEYBOARD, -1, 0);
    }

    private KeyBind(Type type, int code, int modifiers) {
        this.type = Objects.requireNonNull(type, "type");
        this.code = code;
        this.modifiers = modifiers & (SHIFT | CONTROL | ALT);
    }

    public static KeyBind keyboard(int key) {
        return keyboard(key, 0);
    }

    public static KeyBind keyboard(int key, int modifiers) {
        return key < 0 ? NONE : new KeyBind(Type.KEYBOARD, key, modifiers);
    }

    public static KeyBind mouse(int button) {
        return mouse(button, 0);
    }

    public static KeyBind mouse(int button, int modifiers) {
        return button < 0 ? NONE : new KeyBind(Type.MOUSE, button, modifiers);
    }

    public Type getType() {
        return type;
    }

    public int getCode() {
        return code;
    }

    public int getModifiers() {
        return modifiers;
    }

    public boolean isUnbound() {
        return code < 0;
    }

    public KeyBind withModifiers(int modifiers) {
        return isUnbound() ? NONE : new KeyBind(type, code, modifiers);
    }

    /**
     * Совпадает ли событие с сочетанием. Нужны все модификаторы сочетания; лишние зажатые
     * не мешают — так ведут себя и бинды игры.
     */
    public boolean matches(Type type, int code, int modifiers) {
        return !isUnbound() && this.type == type && this.code == code && (modifiers & this.modifiers) == this.modifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyBind other)) return false;
        return type == other.type && code == other.code && modifiers == other.modifiers;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, code, modifiers);
    }

    @Override
    public String toString() {
        return "KeyBind{" + type + " " + code + ", modifiers=" + modifiers + "}";
    }

}
