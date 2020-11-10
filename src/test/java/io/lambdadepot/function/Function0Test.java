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

package io.lambdadepot.function;

import io.lambdadepot.util.Result;
import io.lambdadepot.util.ResultFailure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Function0Test {

    private final Function0<String> testFunction = Function0.of(TestFunctions::getString);

    @Test
    void testOf() {
        assertNotNull(Function0.of(TestFunctions::getString));
        assertThrows(NullPointerException.class, () -> Function0.of(null));
    }

    @Test
    void testGet() {
        assertEquals("hello, world", testFunction.get());
    }

    @Test
    void testApply() {
        assertEquals("hello, world", testFunction.apply());
    }

    @Test
    void testAndThen() {
        assertEquals("HELLO, WORLD", testFunction.andThen(String::toUpperCase).apply());
        assertThrows(NullPointerException.class, () -> testFunction.andThen(null));
    }

    @Test
    void testLift() {
        Function0<String> error = () -> {
            throw new IllegalStateException("Error");
        };
        Function0<String> empty = () -> null;
        Assertions.assertEquals("hello, world", testFunction.lift().apply().getValue());
        assertTrue(error.lift().apply() instanceof ResultFailure);
        assertTrue(error.lift().apply().getException() instanceof IllegalStateException);
        Assertions.assertEquals(Result.empty(), empty.lift().apply());
    }
}
