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
import java.util.function.BiConsumer;

/**
 * Represents an operation that accepts two input arguments and returns no
 * result. This is the two-arity specialization of {@link Consumer1}.
 * Unlike most other functional interfaces, {@code Consumer2} is expected
 * to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, Object)}.
 *
 * <p>Extends {@link BiConsumer} to provide additional helper methods.
 *
 * @param <T1> the type of the first argument to the operation
 * @param <T2> the type of the second argument to the operation
 * @see Consumer1
 */
@FunctionalInterface
public interface Consumer2<T1, T2> extends BiConsumer<T1, T2> {

    /**
     * Gets a method reference/lambda expression as a Consumer2 instance.
     *
     * @param reference Consumer reference
     * @param <T1>      the type of the first input to the operation
     * @param <T2>      the type of the second input to the operation
     * @return method reference/lambda expression as a Consumer2 instance
     * @throws NullPointerException if reference is null
     */
    static <T1, T2> Consumer2<T1, T2> of(Consumer2<T1, T2> reference) {
        Objects.requireNonNull(reference, "reference");
        return reference;
    }

    /**
     * Converts a BiConsumer instance to a Consumer2.
     *
     * <p>Provides interop with existing BiConsumer instances.
     *
     * @param consumer the BiConsumer instance to convert
     * @param <T1>     the type of the first input to the operation
     * @param <T2>     the type of the second input to the operation
     * @return consumer as a Consumer2 instance
     * @throws NullPointerException if consumer is null
     */
    static <T1, T2> Consumer2<T1, T2> asConsumer2(BiConsumer<T1, T2> consumer) {
        Objects.requireNonNull(consumer, "consumer");
        return consumer::accept;
    }

    /**
     * A partial application of {@code t1} to the consumer.
     *
     * @param t1 the first argument for the operation
     * @return Consumer1 instance expecting the second argument
     */
    default Consumer1<T2> partialApply(T1 t1) {
        return t2 -> accept(t1, t2);
    }

    /**
     * A partial application of {@code t1} and {@code t2} to the consumer.
     *
     * @param t1 the first argument for the operation
     * @param t2 the second argument for the operation
     * @return Consumer0 instance expecting no arguments
     */
    default Consumer0 partialApply(T1 t1, T2 t2) {
        return () -> accept(t1, t2);
    }

    /**
     * Returns a composed {@code Consumer2} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer2} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    @Override
    default Consumer2<T1, T2> andThen(BiConsumer<? super T1, ? super T2> after) {
        Objects.requireNonNull(after, "after");
        return (t1, t2) -> {
            accept(t1, t2);
            after.accept(t1, t2);
        };
    }

    /**
     * Returns a consumer that accepts the arguments to this consumer
     * in reversed order.
     *
     * @return a consumer that accepts the arguments to this consumer
     * in reversed order
     */
    default Consumer2<T2, T1> reverse() {
        return (t2, t1) -> accept(t1, t2);
    }
}
