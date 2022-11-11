package com.mocomoco.tradin.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mocomoco.tradin.presentation.TradInDestinations.LOGIN_ROUTE
import com.mocomoco.tradin.presentation.TradInDestinations.MAIN_ROUTE
import com.mocomoco.tradin.presentation.TradInDestinations.ON_BOARDING_ROUTE
import com.mocomoco.tradin.presentation.nav.TradInNavGraph
import com.mocomoco.tradin.presentation.theme.TradInTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: GlobalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                viewModel.splashLoading.value
            }
        }



        setContent {
            TradInTheme {
                TradInNavGraph(
                    startDestination = if (viewModel.showOnBoarding()) ON_BOARDING_ROUTE else if (viewModel.isLogin()) MAIN_ROUTE else LOGIN_ROUTE
                )
            }
        }
    }
}
