package io.lambdadepot.function.checked;

import io.lambdadepot.function.Function2;
import io.lambdadepot.util.Result;

@FunctionalInterface
public interface CheckedFunction2<T1, T2, R, X extends Throwable> {
    R apply(T1 t1, T2 t2) throws X;

    default Function2<T1, T2, Result<R>> lift() {
        return (t1, t2) -> {
            try {
                return Result.success(apply(t1, t2));
            } catch (Throwable e) {
                return Result.failure(e);
            }
        };
    }
}
