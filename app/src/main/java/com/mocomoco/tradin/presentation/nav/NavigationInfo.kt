package com.mocomoco.tradin.presentation

import androidx.annotation.DrawableRes
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.mocomoco.tradin.R


val defaultNavOptionBuilder: NavOptionsBuilder.() -> Unit = {
    launchSingleTop = true
    restoreState = true
}

object TradInDestinations {

    // 완료
    const val LOGIN_ROUTE = "login"
    const val SIGNUP_ROUTE = "signup"
    const val ADD_ROUTE = "addRoute"

    // 진행중
    const val MAIN_ROUTE = "main"
    const val ON_BOARDING_ROUTE = "onBoarding"

    // 시작도 안함
    const val NOTIFICATION_ROUTE = "notification"
    const val SEARCH_ROUTE = "search"
    const val LOCATION_ROUTE = "location"
    const val CATEGORY_ROUTE = "category"
    const val DETAILS_ROUTE = "details"
    const val CHAT_ROUTE = "chat"



    // 심사 후 업데이트

    const val WIP = "wip"

    const val BACK = "back"
}

object MainDestination {
    const val HOME_ROUTE = "home"
    const val COMMUNITY_ROUTE = "community"
    const val INVENTORY_ROUTE = "add"
    const val CHAT_ROUTE = "chat"
    const val PROFILE_ROUTE = " profile"
}

class TradInNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(MainDestination.HOME_ROUTE) {

        }
    }
}

sealed class BottomNavigationScreen(val route: String, @DrawableRes val unselectedIconResourceId: Int, @DrawableRes val selectedIconResourceId: Int) {
    object Home : BottomNavigationScreen(
        route = MainDestination.HOME_ROUTE,
        unselectedIconResourceId = R.drawable.ic_bottom_nav_home,
        selectedIconResourceId = R.drawable.ic_bottom_nav_home_sel
    )

    object Community : BottomNavigationScreen(
        route = MainDestination.COMMUNITY_ROUTE,
        unselectedIconResourceId = R.drawable.ic_bottom_nav_community,
        selectedIconResourceId = R.drawable.ic_bottom_nav_community_sel
    )

    object Add : BottomNavigationScreen(
        route = MainDestination.INVENTORY_ROUTE,
        unselectedIconResourceId = R.drawable.ic_bottom_nav_add,
        selectedIconResourceId = R.drawable.ic_bottom_nav_add_sel
    )

    object Chat : BottomNavigationScreen(
        route = MainDestination.CHAT_ROUTE,
        unselectedIconResourceId = R.drawable.ic_bottom_nav_chat,
        selectedIconResourceId = R.drawable.ic_bottom_nav_chat_sel
    )

    object Profile : BottomNavigationScreen(
        route = MainDestination.PROFILE_ROUTE,
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