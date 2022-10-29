package com.mocomoco.tradin.presentation.main.community

import androidx.compose.runtime.Composable
import com.mocomoco.tradin.presentation.WipScreen

@Composable
fun CommunityScreen(
    onClick: () -> Unit
) {
    WipScreen(title ="CommunityScreen") {
        onClick()
    }
}