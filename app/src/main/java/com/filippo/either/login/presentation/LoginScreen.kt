package com.filippo.either.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.filippo.either.R
import com.filippo.either.account.navigation.navigateToAccount
import com.filippo.either.common.data.TextResource
import com.filippo.either.common.presentation.ProgressBar
import com.filippo.either.common.presentation.asString
import com.filippo.either.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@RootNavGraph
@Destination
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val snackbarState = remember { SnackbarHostState() }
    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarState)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
    ) { paddingValues ->
        state.requestError?.let { requestError ->
            RequestError(
                requestError = requestError,
                snackbarState = snackbarState,
            )
        }

        if (state.isLoading) {
            ProgressBar(modifier = Modifier.padding(paddingValues))
        }

        if (state.isSuccessful) {
            LaunchedEffect(state) {
                navigator.navigateToAccount(popUpTo = LoginScreenDestination.route)
            }
        }

        LoginInputs(state, viewModel::login)
    }
}

@Composable
private fun LoginInputs(
    state: LoginDisplayModel,
    onLoginClicked: (username: String, password: String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val (username, setUsername) = remember { mutableStateOf("") }
        val (password, setPassword) = remember { mutableStateOf("") }

        OutlinedTextField(
            modifier = Modifier
                .setupAutofill(
                    types = listOf(AutofillType.Username, AutofillType.EmailAddress),
                    onFill = setUsername
                ),
            value = username,
            onValueChange = setUsername,
        )
        state.usernameError?.let {
            Text(text = it.asString(), color = Color.Red)
        }
        OutlinedTextField(
            modifier = Modifier
                .setupAutofill(
                    types = listOf(AutofillType.Password),
                    onFill = setPassword
                ),
            value = password,
            onValueChange = setPassword,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )
        state.passwordError?.let {
            Text(text = it.asString(), color = Color.Red)
        }

        Button(onClick = { onLoginClicked(username, password) }) {
            Text(text = stringResource(R.string.log_in))
        }
    }
}

@Composable
private fun RequestError(
    requestError: TextResource,
    snackbarState: SnackbarHostState,
) {
    val requestErrorMessage = requestError.asString()
    val retryLabel = stringResource(R.string.retry)
    LaunchedEffect(requestError) {
        snackbarState.showSnackbar(
            message = requestErrorMessage,
            duration = SnackbarDuration.Long,
            actionLabel = retryLabel
        )
    }
}


private fun Modifier.setupAutofill(types: List<AutofillType>, onFill: (String) -> Unit) = composed {
    val autofill = LocalAutofill.current
    val node = AutofillNode(autofillTypes = types, onFill = onFill)
    LocalAutofillTree.current += node

    onGloballyPositioned { node.boundingBox = it.boundsInWindow() }
        .onFocusChanged { focusState ->
            autofill?.run {
                if (focusState.hasFocus) {
                    requestAutofillForNode(node)
                } else {
                    cancelAutofillForNode(node)
                }
            }
        }
}
