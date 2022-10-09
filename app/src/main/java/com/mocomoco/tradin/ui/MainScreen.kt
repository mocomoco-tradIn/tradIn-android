package com.mocomoco.tradin.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    TradInNavGraph(
        navController = navController,
        startDestination = TradInDestinations.HOME_ROUTE
    )
}