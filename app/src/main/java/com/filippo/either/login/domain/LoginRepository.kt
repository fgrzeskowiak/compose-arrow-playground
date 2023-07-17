package com.filippo.either.login.domain

import com.filippo.either.common.domain.ApiResponse

interface LoginRepository {
    suspend fun login(credentials: Credentials): ApiResponse<Unit>
}
