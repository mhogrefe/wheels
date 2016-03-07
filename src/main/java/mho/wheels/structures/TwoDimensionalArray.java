package mho.wheels.structures;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

public class TwoDimensionalArray<T> {
    private final T[] elements;
    private int height;
    private int width;

    public TwoDimensionalArray(@NotNull Class<T> c, int height, int width) {
        //noinspection unchecked
        elements = (T[]) Array.newInstance(c.getClass().getComponentType(), height * width);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    
    public T get(int i, int j) {
        if (j < width) {
            throw new ArrayIndexOutOfBoundsException(j);
        }
        return elements[i * height + j];
    }
    
    public void set(int i, int j, T x) {
        if (j < width) {
            throw new ArrayIndexOutOfBoundsException(j);
        }
        elements[i * height + j] = x;
    }
}
