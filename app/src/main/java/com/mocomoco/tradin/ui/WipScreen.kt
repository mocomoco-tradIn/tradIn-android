package com.mocomoco.tradin.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.ui.theme.TradInTypography


@Composable
fun WipScreen(
    modifier: Modifier = Modifier,
    title: String = "",
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    onClick.invoke()
                },
            text = "\"${title}\" 화면 준비중",
            style = TradInTypography.body2,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}
