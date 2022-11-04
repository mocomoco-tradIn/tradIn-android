package com.mocomoco.tradin.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.mocomoco.tradin.presentation.theme.Black
import com.mocomoco.tradin.presentation.theme.Blue1
import com.mocomoco.tradin.presentation.theme.White
import com.mocomoco.tradin.presentation.theme.YellowGreen2


@Composable
fun RomCircularProgressIndicator() {
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(enabled = false) {
            // do nothing
        }
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = Blue1,
            strokeWidth = 4.dp
        )
    }
}

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


