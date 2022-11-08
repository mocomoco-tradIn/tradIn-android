package com.mocomoco.tradin.presentation.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocomoco.tradin.R
import com.mocomoco.tradin.common.Constants
import com.mocomoco.tradin.presentation.TradInDestinations
import com.mocomoco.tradin.presentation.TradInDestinations.BACK
import com.mocomoco.tradin.presentation.common.*
import com.mocomoco.tradin.presentation.theme.Gray1
import com.mocomoco.tradin.presentation.theme.Gray2
import com.mocomoco.tradin.presentation.theme.RomTextStyle
import com.mocomoco.tradin.presentation.theme.White
import com.mocomoco.tradin.util.ext.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
    categoryId: Int,
    categoryDisplay: String,
    onNavEvent: (String) -> Unit
) {

    val state = viewModel.state.collectAsState().value

    val loading = viewModel.loading.collectAsState().value

    var showLikeAnimation by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.load(
            categoryId = categoryId,
        )

        launch {
            viewModel.toastMessage.collect {
                it.showToast(context)
            }
        }

        launch {
            viewModel.successLikeEvent.collect {
                showLikeAnimation = true
                delay(Constants.LikeAnimDuration)
                showLikeAnimation = false
            }
        }
    }

    Column {
        DefaultToolbar(
            showBack = true,
            onClickBack = {
                onNavEvent(BACK)
            },
            title = state.title.ifEmpty { categoryDisplay }
        )

        CategoryInfoSection(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 12.dp),
            onClickSort = {
                viewModel.showSortDialog(true)
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
        ) {
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


    }

    LikeAnimation(showLikeAnimation = showLikeAnimation)

    if (loading) {
        RomCircularProgressIndicator()
    }

    if (state.showSortDialog) {
        SortDialog(
            onDismiss = { viewModel.showSortDialog(false) },
            onClick = {
                viewModel.load(sortType = it, categoryId = categoryId)
            }
        )
    }
}

@Composable
fun CategoryInfoSection(
    state: CategoryState,
    modifier: Modifier = Modifier,
    onClickSort: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "${state.feedCount}개의 상품",
            style = RomTextStyle.text14,
            color = Gray1,
            fontWeight = FontWeight(500)
        )

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50f))
                .clickable {
                    onClickSort()
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