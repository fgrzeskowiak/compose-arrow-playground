package com.filippo.either.account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.filippo.either.account.domain.AccountDetails
import com.filippo.either.account.domain.AccountError
import com.filippo.either.account.domain.GetAccountUseCase
import com.filippo.either.account.domain.LogOutUseCase
import com.filippo.either.common.presentation.errorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class AccountViewModel @Inject constructor(
    getAccountUseCase: GetAccountUseCase,
    private val logOutUseCase: LogOutUseCase,
) : ViewModel() {

    val state: StateFlow<AccountDetailsUiState> = getAccountUseCase()
        .map { it.toUiState() }
        .stateIn(viewModelScope, SharingStarted.Lazily, AccountDetailsUiState.Loading)

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase()
        }
    }

//    @Composable
//    fun presenter(): AccountDisplayModel {
//        val scope = rememberCoroutineScope()
//        var uiState by remember { mutableStateOf(AccountDisplayModel()) }
//
//        fun logOut() {
//            scope.launch {
//                logOutUseCase()
//            }
//        }
//
//        LaunchedEffect(Unit) {
//            getAccountUseCase().collect {
//                uiState = it.toDisplayModel(onLogout = ::logOut)
//            }
//        }
//
//        return uiState
//    }
}
