package com.filippo.either.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.filippo.either.R
import com.filippo.either.common.data.TextResource
import com.filippo.either.destinations.AccountScreenDestination
import com.filippo.either.destinations.LoginScreenDestination
import com.filippo.either.destinations.MoviesScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomNavigationDestinations(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    val label: TextResource,
) {
    Movies(
        direction = MoviesScreenDestination,
        icon = Icons.Filled.List,
        label = TextResource.fromStringRes(R.string.movies)
    ),
    Account(
        direction = AccountScreenDestination,
        icon = Icons.Filled.AccountCircle,
        label = TextResource.fromStringRes(R.string.account)
    )
}
