package com.filippo.either.account.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.filippo.either.R
import com.filippo.either.common.presentation.ProgressBar
import com.filippo.either.destinations.AccountScreenDestination
import com.filippo.either.login.navigation.navigateToLogin
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@RootNavGraph
@Composable
internal fun AccountScreen(
    navigator: DestinationsNavigator,
    viewModel: AccountViewModel = hiltViewModel(),
) {
    val uiState = viewModel.state.collectAsState()

    Scaffold(
        bottomBar = { LogoutButton(onClick = viewModel::logOut) }
    ) { paddingValues ->
        when (val state = uiState.value) {
            AccountDetailsUiState.Loading -> ProgressBar(
                modifier = Modifier.padding(paddingValues)
            )

            AccountDetailsUiState.NeedsLogin -> LaunchedEffect(state, navigator) {
                navigator.navigateToLogin(popUpTo = AccountScreenDestination.route)
            }

            is AccountDetailsUiState.Success -> AccountInfo(
                modifier = Modifier.padding(paddingValues),
                name = state.name,
                username = state.username
            )

            is AccountDetailsUiState.Failure -> TODO()
        }
    }
}

@Composable
private fun LogoutButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Button(modifier = modifier, onClick = onClick) {
            Text(text = stringResource(R.string.log_out))
        }
    }
}

@Composable
private fun AccountInfo(name: String, username: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = name)
        Text(text = username)
    }
}
