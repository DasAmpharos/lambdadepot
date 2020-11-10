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
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

final class OptionPresent<T> extends Option<T> {
    /**
     * Underlying value.
     */
    private final T value;

    OptionPresent(T value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> toOptional() {
        return Optional.of(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> Option<U> map(Function<T, U> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return Option.ofNullable(mapper.apply(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> Option<U> flatMap(Function<T, Option<U>> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return mapper.apply(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<T> filter(Predicate<T> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        return predicate.test(value) ? this : Option.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void ifPresent(Consumer<T> whenPresent) {
        Objects.requireNonNull(whenPresent, "whenPresent");
        whenPresent.accept(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void ifPresentOrElse(Consumer<T> whenPresent, Runnable whenEmpty) {
        Objects.requireNonNull(whenPresent, "whenPresent");
        whenPresent.accept(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void ifEmpty(Runnable whenEmpty) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<T> peekIfPresent(Consumer<? super T> whenPresent) {
        Objects.requireNonNull(whenPresent, "whenPresent");
        whenPresent.accept(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<T> peekIfPresentOrElse(Consumer<? super T> whenPresent, Runnable whenEmpty) {
        return peekIfPresent(whenPresent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<T> peekIfEmpty(Runnable whenEmpty) {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPresent() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<T> stream() {
        return Stream.of(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getOrElse(T other) {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getOrElse(Supplier<? extends T> other) {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getOrNull() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <X extends Throwable> T getOrElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<T> orElse(Option<? extends T> other) {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<T> orElse(Supplier<? extends Option<? extends T>> other) {
        return this;
    }
}
