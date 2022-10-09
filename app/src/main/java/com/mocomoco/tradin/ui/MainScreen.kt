package com.mocomoco.tradin.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mocomoco.tradin.ui.theme.White

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                currentDestination?.hierarchy?.forEach { Log.d("SR-N", "${it.route} / ${it.id}") }

                bottomNavItems.forEach { bottomNavItem ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = bottomNavItem.iconResourceId),
                                contentDescription = bottomNavItem.route
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == bottomNavItem.route } == true,
                        onClick = {
                            navController.navigate(bottomNavItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        selectedContentColor = Color.Black
                    )
                }
            }
        }
    ) { innerPadding ->
        TradInNavGraph(
            navController = navController,
            startDestination = TradInDestinations.HOME_ROUTE,
            modifier = Modifier.padding(innerPadding)
        )
    }

}