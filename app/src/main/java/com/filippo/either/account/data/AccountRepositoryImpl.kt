package com.filippo.either.account.data

import arrow.core.raise.either
import com.filippo.either.account.data.remote.AccountApi
import com.filippo.either.account.domain.AccountDetails
import com.filippo.either.account.domain.AccountRepository
import com.filippo.either.common.domain.ApiResponse
import com.filippo.either.common.domain.apiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepositoryImpl @Inject constructor(private val api: AccountApi) : AccountRepository {
    private var cachedAccount: AccountDetails? = null

    override suspend fun getAccountDetails(sessionId: String): ApiResponse<AccountDetails> =
        either {
            cachedAccount ?: apiCall { api.accountDetails(sessionId) }.bind()
                .toModel()
                .also(::cachedAccount::set)
        }

}
