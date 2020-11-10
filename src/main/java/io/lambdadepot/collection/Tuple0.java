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

package io.lambdadepot.collection;

import java.util.Objects;
import java.util.function.Function;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A tuple with zero components.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class Tuple0 {
    /**
     * The number of components this Tuple contains.
     */
    public static final int SIZE = 0;
    /**
     * Singleton instance.
     */
    private static final Tuple0 EMPTY = new Tuple0();

    private Tuple0() {
    }

    /**
     * Returns the singleton instance of {@link Tuple0}.
     *
     * @return {@link #EMPTY}
     */
    public static Tuple0 empty() {
        return EMPTY;
    }

    /**
     * Applies {@code transformer} to {@code this} and returns the output
     * of {@code transformer}.
     *
     * @param transformer the transformation function to apply to {@code this}
     * @param <O>         the output type
     * @return the output of {@code transformer}
     * @throws NullPointerException if {@code transformer} is null
     */
    public <O> O transform(@NonNull Function<Tuple0, O> transformer) {
        Objects.requireNonNull(transformer, "transformer");
        return transformer.apply(this);
    }

    /**
     * Returns the number of components this tuple can contain.
     *
     * @return {@link #SIZE}
     */
    public int getSize() {
        return SIZE;
    }
}
