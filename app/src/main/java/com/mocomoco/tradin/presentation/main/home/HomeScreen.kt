package com.mocomoco.tradin.presentation.main.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mocomoco.tradin.R
import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.model.Category
import com.mocomoco.tradin.model.Feed
import com.mocomoco.tradin.model.FeedStatus
import com.mocomoco.tradin.presentation.TradInDestinations
import com.mocomoco.tradin.presentation.common.HorizontalSpacer
import com.mocomoco.tradin.presentation.common.VerticalSpacer
import com.mocomoco.tradin.presentation.nav.Arguments.FEED_ID
import com.mocomoco.tradin.presentation.theme.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavEvent: (String) -> Unit
) {

    val state = viewModel.state.collectAsState().value

    LaunchedEffect(true) {
        viewModel.load(lastId = 0)
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

    val lazyColumnState = rememberLazyListState()


    // --- 카테고리 ---


    // --- 지역, 정렬 ---

    // --- 리스트 ---
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(horizontal = 16.dp)),
        state = lazyColumnState
    ) {

        item {
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
                    .padding(horizontal = 16.dp),
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

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
                            // todo 바텀시트 띄우기
                        }, verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = state.sortType.display, style = RomTextStyle.text13, color = Gray2)
                    Image(
                        painter = painterResource(id = R.drawable.ic_drop_down_arrow_22_dp),
                        contentDescription = null
                    )
                }

            }
        }

        val pairs = state.feeds.chunked(2)
        items(
            pairs.size,
            key = { pairs[it].first().id },
            contentType = { pairs[it].first() }
        ) { index ->
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
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
                Logger.log("${feed.status}")
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



