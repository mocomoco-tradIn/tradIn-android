package com.mocomoco.tradin.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun VerticalSpacer(dp: Dp) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(dp)
    )
}

@Composable
fun HorizontalSpacer(dp: Dp) {
    Spacer(modifier = Modifier
        .width(dp)
        .background(Color.Gray)
    )
}
