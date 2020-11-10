/*
 * Copyright 2020 The Home Depot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.lambdadepot.function;

import io.lambdadepot.util.Result;
import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a function that accepts four arguments and produces a result.
 * This is the four-arity specialization of {@link Function1}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object, Object, Object, Object)}.
 *
 * @param <T1> the type of the first argument to the function
 * @param <T2> the type of the second argument to the function
 * @param <T3> the type of the third argument to the function
 * @param <T4> the type of the fourth argument to the function
 * @param <R>  the type of the result of the function
 * @see Function1
 */
@FunctionalInterface
public interface Function4<T1, T2, T3, T4, R> {

    /**
     * Gets method reference/lambda as a Function4 instance.
     *
     * @param reference method reference to convert to Function4 instance
     * @param <T1>      first function argument
     * @param <T2>      second function argument
     * @param <T3>      third function argument
     * @param <T4>      fourth function argument
     * @param <R>       function result
     * @return Function4 instance of method reference
     * @throws NullPointerException if reference is null
     */
    static <T1, T2, T3, T4, R> Function4<T1, T2, T3, T4, R> of(Function4<T1, T2, T3, T4, R> reference) {
        Objects.requireNonNull(reference, "reference");
        return reference;
    }

    /**
     * Applies this function to the given arguments.
     *
     * @param t1 the first function argument
     * @param t2 the second function argument
     * @param t3 the third function argument
     * @param t4 the fourth function argument
     * @return the function result
     */
    R apply(T1 t1, T2 t2, T3 t3, T4 t4);

    /**
     * A partial application of the first argument to the function.
     *
     * @param t1 the first argument
     * @return Function3 instance expecting the second, third and fourth arguments
     */
    default Function3<T2, T3, T4, R> partialApply(T1 t1) {
        return (t2, t3, t4) -> apply(t1, t2, t3, t4);
    }

    /**
     * A partial application of the first and second arguments to the function.
     *
     * @param t1 the first argument
     * @param t2 the second argument
     * @return Function2 instance expecting the third and fourth arguments
     */
    default Function2<T3, T4, R> partialApply(T1 t1, T2 t2) {
        return (t3, t4) -> apply(t1, t2, t3, t4);
    }

    /**
     * A partial application of the first, second and third arguments to the function.
     *
     * @param t1 the first argument
     * @param t2 the second argument
     * @param t3 the third argument
     * @return Function1 instance expecting the fourth argument
     */
    default Function1<T4, R> partialApply(T1 t1, T2 t2, T3 t3) {
        return t4 -> apply(t1, t2, t3, t4);
    }

    /**
     * A partial application of the first, second, third and fourth arguments to the function.
     *
     * @param t1 the first argument
     * @param t2 the second argument
     * @param t3 the third argument
     * @param t4 the fourth argument
     * @return Function0 instance expecting no arguments
     */
    default Function0<R> partialApply(T1 t1, T2 t2, T3 t3, T4 t4) {
        return () -> apply(t1, t2, t3, t4);
    }

    /**
     * Returns a function that accepts the arguments to this function
     * in reversed order.
     *
     * @return a function that accepts the arguments to this function
     * in reversed order
     */
    default Function4<T4, T3, T2, T1, R> reverse() {
        return (t4, t3, t2, t1) -> apply(t1, t2, t3, t4);
    }

    /**
     * <a href="https://stackoverflow.com/a/36321">
     * Currying
     * </a>
     *
     * <p>Reduces this function to a Function1 instance that returns a function
     * when it is applied to an argument.
     *
     * @return a Function1 instance that returns a function when it is applied to
     * an argument
     */
    default Function1<T1, Function3<T2, T3, T4, R>> curry() {
        return t1 -> (t2, t3, t4) -> apply(t1, t2, t3, t4);
    }

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <X>   the type of output of the {@code after} function, and of the
     *              composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if after is null
     */
    default <X> Function4<T1, T2, T3, T4, X> andThen(Function<R, X> after) {
        Objects.requireNonNull(after, "after");
        return (t1, t2, t3, t4) -> after.apply(apply(t1, t2, t3, t4));
    }

    /**
     * Internally performs a try/catch on this, and changes the return type to a
     * {@link Result} wrapper.
     *
     * @return {@link Function4} with the return type with a {@link Result} wrapper
     */
    default Function4<T1, T2, T3, T4, Result<R>> lift() {
        return (t1, t2, t3, t4) -> {
            try {
                return Result.success(apply(t1, t2, t3, t4));
            } catch (Exception e) {
                return Result.failure(e);
            }
        };
    }
}
