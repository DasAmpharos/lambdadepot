package io.lambdadepot.function.checked;

import io.lambdadepot.function.Function0;
import io.lambdadepot.util.Result;

import java.util.NoSuchElementException;

public interface CheckedFunction0<R, X extends Throwable> {
    R apply() throws X;

    default Function0<Result<R>> lift() {
        return () -> {
            try {
                return Result.success(apply());
            } catch (Throwable e) {
                return Result.failure(e);
            }
        };
    }
}
