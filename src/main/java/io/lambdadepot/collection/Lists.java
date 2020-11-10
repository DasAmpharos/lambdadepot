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

import io.lambdadepot.util.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A collection of methods to assist operating on {@link java.util.List}s
 * in a functional manner.
 */
public final class Lists {
    private Lists() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a list consisting of the results of applying the given
     * function to the elements of this list.
     *
     * @param list   the list to map
     * @param mapper mapping function to apply to each element
     * @param <T>    source type
     * @param <R>    result type
     * @return {@link List} of mapped result type
     * @throws NullPointerException if {@code list} or {@code mapper} is {@literal null}
     */
    @NonNull
    public static <T, R> List<R> map(@NonNull List<T> list, @NonNull Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(list, "list");
        Objects.requireNonNull(mapper, "mapper");
        final List<R> newList = new ArrayList<>(list.size());
        list.forEach(it -> newList.add(mapper.apply(it)));
        return newList;
    }

    /**
     * Returns a list consisting of the results of replacing each element of
     * this list with the contents of a mapped list produced by applying
     * the provided mapping function to each element.
     *
     * @param list   the list to flatMap
     * @param mapper function to be applied
     * @param <T>    source type
     * @param <R>    result type
     * @return {@link List} of mapped result types
     * @throws NullPointerException if {@code list} or {@code mapper} is null
     */
    @NonNull
    public static <T, R> List<R> flatMap(@NonNull List<T> list, @NonNull Function<? super T, List<? extends R>> mapper) {
        Objects.requireNonNull(list, "list");
        Objects.requireNonNull(mapper, "mapper");
        final List<R> newList = new LinkedList<>();
        list.forEach(it -> newList.addAll(mapper.apply(it)));
        return new ArrayList<>(newList);
    }

    /**
     * Filters out any Objects in the list that do not return true from the {@code predicate} test.
     *
     * @param list      the list to filter
     * @param predicate supplied {@link Predicate} used to filter Objects in the list
     * @param <T>       source type
     * @return {@link List} of T that test true from the supplied {@code predicate}.
     * @throws NullPointerException if {@code list} or {@code predicate} is {@literal null}.
     */
    @NonNull
    public static <T> List<T> filter(@NonNull List<T> list, @NonNull Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        Objects.requireNonNull(list, "list");
        final LinkedList<T> filtered = new LinkedList<>();
        list.forEach(it -> {
            if (predicate.test(it)) {
                filtered.add(it);
            }
        });
        return new ArrayList<>(filtered);
    }

    /**
     * Returns the element at index 0 in {@code list}.
     * If {@code list.isEmpty()} returns {@literal true}, then
     * {@code Option.empty()} is returned.
     *
     * @param list the list to get the head element from
     * @param <T>  type of elements in the list
     * @return {@code Option#of(T)} or {@code Optional#empty()}
     * @throws NullPointerException if {@code list} is {@literal null}
     */
    @NonNull
    public static <T> Option<T> getHead(@NonNull List<T> list) {
        return get(list, 0);
    }

    /**
     * Attempts to get the last {@code T} from the {@code List<T>}.
     * A null input list is acceptable, and will just return empty.
     * <ul>
     * <li>getTail(null) = Optional.empty()</li>
     * <li>getTail(new ArrayList()) = Optional.empty()</li>
     * <li>getTail(listOfNullTees) = Optional.empty()</li>
     * <li>getTail(listOfNonNullTees) = Optional.of(teeFromIndexN)</li>
     * </ul>
     *
     * @param list for getting {@code List::get(size - 1)}
     * @param <T>  type of elements in the list
     * @return {@code Optional#of(T)} or {@code Optional#empty()}
     */
    @NonNull
    public static <T> Option<T> getTail(@NonNull List<T> list) {
        return get(list, list.size() - 1);
    }

    /**
     * Attempts to get {@code T} from the {@code List<T>} at {@code index}.
     * A null input list is acceptable, and will just return empty.
     * <ul>
     * <li>get(null, n) = Optional.empty()</li>
     * <li>get(new ArrayList(), n) = Optional.empty()</li>
     * <li>get(listOfNullTees, n) = Optional.empty()</li>
     * <li>get(listOfNonNullTees, n) = Optional.of(teeFromIndexN)</li>
     * </ul>
     *
     * @param list  for getting {@code List::get(n)}
     * @param index index of the element to return
     * @param <T>   type of elements in the list
     * @return {@code Optional#of(T)} or {@code Optional#empty()}
     */
    @NonNull
    public static <T> Option<T> get(@NonNull List<T> list, @NonNegative int index) {
        if (list.isEmpty() || index >= list.size()) {
            return Option.empty();
        }
        return Option.of(list.get(index));
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the specified
     * collection's iterator (optional operation).
     *
     * @param elements array containing elements to be added to this list
     * @param <T>      element type
     * @return a function that appends the given elements to a list
     */
    @NonNull
    @SafeVarargs
    public static <T> List<T> append(@NonNull List<T> list, T... elements) {
        return append(list, Arrays.asList(elements));
    }

    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the specified
     * collection's iterator (optional operation).
     *
     * @param elements collection containing elements to be added to this list
     * @param <T>      element type
     * @return a function that appends the given elements to a list
     */
    @NonNull
    public static <T> List<T> append(@NonNull List<T> list, @NonNull Collection<T> elements) {
        Objects.requireNonNull(elements, "elements");
        Objects.requireNonNull(list, "list");
        final List<T> newList = new ArrayList<>(list);
        newList.addAll(elements);
        return newList;
    }

    /**
     * Prepends all of the elements in the specified collection to the beginning of
     * this list, in the order that they are returned by the specified
     * collection's iterator (optional operation).
     *
     * @param elements array containing elements to be added to this list
     * @param <T>      element type
     * @return a function that prepends the given elements to a list
     */
    @NonNull
    @SafeVarargs
    public static <T> List<T> prepend(@NonNull List<T> list, T... elements) {
        return prepend(list, Arrays.asList(elements));
    }

    /**
     * Prepends all of the elements in the specified collection to the beginning of
     * this list, in the order that they are returned by the specified
     * collection's iterator (optional operation).
     *
     * @param elements collection containing elements to be added to this list
     * @param <T>      element type
     * @return a function that prepends the given elements to a list
     */
    @NonNull
    public static <T> List<T> prepend(@NonNull List<T> list, @NonNull Collection<T> elements) {
        Objects.requireNonNull(list, "list");
        Objects.requireNonNull(elements, "elements");
        final List<T> newList = new ArrayList<>(list);
        newList.addAll(0, elements);
        return newList;
    }
}
