package mho.wheels.io;

import mho.wheels.iterables.NoRemoveIterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class TextInput implements Iterable<String> {
    private final @NotNull String path;

    public TextInput(@NotNull String path) {
        this.path = path;
    }

    @Override
    public @NotNull Iterator<String> iterator() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
        try {
            return new NoRemoveIterator<String>() {
                private @Nullable String line = br.readLine();

                @Override
                public boolean hasNext() {
                    boolean finished = line == null;
                    if (finished) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            throw new IllegalStateException(e);
                        }
                    }
                    return !finished;
                }

                @Override
                public String next() {
                    String oldLine = line;
                    try {
                        line = br.readLine();
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                    return oldLine;
                }
            };
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
