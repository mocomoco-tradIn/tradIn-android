package com.mocomoco.tradin.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.presentation.theme.Gray0
import com.mocomoco.tradin.presentation.theme.RomTextStyle

@Composable
fun EmptyScreen(modifier: Modifier, painter: Painter, text: String) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painter, contentDescription = null)
            VerticalSpacer(dp = 16.dp)
            Text(text = text, style = RomTextStyle.text13, color = Gray0)
        }
    }
}