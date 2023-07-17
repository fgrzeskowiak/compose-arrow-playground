package com.filippo.either.common.presentation

import com.filippo.either.R
import com.filippo.either.common.data.TextResource
import com.filippo.either.common.domain.RequestError

val RequestError.errorMessage: TextResource
    get() = when (this) {
        RequestError.NoConnection -> TextResource.fromStringRes(R.string.connection_error)
        RequestError.Timeout -> TextResource.fromStringRes(R.string.timeout_error)
        is RequestError.General -> TextResource.fromStringResFormatted(
            R.string.general_request_error,
            code,
            message.orEmpty()
        )

        is RequestError.Unknown -> TextResource.fromStringResFormatted(
            R.string.unknown_error,
            message.orEmpty()
        )

        is RequestError.Unauthorized -> message?.let(TextResource::fromText)
            ?: TextResource.fromStringRes(R.string.unauthorized_error)

        RequestError.BadRequest -> TODO()
        RequestError.NotFound -> TextResource.fromStringRes(R.string.not_found_error)
        RequestError.ServiceDown -> TextResource.fromStringRes(R.string.server_error)
    }
