package com.filippo.either.account.domain

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import com.filippo.either.common.domain.SessionProvider
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class GetAccountUseCase @Inject constructor(
    private val repository: AccountRepository,
    private val sessionProvider: SessionProvider,
) {
    operator fun invoke(): Flow<Either<AccountError, AccountDetails>> = sessionProvider.sessionId
        .distinctUntilChanged()
        .map(::fetchAccountDetails)

    private suspend fun fetchAccountDetails(sessionId: String?) = either {
        ensureNotNull(sessionId) { AccountError.NotLoggedIn }
        repository.getAccountDetails(sessionId)
            .mapLeft(AccountError::Request)
            .bind()
    }
}
