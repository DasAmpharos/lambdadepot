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

package io.lambdadepot.util;

import io.lambdadepot.function.Consumer1;
import io.lambdadepot.function.Function1;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * A collection of methods to work with {@link Supplier}s.
 */
public final class Suppliers {

    private Suppliers() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Gets method reference/lambda as a Supplier instance.
     *
     * @param reference Supplier reference
     * @param <T>       the type of the input to the operation
     * @return method reference/lambda as a Supplier instance
     */
    public static <T> Supplier<T> of(Supplier<T> reference) {
        Objects.requireNonNull(reference, "reference");
        return reference;
    }
}
