package com.mocomoco.tradin.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mocomoco.tradin.ui.theme.Shapes
import com.mocomoco.tradin.ui.theme.TradInTypography
import com.mocomoco.tradin.ui.theme.defaultBorderStroke

@Composable
fun LongRomButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color,
    enable: Boolean,
    textColor: Color,
    onClick: () -> Unit
) {
    TextButton(
        onClick = {
            if (enable) {
                onClick.invoke()
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = Shapes.large)
            .border(defaultBorderStroke, Shapes.large)
    ) {
        Text(
            text = text,
            style = TradInTypography.h3,
            color = textColor,
        )
    }
}