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
import io.lambdadepot.util.ResultSuccess;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.lambdadepot.function.TestFunctions.STRING_INT_STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class Function1Test {

    @Test
    void testOf() {
        assertNotNull(Function1.of(TestFunctions::toUpperCase));
        assertThrows(NullPointerException.class, () -> Function1.of(null));
    }

    @Test
    void testAsFunction1() {
        Function<String, String> javaFunction = TestFunctions::toUpperCase;
        assertNotNull(Function1.asFunction1(javaFunction));
        assertThrows(NullPointerException.class, () -> Function1.asFunction1(null));
    }

    @Test
    void testIdentity() {
        assertNotNull(Function1.identity());
        assertEquals("hello, world", Function1.identity()
            .apply("hello, world"));
    }

    @Test
    void testCompose() {
        assertEquals("DLROW ,OLLEH", Function1.of(TestFunctions::toUpperCase)
            .compose(TestFunctions::reverse)
            .apply("hello, world"));
        assertThrows(NullPointerException.class, () -> Function1.of(TestFunctions::toUpperCase).compose(null));
    }

    @Test
    void testAndThen() {
        assertEquals("DLROW ,OLLEH", Function1.of(TestFunctions::toUpperCase)
            .thenApply(TestFunctions::reverse)
            .apply("hello, world"));
        assertThrows(NullPointerException.class, () -> Function1.of(TestFunctions::toUpperCase).thenApply(null));
    }

    @Test
    void testPartialApply() {
        assertEquals("HELLO, WORLD", Function1.of(TestFunctions::toUpperCase)
            .partialApply("hello, world")
            .apply());
    }

    @Test
    void testApply() {
        assertEquals("HELLO, WORLD", Function1.of(TestFunctions::toUpperCase)
            .apply("hello, world"));
    }

    @Test
    void testLift() {
        Result<String> result = STRING_INT_STRING.lift().apply("FAIL");
        assertTrue(result instanceof ResultFailure);
        assertTrue(STRING_INT_STRING.lift().apply("10") instanceof ResultSuccess);
        Function1<String, String> trimToNull = StringUtils::trimToNull;
        Assertions.assertEquals(Result.empty(), trimToNull.lift().apply(""));
    }
}
