package com.mocomoco.tradin.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mocomoco.tradin.ui.main.home.HomeScreen
import com.mocomoco.tradin.ui.login.LoginScreen
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
            WipScreen(title = TradInDestinations.DETAILS_ROUTE)
        }  // todo make

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


        // todo add screens
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
            WipScreen(title = MainDestination.COMMUNITY_ROUTE) {
                onNavEvent(TradInDestinations.LOGIN)
            }
        }

        composable(MainDestination.ADD_ROUTE) {
            WipScreen(title = MainDestination.ADD_ROUTE) {
                onNavEvent(TradInDestinations.LOGIN)
            }// todo make
        }

        composable(MainDestination.CHAT_ROUTE) {
            WipScreen(title = MainDestination.CHAT_ROUTE) {
                onNavEvent(TradInDestinations.LOGIN)

            }// todo make
        }

        composable(MainDestination.PROFILE_ROUTE) {
            WipScreen(title = MainDestination.PROFILE_ROUTE) {
                onNavEvent(TradInDestinations.LOGIN)

            } // todo make
        }
    }
}