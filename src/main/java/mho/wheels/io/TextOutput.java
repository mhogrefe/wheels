package mho.wheels.io;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TextOutput {
    private @NotNull PrintWriter writer;

    public TextOutput(@NotNull String path) {
        try {
            writer = new PrintWriter(path);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("invalid path: " + path);
        }
    }

    public void print(@Nullable Object o) {
        writer.print(o);
    }

    public void println(@Nullable Object o) {
        writer.println(o);
    }

    public void close() {
        writer.close();
    }
}
