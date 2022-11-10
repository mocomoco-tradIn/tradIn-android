package com.mocomoco.tradin.presentation.details

import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import com.mocomoco.tradin.presentation.common.*
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

    var showImageDetails by remember {
        mutableStateOf(false)
    }
    var showReport by remember {
        mutableStateOf(false)
    }

    val toolbarHeight = 56.dp
    val buttonHeight = 60.dp
    val toolbarAnim by animateDpAsState(targetValue = if (isScrollDown) -toolbarHeight else 0.dp)
    val buttonAnim by animateDpAsState(targetValue = if (isScrollDown) buttonHeight else 0.dp)

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
                showReport = true
                scope.launch {
                    bottomSheetState.hide()
                }
            }
        },
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetState = bottomSheetState
    ) {

        Box(modifier = Modifier
            .fillMaxSize()
            .background(White)) {

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
                                .clickable {
                                    showImageDetails = true
                                }
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
                                    .clip(RoundedCornerShape(50))
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.ic_default_profile_image_30_dp),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
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
                            painter = painterResource(id = if (state.details.isLike) R.drawable.ic_like_on_36_dp else R.drawable.ic_like_off_36_dp),
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
            StartTitleToolbar(
                title = state.details.title,
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
    if (showImageDetails) {
        ImageDetails(imageUrls = state.details.imageUrls) {
            showImageDetails = false
        }
    }

    if (showReport) {
        ReportFeed(viewModel = viewModel, state = state) { reported ->
            showReport = false
            viewModel.clearReportState()
            if (reported) {
                onNavEvent(BACK)
            }
        }
    }
}


@Composable
fun ColumnScope.DetailsBottomSheet(
    onClickReport: () -> Unit
) {
    Text(
        text = "신고하기",
        style = RomTextStyle.text16,
        color = Pink1,
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

@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ImageDetails(
    imageUrls: List<String>,
    onClickBack: () -> Unit
) {

    val pagerState = rememberPagerState()
    var scale by remember { mutableStateOf(1f) }
    var rotationState by remember { mutableStateOf(1f) }

    BackHandler {
        onClickBack()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black)
    ) {
        StartTitleToolbar(
            showBack = true,
            onClickBack = { onClickBack() },
            backIconColor = White,
            backgroundColor = Transparent,
            modifier = Modifier.align(Alignment.TopCenter),
            showBottomLine = false
        )

        Column(
            modifier = Modifier
                .background(Black)
                .align(Alignment.Center)
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, zoom, rotation ->
                        scale *= zoom
                        rotationState += rotation
                    }
                }
                .pointerInteropFilter {
                    Logger.log("action ${it.action}")
                    when {
                        it.action == MotionEvent.ACTION_UP || it.action == MotionEvent.ACTION_POINTER_UP -> {
                            scale = 1f
                            rotationState = 1f
                        }
                    }
                    false
                }
        ) {
            HorizontalPager(
                count = imageUrls.size,
                state = pagerState,
                key = { imageUrls[it] },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) { index ->
                DefaultAsyncImage(
                    url = imageUrls[index],
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RectangleShape)
                        .graphicsLayer {
                            scaleX = maxOf(.5f, minOf(3f, scale))
                            scaleY = maxOf(.5f, minOf(3f, scale))
                            rotationZ = rotationState
                        },
                    contentScale = ContentScale.FillWidth
                )
            }

            VerticalSpacer(dp = 20.dp)

            PagerDot(pagerState = pagerState, selectedColor = White, unSelectedColor = White_Op50)
        }
    }
}

@Composable
fun ReportFeed(
    viewModel: DetailsViewModel,
    state: DetailsState,
    onClickBack: (reported: Boolean) -> Unit
) {
    val reportState = viewModel.reportState.collectAsState().value

    val buttonAnim by animateDpAsState(targetValue = if (!reportState.isSelected) 300.dp else 0.dp)

    var etcReasonText by remember {
        mutableStateOf("")
    }

    var showConfirmDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.getReportReasons()
    }

    BackHandler {
        onClickBack(false)
    }

    if (reportState.completeReport) {
        onClickBack(true)
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {
            DefaultToolbar(
                title = "사용자 신고하기",
                rightButtons = listOf(painterResource(id = R.drawable.ic_close_24_dp) to {
                    onClickBack(
                        false
                    )
                })
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                VerticalSpacer(dp = 10.dp)
                Text(
                    text = state.details.title,
                    style = RomTextStyle.text13,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Gray0
                )

                VerticalSpacer(dp = 6.dp)
                Text(text = "게시글을 신고하고 싶으신가요?", style = RomTextStyle.text17, color = Gray0)

                VerticalSpacer(dp = 8.dp)
                Text(text = "사유 선택", style = RomTextStyle.text17, color = Gray0)

                reportState.reasons.forEach { reportReason ->
                    VerticalSpacer(dp = 4.dp)
                    ReportReasonItem(reportReason) {
                        viewModel.onClickReportReason(reportReason.title, reportReason.index)
                    }
                }

                if (reportState.isSelectEtc) {
                    DefaultTextFields(
                        value = etcReasonText,
                        onValueChange = { etcReasonText = if (it.length <= 20) it else etcReasonText },
                        placeholderText = "기타 사유를 입력해주세요 (20자 이내)"
                    )
                }
            }
        }

        DefaultRomButton(
            text = "신고하기", enable = true, modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
                .padding(bottom = 28.dp)
                .offset(x = 0.dp, y = buttonAnim)
        ) {
            showConfirmDialog = true
        }
    }
    if (showConfirmDialog) {
        ConfirmDialog(
            contentText = "해당 게시글을 신고하시겠습니까?",
            cancelText = "취소",
            confirmText = "신고하기",
            onClickConfirm = {
                viewModel.report(
                    state.details.feedId,
                    if (reportState.isSelectEtc) {
                        "${reportState.selectedReason}: $etcReasonText"
                    } else {
                        reportState.selectedReason
                    }
                )
                showConfirmDialog = false
            },
            onClickCancel = {
                showConfirmDialog = false
            },
            onDismiss = {
                showConfirmDialog = false
            }
        )
    }
}

@Composable
fun ReportReasonItem(
    data: ReportState.ReportReason,
    onClickReason: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = if (data.isChecked) R.drawable.ic_radio_button_on_24_dp else R.drawable.ic_radio_button_off_24_dp),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .clickable { onClickReason() }
        )
        HorizontalSpacer(dp = 6.dp)
        Text(
            text = data.title,
            style = RomTextStyle.text16,
            color = Gray0,
            modifier = Modifier.weight(1f)
        )
    }
}
