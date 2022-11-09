package com.mocomoco.tradin.presentation.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mocomoco.tradin.presentation.DetailsScreen
import com.mocomoco.tradin.presentation.MainDestination
import com.mocomoco.tradin.presentation.MainScreen
import com.mocomoco.tradin.presentation.TradInDestinations.ADD_ROUTE
import com.mocomoco.tradin.presentation.TradInDestinations.BACK
import com.mocomoco.tradin.presentation.TradInDestinations.CATEGORY_ROUTE
import com.mocomoco.tradin.presentation.TradInDestinations.DETAILS_ROUTE
import com.mocomoco.tradin.presentation.TradInDestinations.LOCATION_ROUTE
import com.mocomoco.tradin.presentation.TradInDestinations.LOGIN_ROUTE
import com.mocomoco.tradin.presentation.TradInDestinations.MAIN_ROUTE
import com.mocomoco.tradin.presentation.TradInDestinations.ON_BOARDING_ROUTE
import com.mocomoco.tradin.presentation.TradInDestinations.SIGNUP_ROUTE
import com.mocomoco.tradin.presentation.TradInDestinations.WIP
import com.mocomoco.tradin.presentation.WipScreen
import com.mocomoco.tradin.presentation.add.AddScreen
import com.mocomoco.tradin.presentation.category.CategoryScreen
import com.mocomoco.tradin.presentation.location.LocationSelectScreen
import com.mocomoco.tradin.presentation.login.LoginScreen
import com.mocomoco.tradin.presentation.main.chat.ChatListScreen
import com.mocomoco.tradin.presentation.main.community.CommunityScreen
import com.mocomoco.tradin.presentation.main.home.HomeScreen
import com.mocomoco.tradin.presentation.main.inventory.InventoryScreen
import com.mocomoco.tradin.presentation.main.profile.ProfileScreen
import com.mocomoco.tradin.presentation.nav.Arguments.CATEGORY_DISPLAY
import com.mocomoco.tradin.presentation.nav.Arguments.CATEGORY_ID
import com.mocomoco.tradin.presentation.nav.Arguments.FEED_ID
import com.mocomoco.tradin.presentation.nav.Arguments.FROM_INVENTORY
import com.mocomoco.tradin.presentation.on_boarding.OnBoardingScreen
import com.mocomoco.tradin.presentation.signup.SignupScreen

@Composable
fun TradInNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MAIN_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(ON_BOARDING_ROUTE) {
            OnBoardingScreen() {
                navController.navigate(MAIN_ROUTE) {
                    popUpTo(ON_BOARDING_ROUTE) {
                        inclusive = true
                    }
                }
            }
        }

        composable(MAIN_ROUTE) {
            MainScreen(
                onNavEvent = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(
            "$DETAILS_ROUTE/{${FEED_ID}}",
            arguments = listOf(navArgument(FEED_ID) { type = NavType.IntType })
        ) {
            DetailsScreen()
        }

        composable(
            "$CATEGORY_ROUTE/{${CATEGORY_ID}}/{${CATEGORY_DISPLAY}}",
            arguments = listOf(
                navArgument(CATEGORY_ID) {
                    type = NavType.IntType
                },
                navArgument(CATEGORY_DISPLAY) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            entry.arguments?.getInt(CATEGORY_ID)?.let { id ->
                entry.arguments?.getString(CATEGORY_DISPLAY)?.let { display ->
                    CategoryScreen(
                        categoryId = id,
                        categoryDisplay = display,
                        onNavEvent = { route ->
                            if (route == BACK) {
                                navController.popBackStack()
                            } else {
                                navController.navigate(route)
                            }
                        }
                    )
                }
            }
        }

        composable(
            "$ADD_ROUTE/{${FROM_INVENTORY}}",
            arguments = listOf(navArgument(FROM_INVENTORY) { type = NavType.BoolType })
        ) {
            AddScreen { route ->
                if (route.isNotEmpty()) {
                    navController.navigate(route)
                } else {
                    navController.popBackStack()
                }
            }
        }


        composable(LOGIN_ROUTE) {
            LoginScreen(
                onBack = {
                    navController.popBackStack(LOGIN_ROUTE, inclusive = true)
                },
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
            HomeScreen(
                onNavEvent = {
                    onNavEvent(it)
                }
            )
        }

        composable(MainDestination.COMMUNITY_ROUTE) {
            CommunityScreen() {
                onNavEvent(LOGIN_ROUTE)
            }
        }

        composable(MainDestination.INVENTORY_ROUTE) {
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

object Arguments {
    const val FROM_INVENTORY = "fromInventory"
    const val FEED_ID = "feedId"
    const val CATEGORY_ID = "categoryId"
    const val CATEGORY_DISPLAY = "categoryDisplay"
}

