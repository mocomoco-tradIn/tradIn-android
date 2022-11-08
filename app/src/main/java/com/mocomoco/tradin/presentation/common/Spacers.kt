package com.mocomoco.tradin.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.mocomoco.tradin.presentation.theme.Gray0

@Composable
fun VerticalSpacer(dp: Dp) {
    Spacer(
        modifier = Modifier
            .height(dp)
    )
}

@Composable
fun HorizontalSpacer(dp: Dp) {
    Spacer(modifier = Modifier
        .width(dp)
    )
}
