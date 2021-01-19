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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Consumer3Test extends ConsumerBaseTest {

    @Test
    void testOf() {
        assertNotNull(Consumer3.of(TestConsumers::printOut2));

        assertThrows(NullPointerException.class, () -> Consumer3.of(null));
    }

    @Test
    void testPartialApply() {
        Consumer3.of(TestConsumers::printOut2)
                .partialApply("hello, %s and %s")
                .accept("world", "goodbye");
        assertEquals("hello, world and goodbye", outContent.toString());

        setup();
        Consumer3.of(TestConsumers::printOut2)
                .partialApply("hello, %s and %s", "world")
                .accept("goodbye");
        assertEquals("hello, world and goodbye", outContent.toString());

        setup();
        Consumer3.of(TestConsumers::printOut2)
                .partialApply("hello, %s and %s", "world", "goodbye")
                .accept();
        assertEquals("hello, world and goodbye", outContent.toString());
    }

    @Test
    void testAndThen() {
        Consumer3.of(TestConsumers::printOut2)
                .thenAccept(TestConsumers::printErr2)
                .accept("hello, %s and %s", "world", "goodbye");
        assertEquals("hello, world and goodbye", outContent.toString());
        assertEquals("hello, world and goodbye", errContent.toString());

        assertThrows(NullPointerException.class, () -> Consumer3.of(TestConsumers::printOut2)
                .thenAccept(null));
    }

    @Test
    void testReverse() {
        Consumer3.of(TestConsumers::printOut2)
                .accept("%s and %s", "hello", "goodbye");
        assertEquals("hello and goodbye", outContent.toString());

        setup();
        Consumer3.of(TestConsumers::printOut2)
                .reverse()
                .accept("hello", "goodbye", "%s and %s");
        assertEquals("goodbye and hello", outContent.toString());
    }
}
