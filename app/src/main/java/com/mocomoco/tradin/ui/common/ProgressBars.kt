package com.mocomoco.tradin.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.mocomoco.tradin.ui.theme.Black
import com.mocomoco.tradin.ui.theme.White
import com.mocomoco.tradin.ui.theme.YellowGreen2

@Composable
fun SignupProgressBar(
    total: Int,
    current: Int,
    modifier: Modifier = Modifier
) {

    var parentSize by remember {
        mutableStateOf(Size.Zero)
    }

    Box(modifier = modifier.onGloballyPositioned {
        parentSize = it.size.toSize()
    }) {
        val width = with(LocalDensity.current) {
            parentSize.width.toDp() / total
        }

        for (i in 0 until total) {
            val shape = when (i) {
                0 -> {
                    RoundedCornerShape(50, 0, 0, 50)
                }
                total - 1 -> {
                    RoundedCornerShape(0, 50, 50, 0)
                }
                else -> {
                    RoundedCornerShape(0, 0, 0, 0)
                }
            }

            Spacer(
                modifier = Modifier
                    .offset((width - 2.dp) * i, 0.dp)
                    .width(width)
                    .height(10.dp)
                    .border(
                        width = 2.dp,
                        color = Black,
                        shape = shape
                    )
                    .background(
                        color = if (i <= current) {
                            YellowGreen2
                        } else {
                            White
                        },
                        shape = shape
                    )
            )
        }
    }
}


