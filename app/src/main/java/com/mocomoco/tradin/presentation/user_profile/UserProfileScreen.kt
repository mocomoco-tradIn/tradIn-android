package com.mocomoco.tradin.presentation.user_profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mocomoco.tradin.R
import com.mocomoco.tradin.common.Constants
import com.mocomoco.tradin.model.Trade
import com.mocomoco.tradin.presentation.TradInDestinations
import com.mocomoco.tradin.presentation.TradInDestinations.BACK
import com.mocomoco.tradin.presentation.common.*
import com.mocomoco.tradin.presentation.theme.*
import com.mocomoco.tradin.util.ext.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel = hiltViewModel(),
    id: Int,
    onNavEvent: (String) -> Unit
) {

    val state = viewModel.state.collectAsState().value

    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState()
    val pages = remember {
        listOf(
            "피드",
            "교환내역"
        )
    }

    val pairs by derivedStateOf {
        state.feeds.chunked(2)
    }

    val loading = viewModel.loading.collectAsState().value

    var showLikeAnimation by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.load(id)

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        DefaultToolbar(
            showBack = true,
            onClickBack = {
                onNavEvent(BACK)
            },
            title = state.userName.ifEmpty { "롬롬이 프로필" }
        )

        UserProfileSection(state)

        VerticalSpacer(dp = 22.dp)

        TabRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = White,
            contentColor = Blue1,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]))
            }
        ) {
            // Add tabs for all of our pages
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title, style = RomTextStyle.text13) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    selectedContentColor = Blue1,
                    unselectedContentColor = Gray0
                )
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            count = pages.size,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { page ->
            if (page == 0) {
                if (pairs.isNotEmpty()) {
                    LazyColumn(Modifier.fillMaxSize()) {
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
                } else {
                    EmptyScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.7f),
                        painter = painterResource(id = R.drawable.ic_empty),
                        text = "아직 피드에 내용이 없어요"
                    )
                }
            } else {
                if (state.trades.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(
                            state.trades.size,
                            key = { state.trades[it].tradeId },
                            contentType = { state.trades.firstOrNull() }
                        ) {
                            TradesItem(trade = state.trades[it])
                        }
                    }
                } else {
                    EmptyScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.7f),
                        painter = painterResource(id = R.drawable.ic_empty),
                        text = "아직 교환 내용이 없어요"
                    )
                }
            }
        }
    }

    // --- animation ---
    LikeAnimation(showLikeAnimation = showLikeAnimation)


    // --- loading ---
    if (loading) {
        RomCircularProgressIndicator()
    }
}

@Composable
fun UserProfileSection(state: UserProfileState) {
    val interestsText = buildAnnotatedString {
        withStyle(SpanStyle(Violet1)) {
            append(state.interests)
        }
        withStyle(SpanStyle(Gray2)) {
            append(" 에 관심 있어요")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfileImage52Dp(state.avatar)
            HorizontalSpacer(dp = 12.dp)
            Column(modifier = Modifier.weight(1f)) {
                Text(text = state.userName, style = RomTextStyle.text17, color = Gray0)
                VerticalSpacer(dp = 2.dp)
                Text(text = state.userRegion.code, style = RomTextStyle.text11, color = Gray3)
            }
        }
        VerticalSpacer(dp = 12.dp)
        Text(text = interestsText, style = RomTextStyle.text13)
    }
}

@Composable
fun TradesItem(trade: Trade) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        Text(text = trade.tradeDate, style = RomTextStyle.text11, color = Gray4)
        VerticalSpacer(dp = 6.dp)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row {
                Text(text = trade.otherNickname, style = RomTextStyle.text14, color = Gray0)
                HorizontalSpacer(dp = 8.dp)
                Text(text = "님과의 교환", style = RomTextStyle.text14, color = Gray1)
                HorizontalSpacer(dp = 10.dp)
                StatusTag(status = trade.feedStatus)
            }
            Row {
                Text(text = "상세보기", style = RomTextStyle.text13, color = Gray3)
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_right_18_dp),
                    contentDescription = null
                )
            }
        }
        VerticalSpacer(dp = 16.dp)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            BorderAsyncImage(url = trade.myProductImage, modifier = Modifier.size(90.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_exchange_arrow),
                contentDescription = null
            )
            BorderAsyncImage(url = trade.otherProductImage, modifier = Modifier.size(90.dp))
        }
        VerticalSpacer(dp = 16.dp)
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gray7)
        )
    }

}