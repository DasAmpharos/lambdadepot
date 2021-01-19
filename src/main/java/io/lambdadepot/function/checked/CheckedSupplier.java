package io.lambdadepot.function.checked;

public interface CheckedSupplier<R> {
    R get() throws Throwable;
}
