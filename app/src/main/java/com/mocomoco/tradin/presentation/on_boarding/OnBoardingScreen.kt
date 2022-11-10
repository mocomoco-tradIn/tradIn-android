package com.mocomoco.tradin.presentation.on_boarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mocomoco.tradin.R
import com.mocomoco.tradin.presentation.common.DefaultRomButton
import com.mocomoco.tradin.presentation.common.PagerDot
import com.mocomoco.tradin.presentation.theme.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(
    onNavToMain: () -> Unit
) {

    val pagerState = rememberPagerState()

    val images = listOf(
        R.drawable.img_on_boarding_1,
        R.drawable.img_on_boarding_2,
        R.drawable.img_on_boarding_3,
        R.drawable.img_on_boarding_4,
    )

    val texts = listOf(
        "내 서랍에상품을 등록해요",
        "원하는 상품을 찾아 물물교환해요",
        "마이페이지에서\n교환된 상품을 확인해요!",
        "새롭게 얻은 상품으로\n또 다른 교환을 시작해요!"
    )

    val screenHeight = LocalConfiguration.current.screenHeightDp

    Box(modifier = Modifier.fillMaxSize()) {

        PagerDot(
            pagerState = pagerState,
            selectedColor = Blue1,
            unSelectedColor = Gray6,
            modifier = Modifier.padding(top = (screenHeight.dp / 8))
        )

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {

            HorizontalPager(count = images.size, state = pagerState) { index ->
                Image(
                    painter = painterResource(id = images[index]),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }



        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.BottomCenter)
        ) {

            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f)
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(Transparent, White)
                            )
                        )
                )



                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(7f)
                        .background(White),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    texts.getOrNull(pagerState.currentPage)?.let {

                        AnimatedVisibility(
                            visible = true,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Text(
                                text = it,
                                style = RomTextStyle.text20,
                                fontWeight = FontWeight(600),
                                color = Gray0,
                                textAlign = TextAlign.Center
                            )
                        }

                        if (pagerState.currentPage == images.lastIndex) {
                            DefaultRomButton(
                                text = "시작하기", enable = true, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                onNavToMain()
                            }
                        }
                    }
                }
            }

        }
    }
}

