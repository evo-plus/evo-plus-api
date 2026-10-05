package ru.dargen.evoplus.api.ui;

import java.util.Objects;

/** Эмодзи сервера: название, символ (глиф ресурспака) и ключ {@code :id:}, который понимает сервер. */
public final class Emoji {

    private final String name;
    private final String symbol;
    private final String key;

    public Emoji(String name, String symbol, String key) {
        this.name = Objects.requireNonNull(name, "name");
        this.symbol = Objects.requireNonNull(symbol, "symbol");
        this.key = Objects.requireNonNull(key, "key");
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Emoji other && symbol.equals(other.symbol) && key.equals(other.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, key);
    }

    @Override
    public String toString() {
        return "Emoji{" + key + "}";
    }

}
