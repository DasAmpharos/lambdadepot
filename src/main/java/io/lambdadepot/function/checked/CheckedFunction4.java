package io.lambdadepot.function.checked;

import io.lambdadepot.function.Function4;
import io.lambdadepot.util.Result;

public interface CheckedFunction4<T1, T2, T3, T4, R, X extends Throwable> {
    R apply(T1 t1, T2 t2, T3 t3, T4 t4) throws X;

    default Function4<T1, T2, T3, T4, Result<R>> lift() {
        return (t1, t2, t3, t4) -> {
            try {
                return Result.success(apply(t1, t2, t3, t4));
            } catch (Throwable e) {
                return Result.failure(e);
            }
        };
    }
}
