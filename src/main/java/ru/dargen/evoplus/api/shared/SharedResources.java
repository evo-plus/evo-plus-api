package ru.dargen.evoplus.api.shared;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * Общие ресурсы аддонов: инструменты и файлы, которые нужны нескольким аддонам сразу —
 * ffmpeg, yt-dlp и подобное. Лежат в {@code evo-plus/shared/<имя>}, по папке на ресурс, и
 * скачиваются один раз на всех, а не каждым аддоном себе.
 * <p>
 * Имя ресурса — общее соглашение между аддонами ({@code "ffmpeg"}, {@code "yt-dlp"}): кто первый
 * попросил, тот и ставит, остальные берут готовое. Установка и обслуживание ресурса идут под
 * блокировкой — и между аддонами, и между запущенными копиями игры, — так что два аддона не
 * качают одно и то же одновременно.
 * <pre>
 * Path ffmpeg = addon.getShared().resolve("ffmpeg",
 *         List.of(oldBin.resolve("ffmpeg")),           // уже скачанное раньше — переносится
 *         folder -&gt; download(url, folder));            // нет нигде — ставится
 * </pre>
 *
 * @since 1.9.0
 */
public interface SharedResources {

    /** Папка всех общих ресурсов. */
    Path getFolder();

    /** Папка ресурса {@code name}; её может ещё не быть. */
    Path getPath(String name);

    /** Установлен ли ресурс. */
    boolean isPresent(String name);

    /**
     * Папка готового ресурса. Если его ещё нет:
     * <ol>
     *     <li>переносит в него первый существующий путь из {@code adopt} — файлы, которые аддон
     *     скачал раньше в свою папку (файл ложится в папку ресурса под своим именем, папка —
     *     целиком);</li>
     *     <li>иначе зовёт {@code installer} с пустой временной папкой; она становится папкой
     *     ресурса, только если установка прошла без ошибок.</li>
     * </ol>
     * Блокирует поток до конца установки — звать не с клиентского потока.
     *
     * @param adopt     кандидаты на перенос, можно пустой список
     * @param installer установка с нуля; {@code null} — не ставить, вернуть {@code null}
     * @return папка ресурса или {@code null}, если его нет, а {@code installer} не задан
     */
    Path resolve(String name, Collection<Path> adopt, Installer installer) throws IOException;

    /**
     * Действие над ресурсом под его блокировкой — например, обновить уже установленный
     * инструмент так, чтобы другой аддон в это время не начал им пользоваться или ставить его.
     */
    <T> T withLock(String name, Callable<T> action) throws Exception;

    @FunctionalInterface
    interface Installer {

        /** Ставит ресурс в пустую папку {@code folder}. */
        void install(Path folder) throws Exception;

    }

}
