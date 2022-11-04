package com.mocomoco.tradin.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.presentation.theme.*

@Composable
fun DefaultRomButton(
    modifier: Modifier = Modifier.fillMaxWidth(),
    text: String,
    textStyle: TextStyle = RomTextStyle.text17,
    enable: Boolean,
    onClick: () -> Unit
) {

    Text(
        text = text,
        style = textStyle,
        color = if (enable) White else Gray2,
        modifier = if (enable) {
            modifier
                .background(color = Blue1, shape = Shapes.large)
                .border(borderStrokeBlack2, Shapes.large)
                .clip(shape = Shapes.large)
                .clickable {
                    onClick.invoke()
                }
                .padding(12.dp)
        } else {
            modifier
                .background(color = Gray7, shape = Shapes.large)
                .padding(12.dp)
        },
        textAlign = TextAlign.Center
    )
}
