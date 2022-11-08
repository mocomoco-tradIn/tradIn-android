package com.mocomoco.tradin.presentation.main.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mocomoco.tradin.R
import com.mocomoco.tradin.model.Category
import com.mocomoco.tradin.model.Feed
import com.mocomoco.tradin.model.FeedStatus
import com.mocomoco.tradin.model.SortType
import com.mocomoco.tradin.presentation.TradInDestinations
import com.mocomoco.tradin.presentation.common.HorizontalSpacer
import com.mocomoco.tradin.presentation.common.RomCircularProgressIndicator
import com.mocomoco.tradin.presentation.common.VerticalSpacer
import com.mocomoco.tradin.presentation.nav.Arguments.FEED_ID
import com.mocomoco.tradin.presentation.theme.*
import com.mocomoco.tradin.util.ext.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavEvent: (String) -> Unit
) {

    val state = viewModel.state.collectAsState().value

    val loading = viewModel.loading.collectAsState().value

    var showLikeAnimation by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.load(lastId = 0)

        launch {
            viewModel.toastMessage.collect {
                it.showToast(context)
            }
        }

        launch {
            viewModel.successLikeEvent.collect {
                if (it) {
                    showLikeAnimation = true
                    delay(1500L)
                    showLikeAnimation = false
                }
            }
        }
    }

    val categories = remember {
        listOf(
            (Category.Cloth),
            (Category.Book),
            (Category.Hobby),
            (Category.Electronic),
            (Category.Stuff),
            (Category.Idol),
            (Category.Ticket),
            (Category.Etc),
        )
    }

    val title = remember {
        buildAnnotatedString {
            withStyle(style = SpanStyle(Gray0)) {
                append("오고가는\n교환의 재미, ")
            }
            withStyle(style = SpanStyle(Blue1)) {
                append("롬롬")
            }
        }
    }

    val toolbarHeightPx = remember {
        mutableStateOf(0f)
    }

    val toolbarHeight = with(LocalDensity.current) {
        toolbarHeightPx.value.toDp()
    }

    val toolbarOffsetPx = remember {
        mutableStateOf(0f)
    }

    val lazyColumnState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = (toolbarOffsetPx.value + delta)
                toolbarOffsetPx.value = newOffset.coerceIn(-(toolbarHeightPx.value), 0f)
                return Offset.Zero
            }
        }
    }


    val height = LocalConfiguration.current.screenHeightDp
    val density = LocalDensity.current

    var showSortDialog by remember {
        mutableStateOf(false)
    }

    if (showSortDialog) {
        HomeSortDialog(
            onDismiss = {
                showSortDialog = false
            },
            onClick = {
                viewModel.load(sortType = it)
            }
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(top = toolbarHeight),
            state = lazyColumnState
        ) {


            // --- 카테고리 ---
            item {
                VerticalSpacer(dp = 16.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    categories.take(4).forEachIndexed { index, category ->
                        CategoryItem(
                            data = category,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                // todo 카테고리 화면으로 이동
                            }
                        )

                        if (index != 3) {
                            HorizontalSpacer(dp = 28.dp)
                        }
                    }
                }
                VerticalSpacer(dp = 16.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    categories.takeLast(4).forEachIndexed { index, category ->
                        CategoryItem(
                            data = category,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                // todo 카테고리 화면으로 이동
                            }
                        )
                        if (index != 3) {
                            HorizontalSpacer(dp = 28.dp)
                        }
                    }
                }
                }


            // --- 지역, 정렬 ---
                item {
                    VerticalSpacer(dp = 12.dp)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50f))
                                .clickable {
                                    // todo 지역화면으로 이동
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_location_red_22_dp),
                                contentDescription = null
                            )
                            HorizontalSpacer(dp = 2.dp)
                            Text(text = state.location, style = RomTextStyle.text16, color = Gray1)
                        }

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50f))
                                .clickable {
                                    showSortDialog = true
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = state.sortType.display,
                                style = RomTextStyle.text13,
                                color = Gray2
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_drop_down_arrow_22_dp),
                                contentDescription = null
                            )
                        }

                    }
                }


                // --- 피드 ---
                val pairs = state.feeds.chunked(2)
                items(
                    pairs.size,
                    key = { pairs[it].first().id },
                    contentType = { pairs[it].first() }
                ) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        HomeFeedItem(
                            feed = pairs[index][0],
                            onClickLike = { id ->
                                viewModel.like(id)
                            },
                            onClickFeed = { feed ->
                                onNavEvent("${TradInDestinations.DETAILS_ROUTE}/{${FEED_ID}}")
                            },
                            modifier = Modifier.weight(1f)
                        )

                        HorizontalSpacer(dp = 12.dp)

                        if (pairs[index].size > 1) {
                            HomeFeedItem(
                                feed = pairs[index][1],
                                onClickLike = { id ->
                                    viewModel.like(id)
                                },
                                onClickFeed = { feed ->
                                    onNavEvent("${TradInDestinations.DETAILS_ROUTE}/{${FEED_ID}}")
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }


            // --- toolbar ---
            Column(
                modifier = Modifier
                    .offset {
                        IntOffset(0, toolbarOffsetPx.value.roundToInt())
                    }
                    .fillMaxWidth()
                    .background(color = White)
                    .onGloballyPositioned {
                        toolbarHeightPx.value = it.size.height.toFloat()
                    },
            ) {
                VerticalSpacer(dp = 14.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        style = RomTextStyle.text17,
                        fontWeight = FontWeight(500),
                        modifier = Modifier.padding(top = 6.dp)
                    )

                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null
                        )
                        HorizontalSpacer(dp = 6.dp)
                        Image(
                            painter = painterResource(id = R.drawable.ic_notification),
                            contentDescription = null
                        )
                    }
                }
                VerticalSpacer(dp = 9.dp)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Gray7)
                )
            }

        // --- animation ---
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


        // --- loading ---
        if (loading) {
            RomCircularProgressIndicator()
        }
    }
}

@Composable
fun HomeSortDialog(
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
fun HomeFeedItem(
    feed: Feed,
    onClickLike: (id: Int) -> Unit,
    onClickFeed: (Feed) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                onClickFeed(feed)
            }
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(feed.imgUrl).crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .border(borderStrokeBlack2, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
            )

            Image(painter = painterResource(
                id = if (feed.isLiked) R.drawable.ic_like_on_36_dp else R.drawable.ic_like_off_36_dp
            ),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset((-4).dp, (-4).dp)
                    .clip(RoundedCornerShape(50f))
                    .clickable { onClickLike(feed.id) })

        }

        VerticalSpacer(dp = 12.dp)

        Text(text = feed.title, style = RomTextStyle.text14, color = Gray0)
        VerticalSpacer(dp = 4.dp)

        Text(
            text = "${feed.location} · ${feed.nickname}",
            style = RomTextStyle.text12,
            color = Gray2,
            overflow = TextOverflow.Ellipsis
        )
        VerticalSpacer(dp = 10.dp)

        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier
                    .border(BorderStroke(1.dp, Gray4), shape = RoundedCornerShape(50f))
                    .clip(RoundedCornerShape(50f))
                    .padding(vertical = 2.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_like_off_21_dp),
                    contentDescription = null
                )
                Text(
                    text = if (feed.likeCount > 999) "999+" else "${feed.likeCount}",
                    style = RomTextStyle.text12,
                    color = Gray1
                )
            }
            HorizontalSpacer(dp = 8.dp)

            if (feed.status != FeedStatus.NONE) {
                Text(
                    text = feed.status.display,
                    style = RomTextStyle.text12,
                    color = feed.status.textColor,
                    fontWeight = FontWeight(700),
                    modifier = Modifier
                        .background(
                            color = feed.status.backgroundColor,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                )
            }

        }
        VerticalSpacer(dp = 8.dp)


        Text(
            text = feed.createdAt,
            style = RomTextStyle.text12,
            fontWeight = FontWeight(400),
            color = Gray3
        )
        VerticalSpacer(dp = 10.dp)
    }

}


@Composable
fun CategoryItem(
    data: Category,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
                painter = painterResource(id = data.iconResId),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
            )
        VerticalSpacer(dp = 10.dp)
        Text(
            text = data.display,
            style = RomTextStyle.text13,
            color = Gray0,
            fontWeight = FontWeight(500)
        )
    }
}



