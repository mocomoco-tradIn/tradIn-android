package com.mocomoco.tradin.ui.main.community

import androidx.compose.runtime.Composable
import com.mocomoco.tradin.ui.WipScreen

@Composable
fun CommunityScreen(
    onClick: () -> Unit
) {
    WipScreen(title ="CommunityScreen") {
        onClick()
    }
}