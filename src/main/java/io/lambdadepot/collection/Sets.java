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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A collection of methods to assist operating on {@link java.util.Set}s
 * in a functional manner.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class Sets {
    private Sets() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a set consisting of the results of applying the given
     * function to the elements of this set.
     *
     * @param mapper function to be applied
     * @param <T>    source type
     * @param <R>    result type
     * @return {@link Set} of mapped result type
     */
    public static <T, R> Set<R> map(Set<T> set, Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(set, "set");
        Objects.requireNonNull(mapper, "mapper");
        final Set<R> newSet = new HashSet<>(set.size());
        set.forEach(it -> newSet.add(mapper.apply(it)));
        return newSet;
    }

    /**
     * Returns a set consisting of the results of replacing each element of
     * this set with the contents of a mapped set produced by applying
     * the provided mapping function to each element.
     *
     * @param mapper function to be applied
     * @param <T>    source type
     * @param <R>    result type
     * @return {@link Set} of mapped result type
     */
    public static <T, R> Set<R> flatMap(Set<T> set, Function<? super T, Set<? extends R>> mapper) {
        Objects.requireNonNull(set, "set");
        Objects.requireNonNull(mapper, "mapper");
        final Set<R> newSet = new HashSet<>();
        set.forEach(it -> newSet.addAll(mapper.apply(it)));
        return newSet;
    }

    /**
     * Returns a set consisting of the elements of this set that match
     * the given predicate.
     *
     * @param predicate predicate to apply to each element to determine if it
     *                  should be included
     * @param <T>       element type of the set
     * @return set containing only the elements that match the provided predicate
     */
    public static <T> Set<T> filter(Set<T> set, Predicate<? super T> predicate) {
        Objects.requireNonNull(set, "set");
        Objects.requireNonNull(predicate, "predicate");
        final HashSet<T> newSet = new HashSet<>(set.size());
        set.forEach(it -> {
            if (predicate.test(it)) {
                newSet.add(it);
            }
        });
        return newSet;
    }
}
