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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;

public class ConsumerBaseTest {

    ByteArrayOutputStream outContent;
    ByteArrayOutputStream errContent;

    @BeforeEach
    public void setup() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
    }
}