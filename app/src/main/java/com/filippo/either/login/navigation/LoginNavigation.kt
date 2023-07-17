package com.filippo.either.login.navigation

import com.filippo.either.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

fun DestinationsNavigator.navigateToLogin(popUpTo: String) {
    navigate(LoginScreenDestination) {
        this.popUpTo(popUpTo) {
            inclusive = true
        }
    }
}
