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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.NonNull;

@SuppressWarnings("unchecked")
public final class ResultFailure<T> extends Result<T> {
    private final Throwable error;

    ResultFailure(Throwable error) {
        this.error = error;
    }

    @NonNull
    @Override
    public <R> Result<R> map(@NonNull Function<T, R> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return (Result<R>) this;
    }

    @NonNull
    @Override
    public <R> Result<R> flatMap(@NonNull Function<T, Result<R>> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return (Result<R>) this;
    }

    @NonNull
    @Override
    public Result<T> ifFailureResume(@NonNull Function<? super Throwable, Result<T>> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return mapper.apply(error);
    }

    @NonNull
    @Override
    public <E extends Throwable> Result<T> ifFailureResume(@NonNull Class<E> exceptionType, @NonNull Function<? super E, Result<T>> mapper) {
        Objects.requireNonNull(exceptionType, "exceptionType");
        Objects.requireNonNull(mapper, "mapper");
        return exceptionType.isInstance(error) ? mapper.apply(exceptionType.cast(error)) : this;
    }

    @NonNull
    @Override
    public Result<T> ifFailureResume(@NonNull Predicate<? super Throwable> predicate, @NonNull Function<? super Throwable, Result<T>> mapper) {
        Objects.requireNonNull(predicate, "predicate");
        Objects.requireNonNull(mapper, "mapper");
        return predicate.test(error) ? mapper.apply(error) : this;
    }

    @NonNull
    @Override
    public Result<T> ifFailureReturn(@NonNull T fallbackValue) {
        Objects.requireNonNull(fallbackValue, "fallbackValue");
        return success(fallbackValue);
    }

    @NonNull
    @Override
    public <E extends Throwable> Result<T> ifFailureReturn(@NonNull Class<E> exceptionType, @NonNull T fallbackValue) {
        Objects.requireNonNull(exceptionType, "exceptionType");
        Objects.requireNonNull(fallbackValue, "fallbackValue");
        return exceptionType.isInstance(error) ? success(fallbackValue) : this;
    }

    @NonNull
    @Override
    public Result<T> ifFailureReturn(@NonNull Predicate<? super Throwable> predicate, @NonNull T fallbackValue) {
        Objects.requireNonNull(predicate, "predicate");
        Objects.requireNonNull(fallbackValue, "fallbackValue");
        return predicate.test(error) ? success(fallbackValue) : this;
    }

    @NonNull
    @Override
    public Result<T> ifFailureReturn(@NonNull Supplier<T> fallbackValue) {
        Objects.requireNonNull(fallbackValue, "fallbackValue");
        return success(fallbackValue.get());
    }

    @NonNull
    @Override
    public <E extends Throwable> Result<T> ifFailureReturn(@NonNull Class<E> exceptionType, @NonNull Supplier<T> fallbackValue) {
        Objects.requireNonNull(exceptionType, "exceptionType");
        Objects.requireNonNull(fallbackValue, "fallbackValue");
        return exceptionType.isInstance(error) ? success(fallbackValue.get()) : this;
    }

    @NonNull
    @Override
    public Result<T> ifFailureReturn(@NonNull Predicate<? super Throwable> predicate, @NonNull Supplier<T> fallbackValue) {
        Objects.requireNonNull(predicate, "predicate");
        Objects.requireNonNull(fallbackValue, "fallbackValue");
        return predicate.test(error) ? success(fallbackValue.get()) : this;
    }

    @NonNull
    @Override
    public Result<T> peekIfSuccess(@NonNull Consumer<? super T> onSuccess) {
        Objects.requireNonNull(onSuccess, "onSuccess");
        return this;
    }

    @NonNull
    @Override
    public Result<T> peekIfFailure(@NonNull Consumer<? super Throwable> onFailure) {
        Objects.requireNonNull(onFailure, "onFailure");
        onFailure.accept(error);
        return this;
    }

    @NonNull
    @Override
    public <E extends Throwable> Result<T> peekIfFailure(@NonNull Class<E> exceptionType, @NonNull Consumer<? super E> onFailure) {
        Objects.requireNonNull(exceptionType, "exceptionType");
        Objects.requireNonNull(onFailure, "onFailure");
        if (exceptionType.isInstance(error)) {
            onFailure.accept(exceptionType.cast(error));
        }
        return this;
    }

    @NonNull
    @Override
    public Result<T> peekIfFailure(@NonNull Predicate<? super Throwable> predicate, @NonNull Consumer<? super Throwable> onFailure) {
        Objects.requireNonNull(predicate, "predicate");
        Objects.requireNonNull(onFailure, "onFailure");
        if (predicate.test(error)) {
            onFailure.accept(error);
        }
        return this;
    }

    @Override
    public void ifSuccess(@NonNull Consumer<T> successAction) {
        Objects.requireNonNull(successAction, "successAction");
    }

    @Override
    public void ifSuccessOrElse(@NonNull Consumer<T> successAction, @NonNull Consumer<Throwable> failureAction) {
        Objects.requireNonNull(successAction, "successAction");
        Objects.requireNonNull(failureAction, "failureAction");
        failureAction.accept(error);
    }

    @Override
    public void ifFailure(@NonNull Consumer<Throwable> failureAction) {
        Objects.requireNonNull(failureAction, "failureAction");
        failureAction.accept(error);
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    public T getValue() {
        return null;
    }

    @Override
    public Throwable getException() {
        return error;
    }
}
