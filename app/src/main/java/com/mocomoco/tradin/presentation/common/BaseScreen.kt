package com.mocomoco.tradin.presentation.common

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.util.sharedActivityViewModel

@Composable
inline fun <reified T : BaseViewModel> BaseScreen(
    baseViewModel: T = hiltViewModel(LocalContext.current as ComponentActivity),
    content: @Composable () -> Unit
) {
    val loadingState = baseViewModel.loading.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        content()
        if (loadingState.value) {
            RomCircularProgressIndicator()
        }
    }
}