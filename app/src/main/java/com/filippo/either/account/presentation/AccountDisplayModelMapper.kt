package com.filippo.either.account.presentation

import arrow.core.Either
import com.filippo.either.account.domain.AccountDetails
import com.filippo.either.account.domain.AccountError
import com.filippo.either.account.domain.AccountState
import com.filippo.either.common.presentation.errorMessage

fun AccountState.toDisplayModel(onLogout: () -> Unit): AccountDisplayModel {
    val error = (this as? AccountState.Failure)?.error
    return AccountDisplayModel(
        isLoading = this is AccountState.Loading,
        info = (this as? AccountState.Success)?.accountDetails?.let {
            AccountDisplayModel.Info(name = it.name, username = it.username)
        },
        needsLogin = error is AccountError.NotLoggedIn,
        errorMessage = (error as? AccountError.Request)?.error?.errorMessage,
        onLogout = onLogout
    )
}

fun Either<AccountError, AccountDetails>.toUiState() = fold(
    ifLeft = { error ->
        when (error) {
            AccountError.NotLoggedIn -> AccountDetailsUiState.NeedsLogin
            is AccountError.Request -> AccountDetailsUiState.Failure(error.error.errorMessage)
        }
    },
    ifRight = { AccountDetailsUiState.Success(it.name, it.username) }
)
