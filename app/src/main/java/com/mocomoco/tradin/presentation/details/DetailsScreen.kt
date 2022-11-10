package com.mocomoco.tradin.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mocomoco.tradin.R
import com.mocomoco.tradin.common.Constants
import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.model.TradeMethod
import com.mocomoco.tradin.presentation.TradInDestinations.BACK
import com.mocomoco.tradin.presentation.TradInDestinations.REPORT_ROUTE
import com.mocomoco.tradin.presentation.common.*
import com.mocomoco.tradin.presentation.details.DetailsViewModel
import com.mocomoco.tradin.presentation.theme.*
import com.mocomoco.tradin.util.ext.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    feedId: Int,
    onNavEvent: (String) -> Unit
) {

    val toolbarHeight = 56.dp
    val bottonHeight = 60.dp

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val scope = rememberCoroutineScope()

    val state = viewModel.state.collectAsState().value
    val loading = viewModel.loading.collectAsState().value

    val pagerState = rememberPagerState()

    val scrollState = rememberScrollState()


    var isScrollDown by remember {
        mutableStateOf(false)
    }

    val toolbarAnim by animateDpAsState(targetValue = if (isScrollDown) -toolbarHeight else 0.dp)
    val buttonAnim by animateDpAsState(targetValue = if (isScrollDown) bottonHeight else 0.dp)

    var showLikeAnimation by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.getDetails(feedId)

        launch {
            viewModel.toastMessage.collect {
                it.showToast(context)
            }
        }

        launch {
            viewModel.successLikeEvent.collect {
                if (it) {
                    showLikeAnimation = true
                    delay(Constants.LikeAnimDuration)
                    showLikeAnimation = false
                }
            }
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            DetailsBottomSheet {
                onNavEvent(REPORT_ROUTE)
                scope.launch {
                    bottomSheetState.hide()
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetState = bottomSheetState
    ) {

        Box(modifier = Modifier.fillMaxSize().background(White)) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .pointerInput(true) {
                        detectVerticalDragGestures { change, dragAmount ->
                            Logger.log("dragAmount : $dragAmount  dragAmount <= 0 : ${dragAmount <= 0}")
                            isScrollDown = dragAmount <= 0
                        }
                    }
            ) {

                // --- 피드 이미지 ---
                Box {
                    HorizontalPager(
                        count = state.details.imageUrls.size,
                        state = pagerState
                    ) { index ->
                        DefaultAsyncImage(
                            url = state.details.imageUrls[index], modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                        )
                    }

                    PagerDot(
                        pagerState = pagerState,
                        selectedColor = White,
                        unSelectedColor = White_Op50,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 14.dp)
                    )
                }

                // --- 바디 상단 ---
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = state.details.category,
                        style = RomTextStyle.text13,
                        fontWeight = FontWeight(500),
                        color = Gray0
                    )
                    VerticalSpacer(dp = 4.dp)

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = state.details.title,
                            style = RomTextStyle.text17,
                            color = Gray0,
                            fontWeight = FontWeight(500),
                            modifier = Modifier.weight(1f)
                        )

                        HorizontalSpacer(dp = 16.dp)

                        StatusTag(status = state.details.status)
                    }

                    VerticalSpacer(dp = 16.dp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (state.details.profileImage.isNotEmpty()) {
                            DefaultAsyncImage(
                                url = state.details.profileImage, modifier = Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(50f))
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.ic_default_profile),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(color = Gray6)

                            )
                        }

                        HorizontalSpacer(dp = 8.dp)

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 2.dp)
                        ) {
                            Text(
                                text = "작성자",
                                style = RomTextStyle.text11,
                                color = Gray2,
                                fontWeight = FontWeight(500)
                            )
                            VerticalSpacer(dp = 1.dp)
                            Text(
                                text = state.details.profileName,
                                style = RomTextStyle.text11,
                                color = Gray1,
                                fontWeight = FontWeight(500)
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.ic_like_off_36_dp),
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                viewModel.like(state.details.feedId)
                            }
                        )
                        VerticalSpacer(dp = 2.dp)
                        Text(
                            text = "${state.details.likeCount}",
                            style = RomTextStyle.text18,
                            color = Gray0,
                            fontWeight = FontWeight(500)
                        )
                    }
                    VerticalSpacer(dp = 12.dp)


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            val tradeAnnotated = buildAnnotatedString {
                                withStyle(SpanStyle(Gray2)) {
                                    append("교환 ")
                                }
                                withStyle(SpanStyle(Gray1)) {
                                    append("${state.details.tradeCount}")
                                }
                            }
                            val chatAnnotated = buildAnnotatedString {
                                withStyle(SpanStyle(Gray2)) {
                                    append("채팅 ")
                                }
                                withStyle(SpanStyle(Gray1)) {
                                    append("${state.details.chatCount}")
                                }
                            }
                            val viewAnnotated = buildAnnotatedString {
                                withStyle(SpanStyle(Gray2)) {
                                    append("조회 ")
                                }
                                withStyle(SpanStyle(Gray1)) {
                                    append("${state.details.viewCount}")
                                }
                            }
                            Text(
                                text = tradeAnnotated,
                                style = RomTextStyle.text13,
                                fontWeight = FontWeight(500)
                            )
                            HorizontalSpacer(dp = 16.dp)
                            Text(
                                text = chatAnnotated,
                                style = RomTextStyle.text13,
                                fontWeight = FontWeight(500)
                            )
                            HorizontalSpacer(dp = 16.dp)
                            Text(
                                text = viewAnnotated,
                                style = RomTextStyle.text13,
                                fontWeight = FontWeight(500)
                            )
                        }

                        Text(
                            text = state.details.createdAt,
                            style = RomTextStyle.text12,
                            color = Gray3
                        )
                    }


                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Gray7)
                )

                VerticalSpacer(dp = 4.dp)

                // --- 바디 하단 ---
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    Text(
                        text = state.details.body,
                        style = RomTextStyle.text15,
                        color = Black,
                        fontWeight = FontWeight(500),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    VerticalSpacer(dp = 24.dp)

                    Row {
                        DetailsIconInfoItem(
                            painterResource(id = R.drawable.ic_location_32_dp),
                            state.details.location
                        )

                        HorizontalSpacer(dp = 16.dp)

                        if (state.details.tradeMethod != TradeMethod.None) {
                            DetailsIconInfoItem(
                                painter = painterResource(id = state.details.tradeMethod.resId),
                                text = state.details.tradeMethod.display
                            )
                        }
                    }
                }
                VerticalSpacer(dp = 100.dp)
            }


            // --- 상단 툴바 ---
            DefaultToolbar(
                showBack = true,
                onClickBack = { onNavEvent(BACK) },
                rightButtons = listOf(painterResource(id = R.drawable.ic_more_24_dp) to { scope.launch { bottomSheetState.show() } }),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(x = 0.dp, y = toolbarAnim)
            )

            // --- 하단 버튼 ---
            DefaultRomButton(
                text = "채팅하기",
                enable = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .offset(x = 0.dp, y = buttonAnim)
            ) {
                // todo
            }
        }


        // --- animation ---
        LikeAnimation(showLikeAnimation = showLikeAnimation)

        // --- loading ---
        if (loading) {
            RomCircularProgressIndicator()
        }
    }
}

@Composable
fun Details() {

}


@Composable
fun ColumnScope.DetailsBottomSheet(
    onClickReport: () -> Unit
) {
    Text(
        text = "신고하기",
        style = RomTextStyle.text16,
        color = Gray0,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickReport() }
            .padding(top = 24.dp, bottom = 18.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun DetailsIconInfoItem(painter: Painter, text: String) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .height(85.dp)
            .background(color = Gray8, shape = RoundedCornerShape(10.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            contentScale = ContentScale.Crop
        )

        VerticalSpacer(dp = 8.dp)

        Text(text = text, style = RomTextStyle.text13, color = Gray1)
    }
}
