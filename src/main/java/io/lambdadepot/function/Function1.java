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
 * Represents a function that accepts one argument and produces a result.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object)}.
 *
 * <p>Extends {@link Function} to provide additional helper methods.
 *
 * @param <T1> the type of the input to the function
 * @param <R>  the type of the result of the function
 */
@FunctionalInterface
public interface Function1<T1, R> extends Function<T1, R> {

    /**
     * Gets method reference/lambda as a Function1 instance.
     *
     * @param reference Function reference
     * @param <T1>      the type of the first argument to the function
     * @param <R>       the type of the result of the function
     * @return method reference/lambda as a Function1 instance
     * @throws NullPointerException if reference is null
     */
    static <T1, R> Function1<T1, R> of(Function1<T1, R> reference) {
        Objects.requireNonNull(reference, "reference");
        return reference;
    }

    /**
     * Converts a Function instance to a Function1.
     *
     * <p>Provides interop with existing Function instances.
     *
     * @param function the Function instance to convert
     * @param <T1>     the type of the first argument to the function
     * @param <R>      the type of the result of the function
     * @return {@code function} as a Function1 instance
     * @throws NullPointerException if function is null
     */
    static <T1, R> Function1<T1, R> asFunction1(Function<T1, R> function) {
        Objects.requireNonNull(function, "function");
        return function::apply;
    }

    /**
     * A partial application of {@code t1} to the function.
     *
     * @param t1 the first argument
     * @return Function0 instance expecting no arguments
     */
    default Function0<R> partialApply(T1 t1) {
        return () -> apply(t1);
    }

    /**
     * Returns a function that always returns its input argument.
     *
     * @param <T1> the type of the input and output objects to the function
     * @return a function that always returns its input argument
     */
    static <T1> Function1<T1, T1> identity() {
        return t1 -> t1;
    }

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <T2>   the type of input to the {@code before} function, and to the
     *               composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     * @see #andThen(Function)
     */
    @Override
    default <T2> Function1<T2, R> compose(Function<? super T2, ? extends T1> before) {
        Objects.requireNonNull(before, "before");
        return t2 -> apply(before.apply(t2));
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
     * @see #compose(Function)
     */
    @Override
    default <R1> Function1<T1, R1> andThen(Function<? super R, ? extends R1> after) {
        Objects.requireNonNull(after, "after");
        return t1 -> after.apply(apply(t1));
    }

    /**
     * Internally performs a try/catch on this, and changes the return type to a
     * {@link Result} wrapper.
     *
     * @return {@link Function1} with the return type with a {@link Result} wrapper
     */
    default Function1<T1, Result<R>> lift() {
        return t1 -> {
            try {
                return Result.successOrEmpty(apply(t1));
            } catch (Exception e) {
                return Result.failure(e);
            }
        };
    }
}
