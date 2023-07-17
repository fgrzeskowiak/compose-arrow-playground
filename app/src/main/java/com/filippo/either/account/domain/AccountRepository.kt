package com.filippo.either.account.domain

import com.filippo.either.common.domain.ApiResponse

interface AccountRepository {
    suspend fun getAccountDetails(sessionId: String): ApiResponse<AccountDetails>
}
