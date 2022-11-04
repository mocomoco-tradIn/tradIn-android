package com.mocomoco.tradin.presentation.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocomoco.tradin.R
import com.mocomoco.tradin.model.Category
import com.mocomoco.tradin.presentation.TradInDestinations.LOCATION_ROUTE
import com.mocomoco.tradin.presentation.common.*
import com.mocomoco.tradin.presentation.signup.components.InfoInputWithDescItem
import com.mocomoco.tradin.presentation.signup.components.InfoInputWithDescTextFieldItem
import com.mocomoco.tradin.presentation.theme.Gray0
import com.mocomoco.tradin.presentation.theme.Gray2
import com.mocomoco.tradin.presentation.theme.RomTextStyle


@Composable
fun AddScreen(
    viewModel: AddViewModel = hiltViewModel(),
    navEvent: (String) -> Unit,
) {

    val state = viewModel.state.collectAsState().value

    var itemName by rememberSaveable {
        mutableStateOf("")
    }

    var itemDesc by rememberSaveable {
        mutableStateOf("")
    }


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
                .verticalScroll(rememberScrollState())
        ) {

            AddScreenSectionImage(
                state,
                onClickGallery = {
                    // todo 갤러리 런치
                }
            )

            VerticalSpacer(dp = 22.dp)

            AddScreenSectionCategories(
                state, onClick = { category ->
                    viewModel.onClickCategory(category)
                }
            )

            VerticalSpacer(dp = 30.dp)

            InfoInputWithDescTextFieldItem(
                title = "상품명",
                input = itemName,
                onInputChange = { itemName = it },
                placeholderText = "최대 32자까지 입력가능",
                editable = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            VerticalSpacer(dp = 16.dp)


            InfoInputWithDescTextFieldItem(
                title = "상세내용",
                input = itemDesc,
                onInputChange = { itemDesc = it },
                placeholderText = "상품 사용기간, 원가를 포함해 적으면 좋아요",
                editable = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )

            VerticalSpacer(dp = 16.dp)

            InfoInputWithDescItem(
                title = "희망 거래 방식",
                descText = "기타의 경우 내용에 구체적인 내용을 기입해주세요",
                descTextColor = Gray2
            ) {
                VerticalSpacer(dp = 8.dp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    state.tradeMethodState.forEachIndexed { index, value ->
                        ToggleButton(
                            modifier = Modifier.weight(1f),
                            text = value.tradeMethod.display,
                            enable = value.selected
                        ) {
                            viewModel.onClickTradeMethod(value.tradeMethod)
                        }

                        if (index != state.tradeMethodState.size) {
                            HorizontalSpacer(dp = 8.dp)
                        }
                    }
                }
            }

            VerticalSpacer(dp = 16.dp)

            InfoInputWithDescItem(
                title = "희망 거래지역",
                descTextColor = Gray2
            ) {
                DefaultTextFields(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navEvent(LOCATION_ROUTE)
                        },
                    value = state.location.display,
                    onValueChange = {
                        // do nothing
                    },
                    placeholderText = "희망 거래 지역을 설정해요",
                    enabled = false,
                    trailingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun AddScreenSectionImage(
    state: AddState,
    modifier: Modifier = Modifier,
    onClickGallery: () -> Unit,
) {
    Column(modifier = modifier) {
        LazyRow {
            if (state.showImageNone) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.ic_none_image),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onClickGallery()
                        }
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
                            onClickGallery()
                        }
                    )
                }
            }
        }
        VerticalSpacer(dp = 11.dp)
        Text(text = "사진은 최대 5장까지 첨부 가능합니다", style = RomTextStyle.text12, color = Gray2)
    }
}

@Composable
fun AddScreenSectionCategories(
    state: AddState,
    modifier: Modifier = Modifier,
    onClick: (Category) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "카테고리", style = RomTextStyle.text14, color = Gray0)

        VerticalSpacer(dp = 8.dp)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            state.categoryState.take(4).forEach {
                CategoryItem(
                    data = it.category,
                    selected = it.selected,
                    onClick = {
                        onClick(it.category)
                    }
                )
            }
        }
        VerticalSpacer(dp = 16.dp)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            state.categoryState.takeLast(4).forEach {
                CategoryItem(
                    data = it.category,
                    selected = it.selected,
                    onClick = {
                        onClick(it.category)
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    data: Category,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Image(painter = painterResource(id = data.iconResId), contentDescription = null)
            Image(
                painter = painterResource(id = if (selected) R.drawable.ic_checkbox_fill_on else R.drawable.ic_checkbox_fill_off),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 5.dp, y = 0.dp)
                    .clickable { onClick }
            )
        }

        VerticalSpacer(dp = 8.dp)

        Text(text = data.display, style = RomTextStyle.text14, color = Gray0)
    }
}

