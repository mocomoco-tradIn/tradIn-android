package com.mocomoco.tradin.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mocomoco.tradin.ui.theme.White

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isBottomDestination = currentDestination?.let {
        bottomNavItemRouteSet.contains(it.route)
    }

    Scaffold(
        bottomBar = {
            if (isBottomDestination == true) {
                TradInBottomNavigation(
                    navController = navController,
                    currentDestination = currentDestination
                )
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

@Composable
fun TradInBottomNavigation(
    navController: NavHostController,
    currentDestination: NavDestination?
) {
    BottomNavigation(
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = White,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Row {
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
                        selectedContentColor = Color.Black,
                        modifier = Modifier.weight(1f, true)
                    )
                }
            }
        }
    }
}