package com.mocomoco.tradin.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.presentation.theme.*


@Composable
fun CategoryChip(
    value: String,
    selected: Boolean,
    onClick: (Boolean) -> Unit
) {
    val round = RoundedCornerShape(50)
    Box(
        modifier = Modifier
            .background(
                color = if (selected) Violet1 else Gray7,
                shape = round
            )
            .border(
                width = 2.dp,
                shape = round,
                color = if (selected) Gray0 else Gray6
            )
            .clip(shape = round)
            .clickable {
                onClick(!selected)
            }
    ) {
        Row {
            HorizontalSpacer(dp = 12.dp)
            Column {
                VerticalSpacer(dp = 6.dp)
                Text(
                    text = value,
                    style = RomTextStyle.text13,
                    color = if (selected) White else Gray1,
                    textAlign = TextAlign.Center
                )
                VerticalSpacer(dp = 6.dp)
            }
            HorizontalSpacer(dp = 12.dp)
        }

    }
}