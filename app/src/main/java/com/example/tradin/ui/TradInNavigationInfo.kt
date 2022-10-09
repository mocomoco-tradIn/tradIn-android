package com.example.tradin.ui

import androidx.navigation.NavHostController


object TradInDestinations {
    const val HOME_ROUTE = "home"
    const val DETAILS_ROUTE = "details"
    const val PROFILE_ROUTE = "profile"
}

class TradInNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(TradInDestinations.HOME_ROUTE) {

        }
    }
}