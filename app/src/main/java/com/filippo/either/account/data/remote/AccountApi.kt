package com.filippo.either.account.data.remote

import com.filippo.either.account.data.remote.models.AccountResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AccountApi {
    @GET("3/account")
    suspend fun accountDetails(@Query("session_id") sessionId: String): AccountResponse
}
