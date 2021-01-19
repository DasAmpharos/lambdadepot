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
public interface Function0<R> {

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

    static <R> Function0.Composer<R> composer(Function0<R> function) {
        return new Composer<>(function);
    }

    /**
     * Applies this function.
     *
     * @return the function result
     */
    R apply();

    class Composer<R> {
        private final Function0<R> function;

        Composer(Function0<R> function) {
            this.function = function;
        }

        public <R1> Composer<R1> thenApply(Function1<R, R1> function) {
            return new Composer<>(() -> function.apply(this.function.apply()));
        }

        public Consumer0.Composer thenAccept(Consumer1<R> consumer) {
            return new Consumer0.Composer(() -> consumer.accept(function.apply()));
        }

        public Predicate0.Composer thenTest(Predicate1<R> predicate) {
            return new Predicate0.Composer(() -> predicate.test(function.apply()));
        }

        public Composer<Result<R>> lift() {
            return new Composer<>(() -> {
                try {
                    return Result.success(function.apply());
                } catch (Exception e) {
                    return Result.failure(e);
                }
            });
        }

        public Function0<R> build() {
            return function;
        }
    }
}
