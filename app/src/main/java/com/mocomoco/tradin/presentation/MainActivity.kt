package com.mocomoco.tradin.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mocomoco.tradin.presentation.main.MainViewModel
import com.mocomoco.tradin.presentation.nav.TradInNavGraph
import com.mocomoco.tradin.presentation.theme.TradInTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                viewModel.splashLoading.value
            }
        }

        setContent {
            TradInTheme {
                TradInNavGraph()
            }
        }
    }
}
