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

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Represents an operation that accepts a single input argument and returns no
 * result. Unlike most other functional interfaces, {@code Consumer} is expected
 * to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object)}.
 *
 * <p>Extends {@link Consumer} to provide additional helper methods.
 *
 * @param <T1> the type of the input to the operation
 */
@FunctionalInterface
public interface Consumer1<T1> {

    /**
     * Gets a method reference/lambda expression as a Consumer1 instance.
     *
     * @param reference Consumer reference
     * @param <T1>      the type of the input to the operation
     * @return method reference/lambda expression as a Consumer1 instance
     * @throws NullPointerException if reference is null
     */
    @NonNull
    static <T1> Consumer1<T1> of(@NonNull Consumer1<T1> reference) {
        Objects.requireNonNull(reference, "reference");
        return reference;
    }

    /**
     * Converts a Consumer instance to a Consumer1.
     *
     * <p>Provides interop with existing Consumer instances.
     *
     * @param consumer the Consumer instance to convert
     * @param <T1>     the type of the input to the operation
     * @return consumer as a Consumer1 instance
     * @throws NullPointerException if consumer is null
     */
    static <T1> Consumer1<T1> asConsumer1(Consumer<T1> consumer) {
        Objects.requireNonNull(consumer, "consumer");
        return consumer::accept;
    }

    void accept(T1 t1);

    /**
     * A partial application of {@code t1} to the consumer.
     *
     * @param t1 the first argument for the operation
     * @return Consumer0 instance expecting no arguments
     */
    default Consumer0 partialApply(T1 t1) {
        return () -> accept(t1);
    }

    /**
     * Returns a composed {@code Consumer1} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer1} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    @NonNull
    default Consumer1<T1> thenAccept(@NonNull Consumer<? super T1> after) {
        Objects.requireNonNull(after, "after");
        return t -> {
            accept(t);
            after.accept(t);
        };
    }

    default Consumer<T1> toConsumer() {
        return this::accept;
    }
}
