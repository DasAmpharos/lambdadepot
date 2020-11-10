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

/**
 * Represents an operation that accepts three input arguments and returns no
 * result. This is the three-arity specialization of {@link Consumer1}.
 * Unlike most other functional interfaces, {@code Consumer3} is expected
 * to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, Object, Object)}.
 *
 * @param <T1> the type of the first argument to the operation
 * @param <T2> the type of the second argument to the operation
 * @param <T3> the type of the third argument to the operation
 * @see Consumer1
 */
@FunctionalInterface
public interface Consumer3<T1, T2, T3> {

    /**
     * Gets a method reference/lambda expression as a Consumer3 instance.
     *
     * @param reference Consumer reference
     * @param <T1>      the type of the first input to the operation
     * @param <T2>      the type of the second input to the operation
     * @param <T3>      the type of the third input to the operation
     * @return method reference/lambda expression as a Consumer3 instance
     * @throws NullPointerException if reference is null
     */
    static <T1, T2, T3> Consumer3<T1, T2, T3> of(Consumer3<T1, T2, T3> reference) {
        Objects.requireNonNull(reference, "reference");
        return reference;
    }

    /**
     * Performs this operation on the given arguments.
     *
     * @param t1 the first input argument
     * @param t2 the second input argument
     * @param t3 the third input argument
     */
    void accept(T1 t1, T2 t2, T3 t3);

    /**
     * A partial application of {@code t1} to the consumer.
     *
     * @param t1 the first argument for the operation
     * @return Consumer2 instance expecting the second and third arguments
     */
    default Consumer2<T2, T3> partialApply(T1 t1) {
        return (t2, t3) -> accept(t1, t2, t3);
    }

    /**
     * A partial application of {@code t1} and {@code t2} to the consumer.
     *
     * @param t1 the first argument for the operation
     * @param t2 the second argument for the operation
     * @return Consumer1 instance expecting the third argument
     */
    default Consumer1<T3> partialApply(T1 t1, T2 t2) {
        return t3 -> accept(t1, t2, t3);
    }

    /**
     * A partial application of {@code t1}, {@code t2} and {@code t3} to the consumer.
     *
     * @param t1 the first argument for the operation
     * @param t2 the second argument for the operation
     * @param t3 the third argument for the operation
     * @return Consumer0 instance expecting the no arguments
     */
    default Consumer0 partialApply(T1 t1, T2 t2, T3 t3) {
        return () -> accept(t1, t2, t3);
    }

    /**
     * Returns a composed {@code Consumer3} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer3} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default Consumer3<T1, T2, T3> andThen(Consumer3<? super T1, ? super T2, ? super T3> after) {
        Objects.requireNonNull(after, "after");
        return (t1, t2, t3) -> {
            accept(t1, t2, t3);
            after.accept(t1, t2, t3);
        };
    }

    /**
     * Returns a consumer that accepts the arguments to this consumer
     * in reversed order.
     *
     * @return a consumer that accepts the arguments to this consumer
     * in reversed order
     */
    default Consumer3<T3, T2, T1> reverse() {
        return (t3, t2, t1) -> accept(t1, t2, t3);
    }
}
