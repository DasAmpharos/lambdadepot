package io.lambdadepot.function.checked;

import io.lambdadepot.function.Function0;
import io.lambdadepot.util.Result;

public interface CheckedFunction0<R> {
    R apply() throws Exception;

    default Function0<Result<R>> lift() {
        return () -> {
            try {
                return Result.success(apply());
            } catch (Exception e) {
                return Result.failure(e);
            }
        };
    }
}
