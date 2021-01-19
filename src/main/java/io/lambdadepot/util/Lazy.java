package io.lambdadepot.util;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;
import java.util.function.Supplier;

public final class Lazy<R> {
    private R value;
    private final Supplier<R> supplier;
    private boolean initialized = false;

    private Lazy(Supplier<R> supplier) {
        this.supplier = supplier;
    }

    public R get() {
        if (!initialized) {
            value = supplier.get();
            initialized = true;
        }
        return value;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public static <R> Lazy<R> of(@NonNull Supplier<R> supplier) {
        Objects.requireNonNull(supplier);
        return new Lazy<>(supplier);
    }
}
