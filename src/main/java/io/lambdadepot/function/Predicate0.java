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
import java.util.function.Supplier;

/**
 * Represents a predicate (boolean-valued function) of zero arguments. This is
 * the zero-arity specialization of {@link Predicate1}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test()}.</p>
 *
 * @see Predicate1
 */
@FunctionalInterface
public interface Predicate0 {

    /**
     * Gets a method reference/lambda expression as a Predicate0 instance.
     *
     * @param reference Predicate reference
     * @return method reference/lambda expression as a Predicate0 instance
     * @throws NullPointerException if reference is null
     */
    static Predicate0 of(Predicate0 reference) {
        Objects.requireNonNull(reference, "reference");
        return reference;
    }

    /**
     * Converts a Supplier instance to a Predicate0.
     *
     * <p>Provides interop with existing Supplier instances.
     *
     * @param supplier the Supplier instance to convert
     * @return {@code supplier} as a Predicate0 instance
     * @throws NullPointerException if supplier is null
     */
    static Predicate0 asPredicate0(Supplier<Boolean> supplier) {
        Objects.requireNonNull(supplier, "supplier");
        return supplier::get;
    }

    /**
     * Evaluates this predicate.
     *
     * @return result of the evaluation
     */
    boolean test();

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
    default Predicate0 and(Predicate0 other) {
        Objects.requireNonNull(other, "other");
        return () -> test() && other.test();
    }

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     *
     * @return a predicate that represents the logical negation of this
     * predicate
     */
    default Predicate0 negate() {
        return () -> !test();
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
    default Predicate0 or(Predicate0 other) {
        Objects.requireNonNull(other, "other");
        return () -> test() || other.test();
    }
}
