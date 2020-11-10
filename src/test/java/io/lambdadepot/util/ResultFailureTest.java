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
import org.junit.jupiter.api.Test;

import static io.lambdadepot.util.Predicates.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResultFailureTest {
    private static final Result<Integer> FAILED_NUMBER = Result.success("Nan").map(Integer::parseInt);

    @Test
    void map() {
        assertThrows(NoSuchElementException.class, () -> Result.success("Nan").map(Integer::parseInt).getValue());
        assertTrue(FAILED_NUMBER.isFailure());
    }

    @Test
    void flatMap() {
        Result<Integer> result = FAILED_NUMBER.flatMap(i -> Result.success(i));
        assertTrue(result.isFailure());
        assertTrue(result.getException() instanceof NumberFormatException);
        assertThrows(NoSuchElementException.class, () -> result.getValue());
    }

    @Test
    void filter() {
        Result<Integer> result = FAILED_NUMBER.filter(i -> i < 1000);
        assertTrue(result.isFailure());
        assertThrows(NoSuchElementException.class, () -> result.getValue());
    }

    @Test
    void ifFailureMap() {
        Result<Integer> result = FAILED_NUMBER.ifFailureMap(t -> new IllegalArgumentException());
        assertTrue(result.getException() instanceof IllegalArgumentException);
    }

    @Test
    void testIfFailureMap() {
        Result<Integer> result = FAILED_NUMBER.ifFailureMap(NumberFormatException.class, t -> new IllegalArgumentException());
        assertTrue(result.getException() instanceof IllegalArgumentException);
    }

    @Test
    void testIfFailureMap1() {
        Result<Integer> result = FAILED_NUMBER.ifFailureMap(instanceOf(NumberFormatException.class), t -> new IllegalArgumentException());
        assertTrue(result.getException() instanceof IllegalArgumentException);
    }

    @Test
    void ifFailureResume() {
        assertEquals(10, FAILED_NUMBER.ifFailureResume(t -> Result.success(10)).getValue());
    }

    @Test
    void testIfFailureResume() {
        assertEquals(10, FAILED_NUMBER.ifFailureResume(NumberFormatException.class, t -> Result.success(10)).getValue());
    }

    @Test
    void testIfFailureResume1() {
        assertEquals(10, FAILED_NUMBER.ifFailureResume(instanceOf(NumberFormatException.class), t -> Result.success(10)).getValue());
    }

    @Test
    void ifFailureReturn() {
        assertEquals(10, FAILED_NUMBER.ifFailureReturn(10).getValue());
    }

    @Test
    void testIfFailureReturn() {
        assertEquals(10, FAILED_NUMBER.ifFailureReturn(() -> 10).getValue());
    }

    @Test
    void testIfFailureReturn1() {
        assertEquals(10, FAILED_NUMBER.ifFailureReturn(NumberFormatException.class, 10).getValue());
    }

    @Test
    void testIfFailureReturn2() {
        assertEquals(10, FAILED_NUMBER.ifFailureReturn(NumberFormatException.class, () -> 10).getValue());
    }

    @Test
    void testIfFailureReturn3() {
        assertEquals(10,
                FAILED_NUMBER.ifFailureReturn(instanceOf(NumberFormatException.class), 10).getValue());
    }

    @Test
    void testIfFailureReturn4() {
        assertEquals(10,
                FAILED_NUMBER.ifFailureReturn(instanceOf(NumberFormatException.class), () -> 10).getValue());
    }

    @Test
    void ifEmptyReturn() {
        assertThrows(NoSuchElementException.class, () -> FAILED_NUMBER.ifEmptyReturn(10).getValue());
    }

    @Test
    void testIfEmptyReturn() {
        assertThrows(NoSuchElementException.class, () -> FAILED_NUMBER.ifEmptyReturn(() -> 10).getValue());
    }

    @Test
    void ifEmptyResume() {
        assertThrows(NoSuchElementException.class, () -> FAILED_NUMBER.ifEmptyResume(Result.success(10)).getValue());
    }

    @Test
    void testIfEmptyResume() {
        assertThrows(NoSuchElementException.class, () -> FAILED_NUMBER.ifEmptyResume(() -> Result.success(10)).getValue());
    }

    @Test
    void peekIfSuccess() {
        List<Integer> list = new ArrayList<>();
        FAILED_NUMBER.peekIfSuccess(list::add);
        assertTrue(list::isEmpty);
    }

    @Test
    void peekIfFailure() {
        List<Throwable> list = new ArrayList<>();
        FAILED_NUMBER.peekIfFailure(list::add);
        assertFalse(list.isEmpty());
        assertTrue(list.get(0) instanceof NumberFormatException);
    }

    @Test
    void testPeekIfFailure() {
        List<Throwable> list = new ArrayList<>();
        FAILED_NUMBER.peekIfFailure(NumberFormatException.class, list::add);
        FAILED_NUMBER.peekIfFailure(ClassCastException.class, list::add);
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertTrue(list.get(0) instanceof NumberFormatException);
    }

    @Test
    void testPeekIfFailure1() {
        List<Throwable> list = new ArrayList<>();
        FAILED_NUMBER.peekIfFailure(instanceOf(NumberFormatException.class), list::add);
        FAILED_NUMBER.peekIfFailure(instanceOf(ClassCastException.class), list::add);
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertTrue(list.get(0) instanceof NumberFormatException);
    }

    @Test
    void peekIfEmpty() {
        List<Integer> list = new ArrayList<>();
        FAILED_NUMBER.peekIfEmpty(() -> list.add(10));
        assertTrue(list::isEmpty);
    }

    @Test
    void ifSuccess() {
        List<Integer> list = new ArrayList<>();
        FAILED_NUMBER.ifSuccess(list::add);
        assertTrue(list::isEmpty);
    }

    @Test
    void ifSuccessOrFailure() {
        List<Integer> list = new ArrayList<>();
        FAILED_NUMBER.ifSuccessOrElse(list::add, t -> list.add(-1));
        assertFalse(list::isEmpty);
        assertEquals(1, list.size());
        assertEquals(-1, list.get(0));
    }

    @Test
    void ifSuccessOrEmpty() {
        List<Integer> list = new ArrayList<>();
        FAILED_NUMBER.ifSuccessOrEmpty(list::add, () -> list.add(10));
        assertTrue(list::isEmpty);
    }

    @Test
    void ifSuccessOrElse() {
        List<Integer> list = new ArrayList<>();
        FAILED_NUMBER.ifSuccessOrElse(list::add, t -> list.add(-1), () -> list.add(0));
        assertFalse(list::isEmpty);
        assertEquals(1, list.size());
        assertEquals(-1, list.get(0));
    }

    @Test
    void ifFailure() {
        List<Integer> list = new ArrayList<>();
        FAILED_NUMBER.ifFailure(t -> list.add(-1));
        assertFalse(list::isEmpty);
        assertEquals(1, list.size());
        assertEquals(-1, list.get(0));
    }

    @Test
    void ifFailureOrEmpty() {
        List<Integer> list = new ArrayList<>();
        FAILED_NUMBER.ifFailureOrEmpty(t -> list.add(-1), () -> list.add(0));
        assertFalse(list::isEmpty);
        assertEquals(1, list.size());
        assertEquals(-1, list.get(0));
    }

    @Test
    void ifEmpty() {
        List<Integer> list = new ArrayList<>();
        FAILED_NUMBER.ifEmpty(() -> list.add(0));
        assertTrue(list::isEmpty);
    }

    @Test
    void isSuccess() {
        assertFalse(FAILED_NUMBER::isSuccess);
    }

    @Test
    void isFailure() {
        assertTrue(FAILED_NUMBER::isFailure);
    }

    @Test
    void isEmpty() {
        assertFalse(FAILED_NUMBER::isEmpty);
    }

    @Test
    void getValue() {
        assertThrows(NoSuchElementException.class, () -> FAILED_NUMBER.getValue());
    }

    @Test
    void getError() {
        assertTrue(FAILED_NUMBER.getException() instanceof NumberFormatException);
    }

    @Test
    void orElseThrow() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> FAILED_NUMBER.orElseThrow(() -> new IllegalArgumentException("empty")));
        assertTrue(exception.getCause() instanceof NumberFormatException);
        assertNotEquals("empty", exception.getMessage());
    }

    @Test
    void testOrElseThrow() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> FAILED_NUMBER.orElseThrow(() -> new ClassCastException("empty"),
            () -> new IllegalArgumentException("failure")));
        assertEquals("failure", exception.getMessage());
    }
}