package com.mocomoco.tradin.presentation.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.R
import com.mocomoco.tradin.presentation.theme.Gray0


@Composable
fun LikeAnimation(
    showLikeAnimation: Boolean
) {
    val height = LocalConfiguration.current.screenHeightDp
    val density = LocalDensity.current

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = showLikeAnimation,
            enter = fadeIn(initialAlpha = 0.5f),
            exit = fadeOut()
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.3f)
                    .background(Gray0)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.BottomCenter)
        ) {
            AnimatedVisibility(
                visible = showLikeAnimation,
                enter = slideInVertically() {
                    // Slide in from 40 dp from the top.
                    with(density) { height.dp.roundToPx() }
                },
                exit = fadeOut()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_like_on_36_dp),
                        contentDescription = null,
                        modifier = Modifier
                            .size(128.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }

    }
}