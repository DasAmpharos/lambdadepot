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
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Represents a function that accepts two arguments and produces a result.
 * This is the two-arity specialization of {@link Function1}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object, Object)}.
 *
 * <p>Extends {@link BiFunction} to provide additional helper methods.
 *
 * @param <T1> the type of the first argument to the function
 * @param <T2> the type of the second argument to the function
 * @param <R>  the type of the result of the function
 * @see Function1
 */
@FunctionalInterface
public interface Function2<T1, T2, R> extends BiFunction<T1, T2, R> {

    /**
     * Gets method reference/lambda as a Function2 instance.
     *
     * @param reference Function reference
     * @param <T1>      the type of the first argument to the function
     * @param <T2>      the type of the second argument to the function
     * @param <R>       the type of the result of the function
     * @return method reference/lambda as a Function2 instance
     * @throws NullPointerException if reference is null
     */
    static <T1, T2, R> Function2<T1, T2, R> of(Function2<T1, T2, R> reference) {
        Objects.requireNonNull(reference, "reference");
        return reference;
    }

    /**
     * Converts a BiFunction instance to a Function2.
     *
     * <p>Provides interop with existing BiFunction instances.
     *
     * @param function the BiFunction instance to convert
     * @param <T1>     the type of the first argument to the function
     * @param <T2>     the type of the second argument to the function
     * @param <R>      the type of the result of the function
     * @return function as a Function2 instance
     * @throws NullPointerException if function is null
     */
    static <T1, T2, R> Function2<T1, T2, R> asFunction2(BiFunction<T1, T2, R> function) {
        Objects.requireNonNull(function, "function");
        return function::apply;
    }

    /**
     * A partial application of the first argument to the function.
     *
     * @param t1 the first argument
     * @return Function1 instance expecting the second argument
     */
    default Function1<T2, R> partialApply(T1 t1) {
        return t2 -> apply(t1, t2);
    }

    /**
     * A partial application of the first and second arguments to the function.
     *
     * @param t1 the first argument
     * @param t2 the second argument
     * @return Function0 instance expecting no arguments
     */
    default Function0<R> partialApply(T1 t1, T2 t2) {
        return () -> apply(t1, t2);
    }

    /**
     * Returns a function that accepts the arguments to this function
     * in reversed order.
     *
     * @return a function that accepts the arguments to this function
     * in reversed order
     */
    default Function2<T2, T1, R> reverse() {
        return (t2, t1) -> apply(t1, t2);
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
    default Function1<T1, Function1<T2, R>> curry() {
        return t1 -> t2 -> apply(t1, t2);
    }

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <R1>  the type of output of the {@code after} function, and of the
     *              composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if after is null
     */
    @Override
    default <R1> Function2<T1, T2, R1> andThen(Function<? super R, ? extends R1> after) {
        Objects.requireNonNull(after, "after");
        return (t1, t2) -> after.apply(apply(t1, t2));
    }

    /**
     * Internally performs a try/catch on this, and changes the return type to a
     * {@link Result} wrapper.
     *
     * @return {@link Function2} with the return type with a {@link Result} wrapper
     */
    default Function2<T1, T2, Result<R>> lift() {
        return (t1, t2) -> {
            try {
                return Result.success(apply(t1, t2));
            } catch (Exception e) {
                return Result.failure(e);
            }
        };
    }
}
