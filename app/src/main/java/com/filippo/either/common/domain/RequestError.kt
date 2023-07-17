package com.filippo.either.common.domain

import arrow.core.Either
import com.filippo.either.common.data.ErrorBody
import com.filippo.either.di.configuredJson
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlinx.serialization.decodeFromString
import retrofit2.HttpException

sealed interface RequestError {
    object NoConnection : RequestError
    object Timeout : RequestError
    object BadRequest : RequestError
    object NotFound : RequestError
    object ServiceDown : RequestError
    data class Unauthorized(val message: String?) : RequestError
    data class General(val code: Int, val message: String?) : RequestError
    data class Unknown(val message: String?) : RequestError
}

fun Throwable.toRequestError() = when (this) {
    is UnknownHostException -> RequestError.NoConnection
    is SocketTimeoutException -> RequestError.Timeout
    is HttpException -> toRequestError()
    else -> RequestError.Unknown(message)
}

private fun HttpException.toRequestError() = when (code()) {
    400 -> RequestError.BadRequest
    401 -> RequestError.Unauthorized(errorBody?.statusMessage)
    404 -> RequestError.NotFound
    500 -> RequestError.ServiceDown
    else -> RequestError.General(code(), message)
}

private val HttpException.errorBody
    get() = response()?.errorBody()
        ?.runCatching { string() }
        ?.mapCatching { configuredJson.decodeFromString<ErrorBody>(it) }
        ?.getOrNull()


typealias ApiResponse <Type> = Either<RequestError, Type>
