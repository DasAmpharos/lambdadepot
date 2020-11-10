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
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A union type that encapsulates a successful result (of type {@code T})
 * or a failure result with a {@link Throwable}.
 *
 * @param <T> value type
 */
public abstract class Result<T> {
    Result() {
    }

    /**
     * Creates a new successful {@link Result} that wraps the given value.
     *
     * @param value the value to wrap
     * @param <T>   the value type
     * @return a successful {@link Result}
     * @throws NullPointerException if value is null
     */
    @NonNull
    public static <T> Result<T> success(@NonNull T value) {
        Objects.requireNonNull(value, "value");
        return new ResultSuccess<>(value);
    }

    /**
     * Creates a new failure {@link Result} that wraps the given exception.
     *
     * @param error the exception that occurred
     * @param <T>   the value type
     * @return a failure {@link Result}
     * @throws NullPointerException if error is null
     */
    @NonNull
    public static <T> Result<T> failure(@NonNull Throwable error) {
        Objects.requireNonNull(error, "error");
        return new ResultFailure<>(error);
    }

    /**
     * If this {@link Result} is success, apply the value to {@code mapper}
     * and return a {@link Result} wrapping the value returned from the
     * operation.
     *
     * @param mapper the function to apply to the value if this {@link Result}
     *               is successful
     * @param <R>    the {@link Result} value type returned from {@code mapper}
     * @return the value returned from applying this {@link Result}'s value to
     * {@code mapper} wrapped as a {@link Result}
     * @throws NullPointerException if mapper is null
     */
    @NonNull
    public abstract <R> Result<R> map(@NonNull Function<T, R> mapper);

    /**
     * If this {@link Result} is success, apply the value to {@code mapper}
     * and return the {@link Result} returned from the operation.
     *
     * @param mapper the function to apply to the value if this {@link Result}
     *               is successful
     * @param <R>    the {@link Result} value type returned from {@code mapper}
     * @return the {@link Result} returned from applying this {@link Result}'s
     * value to {@code mapper}
     * @throws NullPointerException if mapper is null
     */
    @NonNull
    public abstract <R> Result<R> flatMap(@NonNull Function<T, Result<R>> mapper);

    /**
     * Applies this {@link Result} to {@code transformer} and returns the value
     * returned from the operation.
     *
     * @param transformer the function to apply this {@link Result} to
     * @param <R>         the output type returned from applying {@code transformer}
     * @return the output returned from applying {@code transformer} to this
     */
    @Nullable
    public final <R> R transform(@NonNull Function<? super Result<T>, R> transformer) {
        Objects.requireNonNull(transformer, "transformer");
        return transformer.apply(this);
    }

    /**
     * If this {@link Result} is failure, apply {@code mapper} to the error and
     * continue with the {@link Result} returned from the operation.
     *
     * @param mapper the function to apply to the error if this {@link Result}
     *               is failure
     * @return the {@link Result} returned from applying this {@link Result}'s error
     * to {@code mapper}
     * @throws NullPointerException if mapper is null
     */
    @NonNull
    public abstract Result<T> ifFailureResume(@NonNull Function<? super Throwable, Result<T>> mapper);

    /**
     * If this {@link Result} is failure and the error matches the given exception type,
     * apply {@code mapper} to the error and continue with the {@link Result} returned
     * from the operation.
     *
     * @param exceptionType the exception type to map
     * @param mapper        the function to apply to the error if this {@link Result}
     *                      is failure and the error matches the given exception type
     * @param <E>           exception type
     * @return the {@link Result} returned from applying this {@link Result}'s error
     * to {@code mapper}
     * @throws NullPointerException if exceptionType is null
     * @throws NullPointerException if mapper is null
     */
    @NonNull
    public abstract <E extends Throwable> Result<T> ifFailureResume(@NonNull Class<E> exceptionType, @NonNull Function<? super E, Result<T>> mapper);

    /**
     * If this {@link Result} is failure and the error matches the given predicate,
     * apply {@code mapper} to the error and continue with the {@link Result} returned
     * from the operation.
     *
     * @param predicate the matcher for errors to handle
     * @param mapper    the function to apply to the error if this {@link Result}
     *                  is failure and the error matches the given predicate
     * @return the {@link Result} returned from applying this {@link Result}'s error
     * to {@code mapper}
     * @throws NullPointerException if predicate is null
     * @throws NullPointerException if mapper is null
     */
    @NonNull
    public abstract Result<T> ifFailureResume(@NonNull Predicate<? super Throwable> predicate, @NonNull Function<? super Throwable, Result<T>> mapper);

    /**
     * If this {@link Result} is failure, continue with the {@code fallbackValue}
     * as a {@link Result}.
     *
     * @param fallbackValue the value to use if this {@link Result} is failure
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if defaultValue is null
     */
    @NonNull
    public abstract Result<T> ifFailureReturn(@NonNull T fallbackValue);

    /**
     * If this {@link Result} is failure and the error matches the given exception type,
     * continue with the {@code fallbackValue} as a {@link Result}.
     *
     * @param exceptionType the exception type to match
     * @param fallbackValue the value to use if this {@link Result} is failure and
     *                      the error matches the given exception type
     * @param <E>           exception type
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if exceptionType is null
     * @throws NullPointerException if defaultValue is null
     */
    @NonNull
    public abstract <E extends Throwable> Result<T> ifFailureReturn(@NonNull Class<E> exceptionType, @NonNull T fallbackValue);

    /**
     * If this {@link Result} is failure and the error matches the given predicate,
     * continue with the {@code fallbackValue} as a {@link Result}.
     *
     * @param predicate     the matcher for errors to handle
     * @param fallbackValue the value to use if this {@link Result} is failure and
     *                      the error matches the given predicate
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if predicate is null
     * @throws NullPointerException if defaultValue is null
     */
    @NonNull
    public abstract Result<T> ifFailureReturn(@NonNull Predicate<? super Throwable> predicate, @NonNull T fallbackValue);

    /**
     * If this {@link Result} is failure, continue with the {@code fallbackValue}
     * as a {@link Result}.
     *
     * @param fallbackValue the value to use if this {@link Result} is failure
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if defaultValue supplier is null
     */
    @NonNull
    public abstract Result<T> ifFailureReturn(@NonNull Supplier<T> fallbackValue);

    /**
     * If this {@link Result} is failure and the error matches the given exception type,
     * continue with the {@code fallbackValue} as a {@link Result}.
     *
     * @param exceptionType the exception type to match
     * @param fallbackValue the value to use if this {@link Result} is failure and
     *                      the error matches the given exception type
     * @param <E>           exception type
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if exceptionType is null
     * @throws NullPointerException if defaultValue supplier is null
     */
    @NonNull
    public abstract <E extends Throwable> Result<T> ifFailureReturn(@NonNull Class<E> exceptionType, @NonNull Supplier<T> fallbackValue);

    /**
     * If this {@link Result} is failure and the error matches the given predicate,
     * continue with the {@code fallbackValue} as a {@link Result}.
     *
     * @param predicate     the matcher for errors to handle
     * @param fallbackValue the value supplier to use if this {@link Result} is failure and
     *                      the error matches the given predicate
     * @return {@code fallbackValue} as a {@link Result}
     * @throws NullPointerException if predicate is null
     * @throws NullPointerException if defaultValue supplier is null
     */
    @NonNull
    public abstract Result<T> ifFailureReturn(@NonNull Predicate<? super Throwable> predicate, @NonNull Supplier<T> fallbackValue);

    /**
     * Add behavior triggered if the {@code Result} is successful.
     *
     * @param onSuccess the consumer to call if the {@link Result} is successful
     * @return an observed {@link Result}
     * @throws NullPointerException if onSuccess is null
     */
    @NonNull
    public abstract Result<T> peekIfSuccess(@NonNull Consumer<? super T> onSuccess);

    /**
     * Add behavior triggered if the {@code Result} is failure.
     *
     * @param onFailure the callback to call if the {@link Result} is failure
     * @return an observed {@link Result}
     * @throws NullPointerException if onFailure is null
     */
    @NonNull
    public abstract Result<T> peekIfFailure(@NonNull Consumer<? super Throwable> onFailure);

    /**
     * Add behavior triggered if the {@code Result} is failure with an error matching
     * the given exception type.
     *
     * @param exceptionType the exception type to handle
     * @param onFailure     the callback to call if the {@link Result} is failure and the
     *                      error matches the exception type
     * @param <E>           the type of the error to handle
     * @return an observed {@link Result}
     * @throws NullPointerException if exceptionType is null
     * @throws NullPointerException if onFailure is null
     */
    @NonNull
    public abstract <E extends Throwable> Result<T> peekIfFailure(@NonNull Class<E> exceptionType, @NonNull Consumer<? super E> onFailure);

    /**
     * Add behavior triggered if the {@code Result} is failure with an error matching
     * the given predicate.
     *
     * @param predicate the matcher for errors to handle
     * @param onFailure the callback to call if the {@link Result} is failure and the
     *                  error matches the predicate
     * @return an observed {@link Result}
     * @throws NullPointerException if predicate is null
     * @throws NullPointerException if onFailure is null
     */
    @NonNull
    public abstract Result<T> peekIfFailure(@NonNull Predicate<? super Throwable> predicate, @NonNull Consumer<? super Throwable> onFailure);

    /**
     * If this {@link Result} is success, consume value with {@code successAction}.
     *
     * @param successAction {@link Consumer} of T
     * @throws NullPointerException if {@code successAction} is null
     */
    public abstract void ifSuccess(@NonNull Consumer<T> successAction);

    /**
     * If this {@link Result} is success, consume value with {@code successAction}.
     * If this {@link Result} is failure, consume error with {@code failureAction}.
     *
     * @param successAction value consumer
     * @param failureAction error consumer
     */
    public abstract void ifSuccessOrElse(@NonNull Consumer<T> successAction, @NonNull Consumer<Throwable> failureAction);

    /**
     * If this {@link Result} is failure, consume error with {@code failureAction}.
     *
     * @param failureAction error consumer
     */
    public abstract void ifFailure(@NonNull Consumer<Throwable> failureAction);

    /**
     * Returns if this {@link Result} is success or not.
     *
     * @return true if this {@link Result} is success
     */
    public abstract boolean isSuccess();

    /**
     * Returns if this {@link Result} is failure or not.
     *
     * @return true if this {@link Result} is failure
     */
    public abstract boolean isFailure();

    /**
     * If the {@link Result} is success, return the value.
     *
     * @return this {@link Result}'s value or {@code null} if is failure
     */
    @Nullable
    public abstract T getValue();

    /**
     * If this {@link Result} is failure, return the error.
     *
     * @return this {@link Result}'s error or {@code null} if is success
     */
    @Nullable
    public abstract Throwable getException();
}
