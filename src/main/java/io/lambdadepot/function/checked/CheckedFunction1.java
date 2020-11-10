package io.lambdadepot.function.checked;

/**
 *
 * @param <T1>
 * @param <R>
 */
public interface CheckedFunction1<T1, R> {
    /**
     *
     * @param t1
     * @return
     * @throws Exception
     */
    R apply(T1 t1) throws Exception;
}
