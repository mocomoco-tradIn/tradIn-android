package com.mocomoco.tradin.ui

import androidx.annotation.DrawableRes
import androidx.navigation.NavHostController
import com.mocomoco.tradin.R


object TradInDestinations {
    const val HOME_ROUTE = "home"
    const val COMMUNITY_ROUTE = "community"
    const val ADD_ROUTE = "add"
    const val CHAT_ROUTE = "chat"
    const val PROFILE_ROUTE = " profile"
    const val DETAILS_ROUTE = "details"
}

class TradInNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(TradInDestinations.HOME_ROUTE) {

        }
    }
}

sealed class BottomNavigationScreen(val route: String, @DrawableRes val iconResourceId: Int) {
    object Home : BottomNavigationScreen(
        route = TradInDestinations.HOME_ROUTE,
        iconResourceId = R.drawable.ic_bottom_nav_home
    )

    object Community : BottomNavigationScreen(
        route = TradInDestinations.COMMUNITY_ROUTE,
        iconResourceId = R.drawable.ic_bottom_nav_community
    )

    object Add : BottomNavigationScreen(
        route = TradInDestinations.ADD_ROUTE,
        iconResourceId = R.drawable.ic_bottom_nav_add
    )

    object Chat : BottomNavigationScreen(
        route = TradInDestinations.CHAT_ROUTE,
        iconResourceId = R.drawable.ic_bottom_nav_chat
    )

    object Profile : BottomNavigationScreen(
        route = TradInDestinations.PROFILE_ROUTE,
        iconResourceId = R.drawable.ic_bottom_nav_profile
    )
}

val bottomNavItems = listOf(
    BottomNavigationScreen.Home,
    BottomNavigationScreen.Community,
    BottomNavigationScreen.Add,
    BottomNavigationScreen.Chat,
    BottomNavigationScreen.Profile
)