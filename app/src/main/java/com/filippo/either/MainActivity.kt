package com.filippo.either

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.filippo.either.ui.BottomNavigation
import com.filippo.either.ui.theme.EitherPlaygroundTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EitherPlaygroundTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigation(navController) }
                ) { paddingValues ->
                    DestinationsNavHost(
                        modifier = Modifier.padding(paddingValues),
                        navGraph = NavGraphs.root,
                        navController = navController
                    )
                }
            }
        }
    }
}
