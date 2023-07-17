package com.filippo.either.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filippo.either.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val loginTrigger = MutableSharedFlow<Pair<String, String>>()

    val state: StateFlow<LoginDisplayModel> = loginTrigger
        .flatMapLatest { (username, password) -> loginUiStateFlow(username, password) }
        .stateIn(viewModelScope, SharingStarted.Lazily, LoginDisplayModel())

    private fun loginUiStateFlow(username: String, password: String) = flow {
        emit(LoginDisplayModel(isLoading = true))
        emit(loginUseCase(username, password).toDisplayModel())
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            loginTrigger.emit(username to password)
        }
    }
}
