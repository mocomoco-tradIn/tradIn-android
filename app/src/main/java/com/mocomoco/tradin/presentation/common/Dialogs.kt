package com.mocomoco.tradin.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mocomoco.tradin.model.SortType
import com.mocomoco.tradin.presentation.theme.*


@Composable
fun SortDialog(
    onDismiss: () -> Unit,
    onClick: (SortType) -> Unit
) {
    val sorted = remember {
        listOf(
            SortType.POPULAR,
            SortType.LATEST,
            SortType.VIEW
        )
    }

    Dialog(
        onDismissRequest = {
            onDismiss()
        }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White, RoundedCornerShape(5.dp))
                .border(borderStrokeBlack2, RoundedCornerShape(5.dp))
                .clip(RoundedCornerShape(5.dp))
        ) {
            sorted.forEachIndexed { index, sortType ->
                Text(
                    text = sortType.display,
                    style = RomTextStyle.text16,
                    color = Gray0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onClick(sortType)
                            onDismiss()
                        }
                        .padding(
                            top = if (index == 0) 24.dp else 18.dp,
                            bottom = if (index == 2) 24.dp else 18.dp
                        ),
                    textAlign = TextAlign.Center
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Gray6)
                )
            }

        }

    }
}
