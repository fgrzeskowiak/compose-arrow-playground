package com.filippo.either.ui

import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.filippo.either.NavGraphs
import com.filippo.either.appCurrentDestinationAsState
import com.filippo.either.common.presentation.asString
import com.filippo.either.navigation.BottomNavigationDestinations
import com.filippo.either.startAppDestination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.utils.isRouteOnBackStack

@Composable
fun BottomNavigation(navController: NavController) {
    val currentDestination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    androidx.compose.material.BottomNavigation {
        BottomNavigationDestinations.values()
            .forEach { destination ->
                val isOnBackStack = navController.isRouteOnBackStack(destination.direction)

                BottomNavigationItem(
                    selected = currentDestination == destination.direction,
                    icon = { Icon(destination.icon, contentDescription = null) },
                    label = { Text(destination.label.asString()) },
                    onClick = {
                        if (isOnBackStack) {
                            // When we click again on a bottom bar item and it was already selected
                            // we want to pop the back stack until the initial destination of this bottom bar item
                            navController.popBackStack(destination.direction, false)
                            return@BottomNavigationItem
                        }

                        navController.navigate(destination.direction) {
                            // Pop up to the root of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(NavGraphs.root) {
                                saveState = true
                            }

                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
    }
}
