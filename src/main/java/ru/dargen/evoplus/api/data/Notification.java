package ru.dargen.evoplus.api.data;

import java.time.Duration;
import java.util.Objects;

public class Notification {

    private final String title;
    private final String message;
    private final Runnable action;
    private final Duration duration;

    public Notification(String title, String message, Runnable action, Duration duration) {
        this.title = title;
        this.message = message;
        this.action = action;
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }

    public Runnable getAction() {
        return action;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String message;
        private Runnable action;
        private Duration duration = Duration.ofSeconds(5);

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder action(Runnable action) {
            this.action = action;
            return this;
        }

        public Builder duration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public Notification build() {
            Objects.requireNonNull(title, "title");
            Objects.requireNonNull(message, "message");
            Objects.requireNonNull(duration, "duration");
            return new Notification(title, message, action, duration);
        }
    }

}
