package com.mocomoco.tradin.presentation.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerDot(
    pagerState: PagerState,
    selectedColor: Color,
    unSelectedColor: Color,
    dotRadiusDp: Dp = 4.dp,
    dotIntervalDp: Dp = 8.dp,
    modifier: Modifier = Modifier
) {
    val dotRadius = with(LocalDensity.current) {
        dotRadiusDp.toPx()
    }
    Row(modifier = modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
        repeat(pagerState.pageCount) {
            Canvas(modifier = Modifier) {
                drawCircle(
                    color = if (pagerState.currentPage == it) selectedColor else unSelectedColor,
                    radius = dotRadius
                )
            }
            HorizontalSpacer(dp = dotRadiusDp + dotIntervalDp)
        }
    }
}