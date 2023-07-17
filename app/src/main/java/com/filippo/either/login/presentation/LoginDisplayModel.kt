package com.filippo.either.login.presentation

import com.filippo.either.common.data.TextResource

sealed interface LoginUiState {
    object Idle: LoginUiState
    object Loading : LoginUiState
    object Success : LoginUiState
    data class Failure(
        val usernameError: TextResource?,
        val passwordError: TextResource?,
        val requestError: TextResource?,
    ) : LoginUiState
}

data class LoginDisplayModel(
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false,
    val usernameError: TextResource? = null,
    val passwordError: TextResource? = null,
    val requestError: TextResource? = null,
)
