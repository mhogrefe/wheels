package mho.wheels.io;

import mho.wheels.iterables.NoRemoveIterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Iterator;

public class TextInput implements Iterable<String> {
    private final Class classPathClass;
    private final @NotNull String path;

    public TextInput(@NotNull String path) {
        classPathClass = null;
        this.path = path;
    }

    public TextInput(Class classPathClass, @NotNull String path) {
        this.classPathClass = classPathClass;
        this.path = path;
    }

    @Override
    public @NotNull Iterator<String> iterator() {
        BufferedReader br;
        if (classPathClass == null) {
            try {
                br = new BufferedReader(new FileReader(path));
            } catch (FileNotFoundException e) {
                throw new IllegalStateException(e);
            }
        } else {
            InputStream is = classPathClass.getResourceAsStream(path);
            br = new BufferedReader(new InputStreamReader(is));
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
