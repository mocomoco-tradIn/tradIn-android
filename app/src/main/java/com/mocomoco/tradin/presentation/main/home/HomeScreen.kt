package com.mocomoco.tradin.presentation.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocomoco.tradin.R
import com.mocomoco.tradin.common.Constants.LikeAnimDuration
import com.mocomoco.tradin.model.Category
import com.mocomoco.tradin.model.Feed
import com.mocomoco.tradin.presentation.TradInDestinations
import com.mocomoco.tradin.presentation.TradInDestinations.CATEGORY_ROUTE
import com.mocomoco.tradin.presentation.common.*
import com.mocomoco.tradin.presentation.theme.*
import com.mocomoco.tradin.util.ext.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

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
                    delay(LikeAnimDuration)
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

    var showSortDialog by remember {
        mutableStateOf(false)
    }

    if (showSortDialog) {
        SortDialog(
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
                                onNavEvent(
                                    "$CATEGORY_ROUTE/${category.code}/${
                                        category.display.replace(
                                            "/",
                                            ""
                                        )
                                    }"
                                )
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
                                category.display.replace("/", "")
                                onNavEvent(
                                    "$CATEGORY_ROUTE/${category.code}/${
                                        category.display.replace(
                                            "/",
                                            ""
                                        )
                                    }"
                                )
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
                        FeedItem(
                            feed = pairs[index][0],
                            onClickLike = { id ->
                                viewModel.like(id)
                            },
                            onClickFeed = { feed ->
                                onNavEvent("${TradInDestinations.DETAILS_ROUTE}/${feed.id}")
                            },
                            modifier = Modifier.weight(1f)
                        )

                        HorizontalSpacer(dp = 12.dp)

                        if (pairs[index].size > 1) {
                            FeedItem(
                                feed = pairs[index][1],
                                onClickLike = { id ->
                                    viewModel.like(id)
                                },
                                onClickFeed = { feed ->
                                    onNavEvent("${TradInDestinations.DETAILS_ROUTE}/${feed.id}")
                                },
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
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
        LikeAnimation(showLikeAnimation = showLikeAnimation)


        // --- loading ---
        if (loading) {
            RomCircularProgressIndicator()
        }
    }
}







