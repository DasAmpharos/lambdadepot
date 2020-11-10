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

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A null-safe, reusable property getter.
 *
 * @param <I> the input object type
 * @param <O> the output property type
 */
public final class SafeGetter<I, O> {
    /**
     * Getter implementation.
     */
    private final Function<I, Option<O>> getter;

    /**
     * Internal constructor. Should only be created using the factory method {@link #of(Function)}.
     *
     * @param getter the getter implementation
     */
    private SafeGetter(Function<I, Option<O>> getter) {
        this.getter = getter;
    }

    /**
     * {@code SafeGetter} factory method. This is the starting point to defining a {@code SafeGetter}.
     *
     * @param type the input class type
     * @param <T>  the input type
     * @return a new {@code SafeGetter} that returns the input wrapped as {@link Option}
     * @throws NullPointerException if {@code type} is null
     */
    @NonNull
    public static <T> SafeGetter<T, T> of(Class<T> type) {
        Objects.requireNonNull(type, "type");
        return new SafeGetter<>(Option::ofNullable);
    }

    /**
     * {@code SafeGetter} factory method. This is the starting point to defining a {@code SafeGetter}.
     *
     * @param getter the initial getter implementation
     * @param <I>    the input object type
     * @param <O>    the output property type
     * @return a new {@code SafeGetter} using the given {@code getter} implementation
     * @throws NullPointerException if {@code getter} is null
     */
    @NonNull
    public static <I, O> SafeGetter<I, O> of(@NonNull Function<I, O> getter) {
        Objects.requireNonNull(getter, "getter");
        return new SafeGetter<>(i -> Option.ofNullable(i).map(getter));
    }

    /**
     * Defines when a property is safe or not to access.
     *
     * @param filter the filter implementation
     * @return a {@code SafeGetter} that incorporates {@code filter}
     * @throws NullPointerException if {@code filter} is null
     */
    @NonNull
    public SafeGetter<I, O> when(@NonNull Predicate<O> filter) {
        Objects.requireNonNull(filter, "filter");
        return new SafeGetter<>(i -> this.getter.apply(i).filter(filter));
    }

    /**
     * Defines the next step in the property accessor chain.
     *
     * @param nextGetter the next getter implementation
     * @param <U>        the new output property type
     * @return a {@code SafeGetter} that incorporates {@code nextGetter}
     * @throws NullPointerException if {@code nextGetter} is null
     * @see #then(SafeGetter)
     */
    @NonNull
    public <U> SafeGetter<I, U> then(@NonNull Function<O, U> nextGetter) {
        Objects.requireNonNull(nextGetter, "nextGetter");
        return new SafeGetter<>(i -> this.getter.apply(i).map(nextGetter));
    }

    /**
     * Defines the next step in the property accessor chain.
     *
     * @param nextGetter the next getter implementation
     * @param <V>        the new output property type
     * @return a {@code SafeGetter} that incorporates {@code nextGetter}
     * @throws NullPointerException if {@code nextGetter} is null
     * @see #then(Function)
     */
    @NonNull
    public <V> SafeGetter<I, V> then(@NonNull SafeGetter<O, V> nextGetter) {
        Objects.requireNonNull(nextGetter, "nextGetter");
        return new SafeGetter<>(i -> this.getter.apply(i).flatMap(nextGetter::get));
    }

    /**
     * Creates a {@link SafeSetter} that uses the current safe accessor chain
     * to get the {@code R} property type and sets the {@code U} property
     * type.
     *
     * @param setter the setter implementation
     * @param <V>    the value type to set
     * @return a {@code SafeSetter}
     * @throws NullPointerException if {@code setter} is null
     */
    @NonNull
    public <V> SafeSetter<I, O, V> setWith(@NonNull BiConsumer<O, V> setter) {
        Objects.requireNonNull(setter, "setter");
        return new SafeSetter<>(this, setter);
    }

    /**
     * Executes the safe access chain to extract the target property from {@code in}.
     *
     * <p>The extracted target property is wrapped in an {@link Option}.
     *
     * @param i the input object
     * @return an {@code Option}-wrapped instance of the extracted target property. If
     * the property is safe to access, then a present {@code Option} is returned. If the
     * property is not safe to access, then an empty {@code Option} is returned.
     */
    @NonNull
    public Option<O> get(@Nullable I i) {
        return getter.apply(i);
    }
}
