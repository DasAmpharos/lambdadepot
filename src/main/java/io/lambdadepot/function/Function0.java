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
import java.util.function.Supplier;

/**
 * Represents a function that accepts zero arguments and produces a result.
 * This is the zero-arity specialization of {@link Function1}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply()}.
 *
 * @param <R> the type of the result of the function
 * @see Function1
 */
@FunctionalInterface
public interface Function0<R> extends Supplier<R> {

    /**
     * Gets method reference/lambda as a Function0 instance.
     *
     * @param reference Function reference
     * @param <R>       the type of the result of the function
     * @return method reference/lambda as a Function0 instance
     * @throws NullPointerException if reference is null
     */
    static <R> Function0<R> of(Function0<R> reference) {
        Objects.requireNonNull(reference, "reference");
        return reference;
    }

    /**
     * Converts a Supplier instance to a Function0.
     *
     * <p>Provides interop with existing Supplier instances.
     *
     * @param supplier the Supplier instance to convert
     * @param <R>      the type of the result of the function
     * @return {@code supplier} as a Function0 instance
     * @throws NullPointerException if supplier is null
     */
    static <R> Function0<R> asFunction0(Supplier<R> supplier) {
        Objects.requireNonNull(supplier, "supplier");
        return supplier::get;
    }

    /**
     * Converts the given value as a Function0 instance.
     *
     * @param value the value to return when {@link #apply()} is called
     * @param <R>   the type of the result of the function
     * @return {@code value}
     */
    static <R> Function0<R> just(R value) {
        return () -> value;
    }

    /**
     * Gets the result.
     *
     * @return result of {@link #apply()}
     * @see #apply()
     */
    @Override
    default R get() {
        return apply();
    }

    /**
     * Applies this function.
     *
     * @return the function result
     */
    R apply();

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
    default <R1> Function0<R1> andThen(Function<? super R, ? extends R1> after) {
        Objects.requireNonNull(after, "after");
        return () -> after.apply(apply());
    }

    /**
     * Internally performs a try/catch on this, and changes the return type to a
     * {@link Result} wrapper.
     *
     * @return {@link Function0} with the return type with a {@link Result} wrapper
     */
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
