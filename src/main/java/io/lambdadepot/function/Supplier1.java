package io.lambdadepot.function;

import java.util.function.Supplier;

public interface Supplier1<R> extends Supplier<R> {
    class Composer<R> {
        private final Supplier1<R> supplier;

        Composer(Supplier1<R> supplier) {
            this.supplier = supplier;
        }

        public Supplier1<R> build() {
            return supplier;
        }
    }
}
