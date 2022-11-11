package com.mocomoco.tradin.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocomoco.tradin.presentation.TradInDestinations
import com.mocomoco.tradin.presentation.TradInDestinations.BACK
import com.mocomoco.tradin.presentation.common.*
import com.mocomoco.tradin.presentation.theme.*
import com.mocomoco.tradin.util.ext.showToast
import kotlinx.coroutines.launch


// todo 테스트
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onNavEvent: (String) -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val loading = viewModel.loading.collectAsState().value

    val keyboard = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.clear()
        keyboard?.show()
        launch {
            viewModel.toastMessage.collect {
                it.showToast(context)
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        SearchToolbar(
            viewModel, state, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 8.dp, end = 16.dp)
        ) {
            onNavEvent(BACK)
        }

        VerticalSpacer(dp = 8.dp)

        LazyColumn {
            if (state.completeSearch) {
                items(state.relatedTexts.size) { index ->
                    RelatedKeyword(
                        text = state.relatedTexts[index],
                        selectedText = state.searchText,
                        onClick = {
                            viewModel.search(it)
                        }
                    )
                }
            } else {
                val pairs = state.result.chunked(2)
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
                                // do nothing
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
                                    // do nothing
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
    }
    // --- loading ---
    if (loading) {
        RomCircularProgressIndicator()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchToolbar(
    viewModel: SearchViewModel,
    state: SearchState,
    modifier: Modifier,
    onBack: () -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = com.mocomoco.tradin.R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .clickable {
                        keyboard?.hide()
                        onBack()
                    }
            )

            HorizontalSpacer(dp = 12.dp)

            TradInTextField(
                value = state.searchText,
                onValueChange = { viewModel.onChangeSearchText(it) },
                contentPaddingValues = PaddingValues(vertical = 11.dp, horizontal = 10.dp),
                placeholder = {
                    Text(text = "원하는 상품을 입력해봐요", style = RomTextStyle.text15, color = Gray3)
                },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = com.mocomoco.tradin.R.drawable.ic_search),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    Image(
                        painter = painterResource(id = com.mocomoco.tradin.R.drawable.ic_search_clear_16_dp),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .clickable {
                                viewModel.clear()
                                keyboard?.hide()
                            }
                    )
                },
                textStyle = RomTextStyle.text15.copy(textAlign = TextAlign.Start),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions {
                    viewModel.search(state.searchText)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Gray0,
                    backgroundColor = Transparent,
                    cursorColor = Gray0,
                    focusedIndicatorColor = Gray0,
                    unfocusedIndicatorColor = Gray3
                ),
                singleLine = true
            )
        }
    }
}

@Composable
fun RelatedKeyword(text: String, selectedText: String, onClick: (String) -> Unit) {
    val annotated = buildAnnotatedString {
        text.findAnyOf(listOf(selectedText), 0)?.let {
            withStyle(SpanStyle(Blue1)) {
                it.second
            }
            withStyle(SpanStyle(Gray0)) {
                text.substring(0, it.first) + text.substring(it.first, text.lastIndex)
            }
        } ?: run {
            withStyle(SpanStyle(Gray0)) {
                text
            }
        }
    }


    Text(
        text = annotated,
        style = RomTextStyle.text15,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .clickable {
                onClick(selectedText)
            }
    )
}