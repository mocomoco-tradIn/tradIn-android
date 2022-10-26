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
    const val LOGIN = "login"
}

class TradInNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(TradInDestinations.HOME_ROUTE) {

        }
    }
}

sealed class BottomNavigationScreen(val route: String, @DrawableRes val unselectedIconResourceId: Int, @DrawableRes val selectedIconResourceId: Int) {
    object Home : BottomNavigationScreen(
        route = TradInDestinations.HOME_ROUTE,
        unselectedIconResourceId = R.drawable.ic_bottom_nav_home,
        selectedIconResourceId = R.drawable.ic_bottom_nav_home_sel
    )

    object Community : BottomNavigationScreen(
        route = TradInDestinations.COMMUNITY_ROUTE,
        unselectedIconResourceId = R.drawable.ic_bottom_nav_community,
        selectedIconResourceId = R.drawable.ic_bottom_nav_community_sel
    )

    object Add : BottomNavigationScreen(
        route = TradInDestinations.ADD_ROUTE,
        unselectedIconResourceId = R.drawable.ic_bottom_nav_add,
        selectedIconResourceId = R.drawable.ic_bottom_nav_add_sel
    )

    object Chat : BottomNavigationScreen(
        route = TradInDestinations.CHAT_ROUTE,
        unselectedIconResourceId = R.drawable.ic_bottom_nav_chat,
        selectedIconResourceId = R.drawable.ic_bottom_nav_chat_sel
    )

    object Profile : BottomNavigationScreen(
        route = TradInDestinations.PROFILE_ROUTE,
        unselectedIconResourceId = R.drawable.ic_bottom_nav_profile,
        selectedIconResourceId = R.drawable.ic_bottom_nav_profile_sel
    )
}

val bottomNavItems = listOf(
    BottomNavigationScreen.Home,
    BottomNavigationScreen.Community,
    BottomNavigationScreen.Add,
    BottomNavigationScreen.Chat,
    BottomNavigationScreen.Profile
)

val bottomNavItemRouteSet = setOf(
    TradInDestinations.HOME_ROUTE,
    TradInDestinations.COMMUNITY_ROUTE,
    TradInDestinations.ADD_ROUTE,
    TradInDestinations.CHAT_ROUTE,
    TradInDestinations.PROFILE_ROUTE
)