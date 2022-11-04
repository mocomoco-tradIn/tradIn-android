package com.mocomoco.tradin.presentation.main.inventory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.mocomoco.tradin.presentation.TradInDestinations.ADD_ROUTE
import com.mocomoco.tradin.presentation.common.DefaultRomButton
import com.mocomoco.tradin.presentation.common.VerticalSpacer
import com.mocomoco.tradin.presentation.theme.*

@Composable
fun InventoryScreen(
    viewModel: InventoryViewModel = hiltViewModel(),
    onNavEvent: (String) -> Unit = {},
) {
    val state = viewModel.state.collectAsState().value
    Column(modifier = Modifier.fillMaxSize()) {
        InventoryScreenHeader()
        InventoryScreenBody(state, onNavEvent)
    }
}

@Composable
fun InventoryScreenHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 14.dp)
    ) {
        Text(text = "피드에 올리기", style = RomTextStyle.text17, color = Black)
        VerticalSpacer(dp = 6.dp)
        Text(text = "내 상품 서랍 속 하나를 선택해 피드에 게시해봐요", style = RomTextStyle.text13, color = Gray2)
    }
}

@Composable
fun InventoryScreenBody(state: InventoryState, onNavEvent: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {

        Spacer(
            modifier = Modifier
                .offset(x = (-16).dp, y = 0.dp)
                .fillMaxSize()
                .background(color = Orange2)
                .border(
                    borderStrokeBlack2
                )
        )

        Column(
            modifier = Modifier
                .offset(x = 0.dp, y = 6.dp)
                .fillMaxSize()
                .background(color = Orange3)
        ) {
            if (state.showNone) {
                InventoryScreenItemsNone(onNavEvent)
            } else {
                InventoryScreenItems(
                    onClickInventoryItem = state.onClickInventoryItem,
                    onClickCheckBox = state.onClickCheckBox,
                    onNavEvent = onNavEvent,
                    items = state.items,
                    showAddFeedButton = state.showAddFeedButton
                )
            }
        }
    }

}

@Composable
fun InventoryScreenItems(
    onClickInventoryItem: () -> Unit,
    onClickCheckBox: (index: Int) -> Unit,
    onNavEvent: (String) -> Unit,
    items: List<InventoryItem>,
    showAddFeedButton: Boolean
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            InventoryScreenItemsHeader(onNavEvent)

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(12.dp, 0.dp)
            ) {

                items.forEachIndexed { index, value ->
                    item {
                        InventoryItem(
                            item = value,
                            onClickCheckBox = onClickCheckBox,
                            onClickItem = onClickInventoryItem
                        )
                    }
                }
            }
        }

        if (showAddFeedButton) {
            DefaultRomButton(
                text = "다음",
                enable = true,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(17.dp, 0.dp)
            ) {
                onNavEvent(ADD_ROUTE + "/false")
            }
        }
    }
}

@Composable
fun InventoryScreenItemsNone(
    onNavEvent: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        InventoryScreenItemsHeader(onNavEvent)

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = com.mocomoco.tradin.R.drawable.ic_inventory_empty),
                    contentDescription = null
                )
                VerticalSpacer(dp = 16.dp)
                Text(text = "아직 서랍에 상품이 없어요", style = RomTextStyle.text13, color = Gray0)
                VerticalSpacer(dp = 2.dp)
                Row {
                    Image(
                        painter = painterResource(id = com.mocomoco.tradin.R.drawable.ic_add_24_dp),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(text = "버튼을 눌러 새로운 상품을 등록해봐요", style = RomTextStyle.text13, color = Gray0)
                }
            }
        }
    }
}

@Composable
fun InventoryScreenItemsHeader(
    onNavEvent: (String) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(color = Gray0)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 19.dp, end = 15.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "내 상품 서랍", style = RomTextStyle.text16, color = Gray0)
            Image(
                painter = painterResource(id = com.mocomoco.tradin.R.drawable.ic_add_24_dp),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onNavEvent(ADD_ROUTE + "/true")
                }
            )
        }
    }
}

@Composable
fun InventoryItem(
    item: InventoryItem,
    onClickItem: () -> Unit,
    onClickCheckBox: (index: Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 3.dp, vertical = 7.dp
            )
            .border(borderStrokeBlack2, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onClickItem()
            }
            .padding(
                start = 8.dp, top = 8.dp, end = 8.dp, bottom = 14.dp
            )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(borderStrokeBlack2, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imgUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .border(borderStrokeBlack2, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
            )
            Image(
                painter = painterResource(
                    if (item.checked) com.mocomoco.tradin.R.drawable.ic_checkbox_fill_on else com.mocomoco.tradin.R.drawable.ic_checkbox_fill_off
                ),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-4).dp, y = (-4).dp)
                    .clickable { onClickCheckBox(item.index) }
            )
        }

        VerticalSpacer(dp = 10.dp)

        Text(
            text = item.title,
            style = RomTextStyle.text13,
            color = Gray1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight(500)
        )

    }


}