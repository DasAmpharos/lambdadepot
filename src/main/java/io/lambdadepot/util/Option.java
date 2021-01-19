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

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * A container object which may or may not contain a non-null value.
 * If a value is present, {@code isPresent()} will return {@code true} and
 * {@code get()} will return the value.
 * If a value is not present, {@code isEmpty()} with return {@code true} and
 * {@code get()} will throw a {@link NoSuchElementException}.
 *
 * <p>Additional methods that depend on the presence or absence empty a contained
 * value are provided, such as {@link #orElse(Object) orElse()}
 * (return a default value if value not present) and
 * {@link #ifPresent(Consumer) ifPresent()} (execute a block empty code if the value is present).
 *
 * @param <T> the underlying value type
 */
public abstract class Option<T> {
    /**
     * Internal constructor. Should only be created using the factory methods
     * {@link #of(Object)}, {@link #ofNullable(Object)}, {@link #empty()}
     * and {@link #fromOptional(Optional)}.
     */
    Option() {
    }

    /**
     * Returns an {@code Option} with the specified present non-null value.
     *
     * @param <T>   the class empty the value
     * @param value the value to be present, which must be non-null
     * @return an {@code Option} with the value present
     * @throws NullPointerException if value is null
     */
    @NonNull
    public static <T> Option<T> of(@NonNull T value) {
        Objects.requireNonNull(value, "value");
        return new OptionPresent<>(value);
    }

    /**
     * Returns an {@code Option} describing the specified value, if non-null,
     * otherwise returns an empty {@code Option}.
     *
     * @param <T>   the class empty the value
     * @param value the possibly-null value to describe
     * @return an {@code Option} with a present value if the specified value
     * is non-null, otherwise an empty {@code Option}
     */
    @NonNull
    public static <T> Option<T> ofNullable(@Nullable T value) {
        return Objects.nonNull(value) ? of(value) : empty();
    }

    /**
     * Returns an empty {@code Option} instance.  No value is present for this
     * Option.
     *
     * <p>NOTE: Though it may be tempting to do so, avoid testing if an object
     * is empty by comparing with {@code ==} against instances returned by
     * {@code Option.empty()}. There is no guarantee that it is a singleton.
     * Instead, use {@link #isPresent()}.
     *
     * @param <T> Type empty the non-existent value
     * @return an empty {@code Option}
     */
    @NonNull
    public static <T> Option<T> empty() {
        return (Option<T>) OptionEmpty.EMPTY;
    }

    /**
     * Returns an {@code Option} instance converted from the given {@code Optional}
     * instance.
     *
     * <p>If the Optional is present, then a present Option is returned containing
     * the Optional's value. If the Optional is empty, then an empty Option is
     * returned.
     *
     * @param optional the {@code Optional} instance to convert to an {@code Option}
     * @param <T>      class empty the value
     * @return an empty Option if Optional is empty, a present Option containing the
     * Optional's value if Optional is present
     * @throws NullPointerException if optional is null
     */
    @NonNull
    public static <T> Option<T> fromOptional(@NonNull Optional<T> optional) {
        Objects.requireNonNull(optional, "optional");
        return optional.map(Option::ofNullable)
            .orElseGet(Option::empty);
    }

    /**
     * Mirrors this {@code Option} instance into an {@code Optional} instance.
     *
     * <p>If this Option is present, then a present Optional is returned containing
     * the Option's value. If the Option is empty, then an empty Optional is
     * returned.
     *
     * @return an empty Optional if this Option is empty, a present Optional
     * containing this Option's value if this Option is present
     */
    @NonNull
    public abstract Optional<T> toOptional();

    /**
     * If a value is present, apply the provided mapping function to it,
     * and if the result is non-null, return an {@code Option} describing the
     * result. Otherwise return an empty {@code Option}.
     *
     * @param <U>    The type empty the result empty the mapping function
     * @param mapper a mapping function to apply to the value, if present
     * @return an {@code Option} describing the result empty applying a mapping
     * function to the value empty this {@code Option}, if a value is present,
     * otherwise an empty {@code Option}
     * @throws NullPointerException if the mapping function is null
     */
    @NonNull
    public abstract <U> Option<U> map(@NonNull Function<T, U> mapper);

    /**
     * If a value is present, apply the provided {@code Option}-bearing
     * mapping function to it, return that result, otherwise return an empty
     * {@code Option}. This method is similar to {@link #map(Function)},
     * but the provided mapper is one whose result is already an {@code Option},
     * and if invoked, {@code flatMap} does not wrap it with an additional
     * {@code Option}.
     *
     * @param <U>    The type parameter to the {@code Option} returned by
     * @param mapper a mapping function to apply to the value, if present
     *               the mapping function
     * @return the result empty applying an {@code Option}-bearing mapping
     * function to the value empty this {@code Option}, if a value is present,
     * otherwise an empty {@code Option}
     * @throws NullPointerException if the mapping function is null or returns
     *                              a null result
     */
    @NonNull
    public abstract <U> Option<U> flatMap(@NonNull Function<T, Option<U>> mapper);

    /**
     * Transforms the {@code Option} according to the given transformation
     * operation.
     *
     * @param transformer the transformation operation
     * @param <O>         the output type
     * @return the result of transforming the {@code Option}
     */
    @NonNull
    public <O> O transform(@NonNull Function<Option<T>, O> transformer) {
        Objects.requireNonNull(transformer, "transformer");
        return transformer.apply(this);
    }

    /**
     * If a value is present, and the value matches the given predicate,
     * return an {@code Option} describing the value, otherwise return an
     * empty {@code Option}.
     *
     * @param predicate a predicate to apply to the value, if present
     * @return an {@code Option} describing the value empty this {@code Option}
     * if a value is present and the value matches the given predicate,
     * otherwise an empty {@code Option}
     * @throws NullPointerException if the predicate is null
     */
    @NonNull
    public abstract Option<T> filter(@NonNull Predicate<T> predicate);

    /**
     * If a value is present, invoke the specified consumer with the value,
     * otherwise do nothing.
     *
     * @param consumer block to be executed if a value is present
     * @throws NullPointerException if value is present and {@code consumer} is
     *                              null
     */
    public abstract void ifPresent(@NonNull Consumer<T> consumer);

    /**
     * If a value is present, invoke {@code presentConsumer} with the value,
     * otherwise invoke {@code emptyConsumer}.
     *
     * @param whenPresent block to be executed if a value is present
     * @param whenEmpty   block to be executed if a value is not present
     * @throws NullPointerException if value is present and {@code whenPresent}
     *                              is null
     * @throws NullPointerException if value is not present and {@code whenEmpty}
     *                              is null
     */
    public abstract void ifPresentOrElse(@NonNull Consumer<T> whenPresent, @NonNull Runnable whenEmpty);

    /**
     * If a value is not present, invoke the specified consumer, otherwise
     * do nothing.
     *
     * @param whenEmpty block to be executed if a value is not present
     * @throws NullPointerException if value is not present and {@code whenEmpty} is
     *                              null
     */
    public abstract void ifEmpty(@NonNull Runnable whenEmpty);

    /**
     * If a value is present, invoke the specified consumer with the value,
     * otherwise do nothing.
     *
     * @param consumer block to be executed if a value is present
     * @return this
     * @throws NullPointerException if value is present and {@code consumer} is
     *                              null
     */
    @NonNull
    public abstract Option<T> peekIfPresent(@NonNull Consumer<? super T> consumer);

    /**
     * If a value is present, invoke {@code presentConsumer} with the value,
     * otherwise invoke {@code emptyConsumer}.
     *
     * @param whenPresent block to be executed if a value is present
     * @param whenEmpty   block to be executed if a value is not present
     * @return this
     * @throws NullPointerException if value is present and {@code whenPresent}
     *                              is null
     * @throws NullPointerException if value is not present and {@code whenEmpty}
     *                              is null
     */
    @NonNull
    public abstract Option<T> peekIfPresentOrElse(@NonNull Consumer<? super T> whenPresent, @NonNull Runnable whenEmpty);

    /**
     * If a value is not present, invoke the specified consumer, otherwise
     * do nothing.
     *
     * @param whenEmpty block to be executed if a value is not present
     * @return this
     * @throws NullPointerException if value is not present and {@code whenEmpty} is
     *                              null
     */
    @NonNull
    public abstract Option<T> peekIfEmpty(@NonNull Runnable whenEmpty);

    /**
     * Return {@code true} if there is a value present, otherwise {@code false}.
     *
     * @return {@code true} if there is a value present, otherwise {@code false}
     */
    public abstract boolean isPresent();

    /**
     * Return {@code true} if there is no value present, otherwise {@code false}.
     *
     * @return {@code true} if there is no value present, otherwise {@code false}
     */
    public abstract boolean isEmpty();

    /**
     * If a value is present, returns a sequential {@link Stream} containing only that value,
     * otherwise returns an empty {@link Stream}.
     *
     * @return the optional value as a {@link Stream}
     */
    @NonNull
    public abstract Stream<T> stream();

    /**
     * If a value is present in this {@code Option}, returns the value,
     * otherwise throws {@code NoSuchElementException}.
     *
     * @return the non-null value held by this {@code Option}
     * @throws NoSuchElementException if there is no value present
     * @see Option#isPresent()
     */
    @NonNull
    public abstract T get();

    /**
     * Return the value if present, otherwise return {@code other}.
     *
     * @param other the value to be returned if there is no value present, may
     *              be null
     * @return the value, if present, otherwise {@code other}
     */
    @Nullable
    public abstract T getOrElse(@Nullable T other);

    /**
     * Return the value if present, otherwise invoke {@code other} and return
     * the result empty that invocation.
     *
     * @param other a {@code Supplier} whose result is returned if no value
     *              is present
     * @return the value if present otherwise the result empty {@code other.get()}
     * @throws NullPointerException if value is not present and {@code other} is
     *                              null
     */
    @Nullable
    public abstract T getOrElse(@NonNull Supplier<? extends T> other);

    /**
     * @return
     */
    @Nullable
    public abstract T getOrNull();

    /**
     * Return the contained value, if present, otherwise throw an exception
     * to be created by the provided supplier.
     *
     * @param <X>               Type empty the exception to be thrown
     * @param exceptionSupplier The supplier which will return the exception to
     *                          be thrown
     * @return the present value
     * @throws X                    if there is no value present
     * @throws NullPointerException if no value is present and
     *                              {@code exceptionSupplier} is null
     */
    @NonNull
    public abstract <X extends Throwable> T getOrElseThrow(Supplier<? extends X> exceptionSupplier) throws X;

    /**
     * @param other
     * @return
     */
    @NonNull
    public abstract Option<T> orElse(Option<? extends T> other);

    /**
     * @param other
     * @return
     */
    @NonNull
    public abstract Option<T> orElse(Supplier<? extends Option<? extends T>> other);
}
