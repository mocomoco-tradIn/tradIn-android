package com.mocomoco.tradin.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mocomoco.tradin.presentation.TradInDestinations.DETAILS_ROUTE
import com.mocomoco.tradin.presentation.TradInDestinations.LOCATION_ROUTE
import com.mocomoco.tradin.presentation.TradInDestinations.LOGIN_ROUTE
import com.mocomoco.tradin.presentation.TradInDestinations.MAIN_ROUTE
import com.mocomoco.tradin.presentation.TradInDestinations.SIGNUP_ROUTE
import com.mocomoco.tradin.presentation.TradInDestinations.WIP
import com.mocomoco.tradin.presentation.location.LocationSelectScreen
import com.mocomoco.tradin.presentation.login.LoginScreen
import com.mocomoco.tradin.presentation.main.add.InventoryScreen
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

        composable(MAIN_ROUTE) {
            MainScreen(
                onNavEvent = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(DETAILS_ROUTE) {
            DetailsScreen()
        }

        composable(LOGIN_ROUTE) {
            LoginScreen(
                onClickFindPassword = {
                    navController.navigate(WIP) // todo replace
                },
                onClickSignup = {
                    navController.navigate(SIGNUP_ROUTE) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(SIGNUP_ROUTE) {
            SignupScreen(
                onBack = {
                    navController.popBackStack(SIGNUP_ROUTE, true, true)
                },
                onNavEvent = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(LOCATION_ROUTE) {
            LocationSelectScreen {
                navController.popBackStack(LOCATION_ROUTE, true, true)
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
                onNavEvent(TradInDestinations.LOGIN_ROUTE)
            }
        }

        composable(MainDestination.ADD_ROUTE) {
            InventoryScreen() {
                onNavEvent(it)
            }
        }

        composable(MainDestination.CHAT_ROUTE) {
            ChatListScreen()
        }

        composable(MainDestination.PROFILE_ROUTE) {
            ProfileScreen()
        }
    }
}

