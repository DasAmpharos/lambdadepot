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

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

final class OptionEmpty<T> extends Option<T> {

    /**
     * Singleton "EMPTY" instance.
     */
    static final Option<?> EMPTY = new OptionEmpty<>();

    private OptionEmpty() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<T> toOptional() {
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> Option<U> map(Function<T, U> mapper) {
        return empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> Option<U> flatMap(Function<T, Option<U>> mapper) {
        return empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<T> filter(Predicate<T> predicate) {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void ifPresent(Consumer<T> whenPresent) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void ifPresentOrElse(Consumer<T> whenPresent, Runnable whenEmpty) {
        Objects.requireNonNull(whenEmpty, "whenEmpty");
        whenEmpty.run();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void ifEmpty(Runnable whenEmpty) {
        Objects.requireNonNull(whenEmpty, "whenEmpty");
        whenEmpty.run();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<T> peekIfPresent(Consumer<? super T> whenPresent) {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<T> peekIfPresentOrElse(Consumer<? super T> whenPresent, Runnable whenEmpty) {
        Objects.requireNonNull(whenEmpty, "whenEmpty");
        whenEmpty.run();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<T> peekIfEmpty(Runnable whenEmpty) {
        Objects.requireNonNull(whenEmpty, "whenEmpty");
        whenEmpty.run();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPresent() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<T> stream() {
        return Stream.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get() {
        throw new NoSuchElementException("No value present");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getOrElse(T other) {
        return other;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getOrElse(Supplier<? extends T> other) {
        Objects.requireNonNull(other, "other");
        return other.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getOrNull() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <X extends Throwable> T getOrElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        Objects.requireNonNull(exceptionSupplier, "exceptionSupplier");
        throw exceptionSupplier.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<T> orElse(Option<? extends T> other) {
        Objects.requireNonNull(other, "other");
        return (Option<T>) other;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Option<T> orElse(Supplier<? extends Option<? extends T>> other) {
        Objects.requireNonNull(other, "other");
        return (Option<T>) Objects.requireNonNull(other.get(), "other#get");
    }
}
