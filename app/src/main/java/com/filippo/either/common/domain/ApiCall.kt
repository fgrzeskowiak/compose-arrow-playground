package com.filippo.either.common.domain

import arrow.core.Either

suspend inline fun <Response> apiCall(
    crossinline request: suspend () -> Response,
): Either<RequestError, Response> =
    Either.catch { request() }
        .onLeft(Throwable::printStackTrace)
        .mapLeft(Throwable::toRequestError)
