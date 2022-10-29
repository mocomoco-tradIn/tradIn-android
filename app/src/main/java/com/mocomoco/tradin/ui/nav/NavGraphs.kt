package com.mocomoco.tradin.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mocomoco.tradin.ui.main.home.HomeScreen
import com.mocomoco.tradin.ui.login.LoginScreen
import com.mocomoco.tradin.ui.main.chat.ChatListScreen
import com.mocomoco.tradin.ui.main.community.CommunityScreen
import com.mocomoco.tradin.ui.main.profile.ProfileScreen
import com.mocomoco.tradin.ui.signup.SignupScreen

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
                onClickSignup = {
                    navController.navigate(TradInDestinations.SIGNUP) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(TradInDestinations.SIGNUP) {
            SignupScreen(onClickBack = {
                navController.popBackStack(TradInDestinations.SIGNUP, true, true)
            })
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