package com.mocomoco.tradin.presentation.common

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun ConfirmDialog(
    contentText: String,
    cancelText: String = "취소",
    confirmText: String = "",
    onClickConfirm: () -> Unit,
    onClickCancel: () -> Unit,
    onDismiss: () -> Unit
) {
    BackHandler {
        onDismiss()
    }
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White, RoundedCornerShape(10.dp))
                .border(borderStrokeBlack2, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
        ) {
            Text(
                text = contentText,
                style = RomTextStyle.text17,
                color = Gray0,
                fontWeight = FontWeight(600),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 28.dp),
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray7)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                if (cancelText.isNotEmpty()) {
                    Text(
                        text = cancelText,
                        style = RomTextStyle.text17,
                        color = Gray2,
                        fontWeight = FontWeight(600),
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onClickCancel()
                            }
                            .padding(vertical = 14.dp),
                        textAlign = TextAlign.Center
                    )
                }
                if (cancelText.isNotEmpty() && confirmText.isNotEmpty()) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(Gray7)
                    )
                }
                if (confirmText.isNotEmpty()) {
                    Text(
                        text = confirmText,
                        style = RomTextStyle.text17,
                        color = Pink1,
                        fontWeight = FontWeight(600),
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onClickConfirm()
                            }
                            .padding(vertical = 14.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }

}