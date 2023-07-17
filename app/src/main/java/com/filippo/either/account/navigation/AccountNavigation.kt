package com.filippo.either.account.navigation

import com.filippo.either.destinations.AccountScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

fun DestinationsNavigator.navigateToAccount(popUpTo: String) {
    navigate(AccountScreenDestination) {
        this.popUpTo(popUpTo) {
            inclusive = true
        }
    }
}
