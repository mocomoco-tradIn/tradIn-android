package com.mocomoco.tradin.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.model.FeedStatus
import com.mocomoco.tradin.presentation.theme.RomTextStyle

class StatusTag {
}


@Composable
fun StatusTag(status: FeedStatus) {
    if (status != FeedStatus.NONE) {
        Text(
            text = status.display,
            style = RomTextStyle.text12,
            color = status.textColor,
            fontWeight = FontWeight(700),
            modifier = Modifier
                .background(
                    color = status.backgroundColor,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(horizontal = 8.dp, vertical = 6.dp)
        )
    }

}