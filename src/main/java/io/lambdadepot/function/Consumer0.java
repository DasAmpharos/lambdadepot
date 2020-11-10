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
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents an operation that accepts zero input argument and returns no
 * result. This is the zero-arity specialization of {@link Consumer1}.
 * Unlike most other functional interfaces, {@code Consumer0} is expected
 * to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept()}.
 *
 * @see Consumer1
 */
@FunctionalInterface
public interface Consumer0 {

    /**
     * Gets a method reference/lambda expression as a Consumer0 instance.
     *
     * @param reference Consumer reference
     * @return method reference/lambda expression as a Consumer0 instance
     * @throws NullPointerException if reference is null
     */
    @NonNull
    static Consumer0 of(@NonNull Consumer0 reference) {
        Objects.requireNonNull(reference, "reference");
        return reference;
    }

    /**
     * Performs this operation.
     */
    void accept();

    /**
     * Returns a composed {@code Consumer0} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer0} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    @NonNull
    default Consumer0 andThen(@NonNull Consumer0 after) {
        Objects.requireNonNull(after, "after");
        return () -> {
            accept();
            after.accept();
        };
    }
}
