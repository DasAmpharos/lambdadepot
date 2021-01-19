package io.lambdadepot;

import io.lambdadepot.function.Function0;

public class Main {
    public static void main(String[] args) {
        Function0<Integer> fn = Function0.composer(Function0.just(""))
            .thenTest(String::isEmpty)
            .thenApply(it -> it ? 1 : 0)
            .build();
        int isEmpty = fn.apply();

    }
}
