package com.homedepot.lambdadepot.util

import io.lambdadepot.util.Result

fun <R> runCatching(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        Result.failure(e)
    }
}
