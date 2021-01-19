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

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Represents a predicate (boolean-valued function) of one argument.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(Object)}.
 *
 * <p>Extends {@link Predicate} to provide additional helper methods.
 *
 * @param <T1> the type of the input to the predicate
 */
@FunctionalInterface
public interface Predicate1<T1> extends Predicate<T1> {

    /**
     * Gets a method reference/lambda expression as a Predicate1 instance.
     *
     * @param reference Predicate reference
     * @param <T1>      the type of the input to the predicate
     * @return method reference/lambda expression as a Predicate1 instance
     * @throws NullPointerException if reference is null
     */
    static <T1> Predicate1<T1> of(Predicate1<T1> reference) {
        Objects.requireNonNull(reference, "reference");
        return reference;
    }

    /**
     * Converts a Predicate instance to a Predicate1.
     *
     * <p>Provides interop with existing Predicate instances.
     *
     * @param predicate the Predicate instance to convert
     * @param <T1>      the type of the input to the predicate
     * @return {@code predicate} as a Predicate1 instance
     * @throws NullPointerException if predicate is null
     */
    static <T1> Predicate1<T1> asPredicate1(Predicate<T1> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        return predicate::test;
    }

    boolean test(T1 t1);

    /**
     * A partial application of {@code t} to the predicate.
     *
     * @param t1 the first argument to test with the predicate
     * @return Predicate0 instance expecting no arguments
     */
    default Predicate0 partialApply(T1 t1) {
        return () -> test(t1);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * AND of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code false}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ANDed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * AND of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default Predicate1<T1> and(Predicate<? super T1> other) {
        Objects.requireNonNull(other, "other");
        return t1 -> test(t1) && other.test(t1);
    }

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     *
     * @return a predicate that represents the logical negation of this
     * predicate
     */
    default Predicate1<T1> negate() {
        return t1 -> !test(t1);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * OR of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code true}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * OR of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default Predicate1<T1> or(Predicate<? super T1> other) {
        Objects.requireNonNull(other, "other");
        return t1 -> test(t1) || other.test(t1);
    }

    class Composer<T1> {
        private final Predicate1<T1> predicate;

        Composer(Predicate1<T1> predicate) {
            this.predicate = predicate;
        }

        public <R> Function1.Composer<T1, R> thenApply(Function1<Boolean, R> function) {
            return new Function1.Composer<>(t1 -> function.apply(predicate.test(t1)));
        }

        public Consumer1.Composer<T1> thenAccept(Consumer1<Boolean> consumer) {
            return new Consumer1.Composer<>(t1 -> consumer.accept(predicate.test(t1)));
        }

        public Predicate1<T1> build() {
            return predicate;
        }
    }
}
