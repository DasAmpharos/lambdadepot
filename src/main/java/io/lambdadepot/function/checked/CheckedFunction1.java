package io.lambdadepot.function.checked;

import io.lambdadepot.function.Function1;
import io.lambdadepot.util.Result;

/**
 * @param <T1>
 * @param <R>
 */
@FunctionalInterface
public interface CheckedFunction1<T1, R> {
    /**
     * @param t1
     * @return
     * @throws Exception
     */
    R apply(T1 t1) throws Exception;

    default Function1<T1, Result<R>> lift() {
        return t1 -> {
            try {
                return Result.success(apply(t1));
            } catch (Exception e) {
                return Result.failure(e);
            }
        };
    }
}
