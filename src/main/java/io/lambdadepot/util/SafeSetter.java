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

import java.util.function.BiConsumer;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A null-safe, reusable property setter.
 *
 * @param <I> the input object type
 * @param <O> the output property type
 * @param <V> the value property type
 */
public final class SafeSetter<I, O, V> {
    /**
     * Getter implementation.
     */
    private final SafeGetter<I, O> getter;

    /**
     * Setter implementation.
     */
    private final BiConsumer<O, V> setter;

    /**
     * Internal constructor. Should only be created using {@link SafeGetter#setWith(BiConsumer)}.
     *
     * @param getter the getter implementation
     * @param setter the setter implementation
     */
    SafeSetter(@NonNull SafeGetter<I, O> getter, @NonNull BiConsumer<O, V> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    /**
     * Executes the safe access chain to extract the target property from {@code in}
     * and attempts to set {@code value} if the target property is safe to access.
     *
     * @param i the input object
     * @param v the new value
     * @return {@code true} if the property was set; {@code false} otherwise
     */
    public boolean set(@Nullable I i, @Nullable V v) {
        Option<O> out = getter.get(i);
        out.ifPresent(o -> setter.accept(o, v));
        return out.isPresent();
    }
}
