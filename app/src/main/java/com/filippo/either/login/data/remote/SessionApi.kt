package com.filippo.either.login.data.remote

import com.filippo.either.login.data.remote.model.SessionRequest
import com.filippo.either.login.data.remote.model.SessionResponse
import com.filippo.either.login.data.remote.model.TokenResponse
import com.filippo.either.login.data.remote.model.ValidateTokenRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SessionApi {
    @GET("3/authentication/token/new")
    suspend fun createToken(): TokenResponse

    @POST("3/authentication/token/validate_with_login")
    suspend fun validateToken(@Body request: ValidateTokenRequest): TokenResponse

    @POST("3/authentication/session/new")
    suspend fun createSession(@Body request: SessionRequest): SessionResponse
}
