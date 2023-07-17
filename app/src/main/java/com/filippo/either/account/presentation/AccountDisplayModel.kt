package com.filippo.either.account.presentation

import com.filippo.either.common.data.TextResource

data class AccountDisplayModel(
    val isLoading: Boolean = false,
    val needsLogin: Boolean = false,
    val errorMessage: TextResource? = null,
    val info: Info? = null,
    val onLogout: () -> Unit = {},
) {
    data class Info(
        val name: String,
        val username: String,
    )
}

sealed interface AccountDetailsUiState {
    object Loading : AccountDetailsUiState
    object NeedsLogin : AccountDetailsUiState
    data class Failure(val message: TextResource) : AccountDetailsUiState
    data class Success(
        val name: String,
        val username: String,
    ): AccountDetailsUiState
}

