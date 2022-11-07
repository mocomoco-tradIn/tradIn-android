package com.mocomoco.tradin.presentation.main.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mocomoco.tradin.R
import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.model.Feed
import com.mocomoco.tradin.model.FeedStatus
import com.mocomoco.tradin.presentation.common.HorizontalLineSpacer
import com.mocomoco.tradin.presentation.common.HorizontalSpacer
import com.mocomoco.tradin.presentation.common.VerticalSpacer
import com.mocomoco.tradin.presentation.theme.*


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    LaunchedEffect(true) {
        viewModel.load(lastId = 0)
    }

    val lazyColumnState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(16.dp)),
        state = lazyColumnState
    ) {
        items(state.value.feeds.size) { index ->
            HomeFeedItem(feed = state.value.feeds[index],
                onClickLike = { id ->
                    viewModel.like(id)
                },
                onClickFeed = { feed ->
                    // todo goToDetails
                }
            )
        }
    }
}


@Composable
fun HomeFeedItem(feed: Feed, onClickLike: (id: Int) -> Unit, onClickFeed: (Feed) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                onClickFeed(feed)
            }
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(feed.imgUrl).crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
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

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
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
                        .background(color = feed.status.backgroundColor, shape = RoundedCornerShape(5.dp))
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
