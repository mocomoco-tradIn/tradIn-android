package com.mocomoco.tradin.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mocomoco.tradin.ui.home.HomeScreen

@Composable
fun TradInNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = TradInDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(TradInDestinations.HOME_ROUTE) {
            HomeScreen()
        }
        composable(TradInDestinations.COMMUNITY_ROUTE) {
            WipScreen(title = TradInDestinations.COMMUNITY_ROUTE)
        }
        composable(TradInDestinations.ADD_ROUTE) {
            WipScreen(title = TradInDestinations.ADD_ROUTE)
        }
        composable(TradInDestinations.CHAT_ROUTE) {
            WipScreen(title = TradInDestinations.CHAT_ROUTE)
        }
        composable(TradInDestinations.PROFILE_ROUTE) {
            WipScreen(title = TradInDestinations.PROFILE_ROUTE) // todo make
        }


        composable(TradInDestinations.DETAILS_ROUTE) {
            WipScreen(title = TradInDestinations.DETAILS_ROUTE) // todo make
        }

        // todo add screen
    }
}