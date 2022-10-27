package com.mocomoco.tradin.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
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
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = White,
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            BottomNavigation(
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                modifier = Modifier.height(66.dp)
            ) {
                bottomNavItems.forEach { bottomNavItem ->
                    val selected =
                        currentDestination?.hierarchy?.any { it.route == bottomNavItem.route } == true
                    BottomNavigationItem(
                        icon = {
                            val resourceId = if (selected) {
                                bottomNavItem.selectedIconResourceId
                            } else {
                                bottomNavItem.unselectedIconResourceId
                            }
                            Icon(
                                painter = painterResource(id = resourceId),
                                contentDescription = bottomNavItem.route,
                            )
                        },
                        selected = selected,
                        enabled = !selected,
                        onClick = {
                            navController.navigate(bottomNavItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        modifier = Modifier.weight(1f, true),
                        unselectedContentColor = Color.Black,
                        selectedContentColor = Color.Black
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
        }
    }
}
