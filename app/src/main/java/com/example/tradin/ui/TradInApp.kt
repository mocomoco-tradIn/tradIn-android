package com.example.tradin.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun TradInApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "") {

    }

}