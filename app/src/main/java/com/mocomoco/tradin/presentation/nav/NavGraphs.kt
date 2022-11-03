package com.mocomoco.tradin.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mocomoco.tradin.presentation.TradInDestinations.LOCATION
import com.mocomoco.tradin.presentation.TradInDestinations.SIGNUP
import com.mocomoco.tradin.presentation.TradInDestinations.WIP
import com.mocomoco.tradin.presentation.location.LocationSelectScreen
import com.mocomoco.tradin.presentation.login.LoginScreen
import com.mocomoco.tradin.presentation.main.chat.ChatListScreen
import com.mocomoco.tradin.presentation.main.community.CommunityScreen
import com.mocomoco.tradin.presentation.main.home.HomeScreen
import com.mocomoco.tradin.presentation.main.profile.ProfileScreen
import com.mocomoco.tradin.presentation.signup.SignupScreen

@Composable
fun TradInNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = TradInDestinations.MAIN_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(TradInDestinations.MAIN_ROUTE) {
            MainScreen(
                onNavEvent = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(TradInDestinations.DETAILS_ROUTE) {
            DetailsScreen()
        }

        composable(TradInDestinations.LOGIN) {
            LoginScreen(
                onClickFindPassword = {
                    navController.navigate(WIP) // todo replace
                },
                onClickSignup = {
                    navController.navigate(SIGNUP) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(SIGNUP) {
            SignupScreen(
                onClickBack = {
                    navController.popBackStack(SIGNUP, true, true)
                },
                onNavEvent = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(LOCATION) {
            LocationSelectScreen {
                navController.popBackStack(LOCATION, true, true)
            }
        }

        composable(WIP) {
            WipScreen()
        }
    }
}

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestination.HOME_ROUTE,
    onNavEvent: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(MainDestination.HOME_ROUTE) {
            HomeScreen()
        }

        composable(MainDestination.COMMUNITY_ROUTE) {
            CommunityScreen() {
                onNavEvent(TradInDestinations.LOGIN)
            }
        }

        composable(MainDestination.ADD_ROUTE) {

        }

        composable(MainDestination.CHAT_ROUTE) {
            ChatListScreen()
        }

        composable(MainDestination.PROFILE_ROUTE) {
            ProfileScreen()
        }
    }
}

object Arguments {
    const val LOCATION_CODE = "locationCode"
    const val LOCATION_DISPLAY = "locationDisplay"

}

