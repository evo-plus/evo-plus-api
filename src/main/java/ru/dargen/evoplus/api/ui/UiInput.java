package ru.dargen.evoplus.api.ui;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/** Поле ввода в одну строку. Фокус — по клику, Enter отправляет, Esc снимает фокус. */
public interface UiInput extends UiElement<UiInput> {

    String getValue();

    /** Ставит текст без вызова {@link #onChange}. */
    UiInput value(String value);

    UiInput placeholder(String placeholder);

    /** Предел длины; по умолчанию 256. */
    UiInput maxLength(int maxLength);

    /** Какие строки можно ввести: ввод, после которого текст не проходит проверку, отбрасывается. */
    UiInput filter(Predicate<String> filter);

    /** Текст изменился от ввода игрока. */
    UiInput onChange(Consumer<String> listener);

    /** Нажат Enter. */
    UiInput onSubmit(Consumer<String> listener);

    /**
     * Дополнение по Tab: {@code provider} получает введённый текст и отдаёт варианты. Tab
     * перебирает их (вариант виден подсказкой и уже приходит в {@link #onChange}), Enter
     * подставляет выбранный.
     */
    UiInput completions(Function<String, List<String>> provider);

    /**
     * Дополнение по Tab, когда варианты приходят не сразу — например, у сервера (автодополнение
     * его команд). Ответ можно завершать на любом потоке: EvoPlus подставит его на клиентском.
     */
    UiInput completionsAsync(Function<String, CompletionStage<List<String>>> provider);

    /**
     * До скольких строк растёт поле; по умолчанию одна. Больше одной — длинный текст
     * переносится по словам (в {@link #getValue()} переносов нет), а поле растёт вниз на
     * строку; дальше предела оно прокручивается к строке курсора.
     */
    UiInput maxLines(int maxLines);

    /** Вставляет текст на место курсора (вместо выделения) — как ввод игрока, с {@link #onChange}. */
    UiInput insert(String text);

    UiInput focus(boolean focused);

    boolean isFocused();

}
