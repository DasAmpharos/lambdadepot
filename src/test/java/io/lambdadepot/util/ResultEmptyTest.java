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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.junit.jupiter.api.Test;

import static io.lambdadepot.util.Predicates.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResultEmptyTest {
    private static final Result<Integer> EMPTY_INT = Result.success(10).filter(Objects::isNull);
    private static final Result<String> EMPTY_STRING = Result.success("success").filter(Objects::isNull);

    @Test
    void map() {
        Result<Integer> result = Result.success(10).map(i -> null);
        assertEquals(Result.empty(), result);
    }

    @Test
    void flatMap() {
        assertEquals(Result.empty(), Result.success(10).flatMap(i -> Result.empty()));
    }

    @Test
    void filter() {
        assertEquals(Result.empty(), Result.success(5).filter(Objects::isNull));
    }

    @Test
    void ifFailureMap() {
        assertEquals(Result.empty(), EMPTY_INT.ifFailureMap(t -> new UnsupportedOperationException()));
    }

    @Test
    void testIfFailureMap() {
        assertEquals(Result.empty(), EMPTY_INT.ifFailureMap(Throwable.class, t -> new UnsupportedOperationException()));
    }

    @Test
    void testIfFailureMap1() {
        assertEquals(Result.empty(), EMPTY_INT.ifFailureMap(instanceOf(UnsupportedOperationException.class), t -> new IllegalArgumentException()));
    }

    @Test
    void ifFailureResume() {
        assertEquals(Result.empty(), EMPTY_INT.ifFailureResume(t -> Result.success(10)));
    }

    @Test
    void testIfFailureResume() {
        assertEquals(Result.empty(), EMPTY_INT.ifFailureResume(Throwable.class, t -> Result.success(10)));
    }

    @Test
    void testIfFailureResume1() {
        assertEquals(Result.empty(), EMPTY_INT.ifFailureResume(instanceOf(Throwable.class), t -> Result.success(10)));
    }

    @Test
    void ifFailureReturn() {
        assertEquals(Result.empty(), EMPTY_INT.ifFailureReturn(10));
    }

    @Test
    void testIfFailureReturn() {
        assertEquals(Result.empty(), EMPTY_INT.ifFailureReturn(() -> 10));
    }

    @Test
    void testIfFailureReturn1() {
        assertEquals(Result.empty(), EMPTY_INT.ifFailureReturn(NumberFormatException.class, 10));
    }

    @Test
    void testIfFailureReturn2() {
        assertEquals(Result.empty(), EMPTY_INT.ifFailureReturn(instanceOf(Throwable.class), 10));
    }

    @Test
    void testIfFailureReturn3() {
        assertEquals(Result.empty(), EMPTY_INT.ifFailureReturn(instanceOf(Throwable.class), () -> 10));
    }

    @Test
    void testIfFailureReturn4() {
        assertEquals(Result.empty(), EMPTY_INT.ifFailureReturn(instanceOf(NumberFormatException.class), () -> 10));
    }

    @Test
    void ifEmptyReturn() {
        assertEquals(10, EMPTY_INT.ifEmptyReturn(10).getValue());
    }

    @Test
    void testIfEmptyReturn() {
        assertEquals(10, EMPTY_INT.ifEmptyReturn(() -> 10).getValue());
    }

    @Test
    void ifEmptyResume() {
        assertEquals(10, EMPTY_INT.ifEmptyResume(Result.success(10)).getValue());
    }

    @Test
    void testIfEmptyResume() {
        assertEquals(10, EMPTY_INT.ifEmptyResume(() -> Result.success(10)).getValue());
    }

    @Test
    void peekIfSuccess() {
        List<String> strings = new ArrayList<>();
        EMPTY_STRING.peekIfSuccess(strings::add);
        assertTrue(strings::isEmpty);
    }

    @Test
    void peekIfFailure() {
        List<String> strings = new ArrayList<>();
        EMPTY_STRING.peekIfFailure(t -> strings.add(t.getMessage()));
        assertTrue(strings::isEmpty);
    }

    @Test
    void testPeekIfFailure() {
        List<String> strings = new ArrayList<>();
        EMPTY_STRING.peekIfFailure(Throwable.class, t -> strings.add(t.getMessage()));
        assertTrue(strings::isEmpty);
    }

    @Test
    void testPeekIfFailure1() {
        List<String> strings = new ArrayList<>();
        EMPTY_STRING.peekIfFailure(instanceOf(Throwable.class), t -> strings.add(t.getMessage()));
        assertTrue(strings::isEmpty);
    }

    @Test
    void peekIfEmpty() {
        List<String> strings = new ArrayList<>();
        EMPTY_STRING.peekIfEmpty(() -> strings.add("empty"));
        assertFalse(strings::isEmpty);
        assertTrue(strings.contains("empty"));
    }

    @Test
    void ifSuccess() {
        List<String> strings = new ArrayList<>();
        EMPTY_STRING.ifSuccess(strings::add);
        assertTrue(strings::isEmpty);
    }

    @Test
    void ifSuccessOrFailure() {
        List<String> strings = new ArrayList<>();
        EMPTY_STRING.ifSuccessOrElse(strings::add, t -> strings.add(t.getMessage()));
        assertTrue(strings::isEmpty);
    }

    @Test
    void ifSuccessOrEmpty() {
        List<String> strings = new ArrayList<>();
        EMPTY_STRING.ifSuccessOrEmpty(strings::add, () -> strings.add("empty"));
        assertFalse(strings::isEmpty);
        assertTrue(strings.contains("empty"));
    }

    @Test
    void ifSuccessOrElse() {
        List<String> strings = new ArrayList<>();
        EMPTY_STRING.ifSuccessOrEmpty(strings::add, () -> strings.add("empty"));
        assertFalse(strings::isEmpty);
        assertTrue(strings.contains("empty"));
    }

    @Test
    void ifFailure() {
        List<String> strings = new ArrayList<>();
        EMPTY_STRING.ifFailure(t -> strings.add(t.getMessage()));
        assertTrue(strings::isEmpty);
    }

    @Test
    void ifFailureOrEmpty() {
        List<String> strings = new ArrayList<>();
        EMPTY_STRING.ifFailureOrEmpty(t -> strings.add(t.getMessage()), () -> strings.add("empty"));
        assertFalse(strings::isEmpty);
        assertTrue(strings.contains("empty"));
    }

    @Test
    void ifEmpty() {
        List<String> strings = new ArrayList<>();
        EMPTY_STRING.ifEmpty(() -> strings.add("empty"));
        assertFalse(strings::isEmpty);
        assertTrue(strings.contains("empty"));
    }

    @Test
    void isSuccess() {
        assertFalse(EMPTY_INT.isSuccess());
    }

    @Test
    void isFailure() {
        assertFalse(EMPTY_INT.isFailure());
    }

    @Test
    void isEmpty() {
        assertTrue(EMPTY_INT.isEmpty());
    }

    @Test
    void getValue() {
        assertThrows(NoSuchElementException.class, () -> EMPTY_INT.getValue());
    }

    @Test
    void getError() {
        assertThrows(NoSuchElementException.class, () -> EMPTY_INT.getException());
    }

    @Test
    void orElseThrow() {
        assertThrows(NumberFormatException.class, () -> EMPTY_INT.orElseThrow(() -> new NumberFormatException()));
    }

    @Test
    void testOrElseThrow() {
        assertThrows(NumberFormatException.class, () ->
                EMPTY_INT.orElseThrow(() -> new NumberFormatException(), () -> new UnsupportedOperationException()));
    }
}