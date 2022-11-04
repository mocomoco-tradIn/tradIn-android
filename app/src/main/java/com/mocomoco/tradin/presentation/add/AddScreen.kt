package com.mocomoco.tradin.presentation.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mocomoco.tradin.R
import com.mocomoco.tradin.model.Category
import com.mocomoco.tradin.presentation.common.DefaultToolbar
import com.mocomoco.tradin.presentation.common.HorizontalSpacer
import com.mocomoco.tradin.presentation.common.VerticalSpacer
import com.mocomoco.tradin.presentation.theme.Gray0
import com.mocomoco.tradin.presentation.theme.Gray2
import com.mocomoco.tradin.presentation.theme.RomTextStyle
import com.mocomoco.tradin.presentation.theme.borderStrokeBlack2


@Composable
fun AddScreen(
    viewModel: AddViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState().value

    Column {
        DefaultToolbar(
            showBack = true,
            title = state.title,
            rightButtons = listOf(
                painterResource(id = R.drawable.ic_btn_complete) to { viewModel.onClickComplete() }
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .scrollable(rememberScrollState(), Orientation.Vertical)
        ) {

            AddScreenSectionImage(state, onClick =  {
                // todo 갤러리 런치
            })

        }
    }
}


@Composable
fun AddScreenSectionImage(state: AddState, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(modifier = modifier) {
        LazyRow {
            if (state.showImageNone) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.ic_none_image),
                        contentDescription = null
                    )
                }
            } else {
                state.imageUrls.forEach { url ->
                    item {
                        DefaultAsyncImage(url)
                    }
                    item {
                        HorizontalSpacer(dp = 12.dp)
                    }
                }
                item {
                    DefaultImage(
                        painterResource(id = R.drawable.ic_add_24_dp),
                        isCrop = true,
                        onClick = {
                            onClick()
                            // todo 갤러리 런치
                        }
                    )
                }
            }
        }
        VerticalSpacer(dp = 11.dp)
        Text(text = "사진은 최대 5장까지 첨부 가능합니다", style = RomTextStyle.text12, color = Gray2)
        VerticalSpacer(dp = 22.dp)
        AddScreenSectionCategory(state)
    }
}

@Composable
fun AddScreenSectionCategory(state: AddState, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "카테고리", style = RomTextStyle.text14, color = Gray0)
        Row(modifier = Modifier.fillMaxWidth()) {

        }
        Row(modifier = Modifier.fillMaxWidth()) {

        }
    }
}

@Composable
fun CategoryItem(
    data: Category,
    modifier: Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {

    Column(modifier = modifier) {
        Box {
            Image(painter = painterResource(id = data.iconResId), contentDescription = null)
            Image(
                painter = painterResource(id = if (selected) R.drawable.ic_checkbox_fill_on else R.drawable.ic_checkbox_fill_off),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 2.dp, y = 0.dp)
                    .clickable { onClick }
            )
        }

        VerticalSpacer(dp = 8.dp)

        Text(text = data.display, style = RomTextStyle.text14, color = Gray0)

    }
}

@Composable
fun DefaultAsyncImage(url: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = modifier
            .border(borderStrokeBlack2, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun DefaultImage(
    painter: Painter,
    modifier: Modifier = Modifier,
    isCrop: Boolean = true,
    onClick: () -> Unit = {}
) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .border(borderStrokeBlack2, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        contentScale = if (isCrop) ContentScale.Crop else ContentScale.None,
    )
}