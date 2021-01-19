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
import java.util.function.BiPredicate;

/**
 * Represents a predicate (boolean-valued function) of two arguments.  This is
 * the two-arity specialization of {@link Predicate1}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(Object, Object)}.
 *
 * <p>Extends {@link BiPredicate} to provide additional helper methods.
 *
 * @param <T1> the type of the first argument to the predicate
 * @param <T2> the type of the second argument the predicate
 * @see Predicate1
 */
@FunctionalInterface
public interface Predicate2<T1, T2> {

    /**
     * Gets a method reference/lambda expression as a Predicate2 instance.
     *
     * @param reference Predicate reference
     * @param <T1>      the type of the input to the predicate
     * @param <T2>      the type of the second argument to the predicate
     * @return method reference/lambda expression as a Predicate2 instance
     * @throws NullPointerException if reference is null
     */
    static <T1, T2> Predicate2<T1, T2> of(Predicate2<T1, T2> reference) {
        Objects.requireNonNull(reference, "reference");
        return reference;
    }

    /**
     * Converts a BiPredicate instance to a Predicate2.
     *
     * <p>Provides interop with existing BiPredicate instances.
     *
     * @param predicate the BiPredicate instance to convert
     * @param <T1>      the type of the first argument to the predicate
     * @param <T2>      the type of the second argument the predicate
     * @return {@code predicate} as a Predicate2 instance
     * @throws NullPointerException if predicate is null
     */
    static <T1, T2> Predicate2<T1, T2> asPredicate2(BiPredicate<T1, T2> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        return predicate::test;
    }

    boolean test(T1 t1, T2 t2);

    /**
     * A partial application of {@code t1} to the predicate.
     *
     * @param t1 the first argument to test with the predicate
     * @return Predicate1 instance expecting the second argument to the predicate
     */
    default Predicate1<T2> partialApply(T1 t1) {
        return t2 -> test(t1, t2);
    }

    /**
     * A partial application of {@code t1} and {@code t2} to the predicate.
     *
     * @param t1 the first argument to test with the predicate
     * @param t2 the second argument to test with the predicate
     * @return Predicate0 instance expecting no arguments
     */
    default Predicate0 partialApply(T1 t1, T2 t2) {
        return () -> test(t1, t2);
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
    default Predicate2<T1, T2> and(BiPredicate<? super T1, ? super T2> other) {
        Objects.requireNonNull(other, "other");
        return (t1, t2) -> test(t1, t2) && other.test(t1, t2);
    }

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     *
     * @return a predicate that represents the logical negation of this
     * predicate
     */
    default Predicate2<T1, T2> negate() {
        return (t1, t2) -> !test(t1, t2);
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
    default Predicate2<T1, T2> or(BiPredicate<? super T1, ? super T2> other) {
        Objects.requireNonNull(other, "other");
        return (t1, t2) -> test(t1, t2) || other.test(t1, t2);
    }

    /**
     * Returns a predicate that accepts the arguments to this predicate
     * in reversed order.
     *
     * @return a predicate that accepts the arguments to this predicate
     * in reversed order
     */
    default Predicate2<T2, T1> reverse() {
        return (t2, t1) -> test(t1, t2);
    }

    default BiPredicate<T1, T2> toBiPredicate() {
        return this::test;
    }
}
