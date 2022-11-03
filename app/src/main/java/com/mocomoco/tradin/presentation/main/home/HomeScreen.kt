package com.mocomoco.tradin.presentation.main.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mocomoco.tradin.common.Logger
import kotlin.math.roundToInt



@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenWidthPx = with(LocalDensity.current) { screenWidth.roundToPx().toFloat() }


    val toolbarHeight = 100.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val offsetHeightPx = remember { mutableStateOf(0f) }


    Column(modifier = Modifier.fillMaxSize().pointerInput(Unit) {
        detectVerticalDragGestures { change, dragAmount ->

        }
    }) {

        HorizontalPager(modifier = Modifier, count = 5, key = { it }) {
            LazyColumn() {
                items(100) { index ->
                    Text(
                        "I'm item $index",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Screena() {

    var offset by remember {
        mutableStateOf(0f)
    }

    val state = rememberLazyListState()
    val interactionSource: MutableInteractionSource =
        remember { MutableInteractionSource() }
    val isDragging = interactionSource.collectIsDraggedAsState()
    offset += state.firstVisibleItemScrollOffset

    state.layoutInfo.afterContentPadding

    val deri = derivedStateOf {
        state.layoutInfo.visibleItemsInfo.getOrNull(0)?.offset
    }

    Logger.log("$offset ${deri.value}")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = state
    ) {

        item {
            Text(text = "asdf", fontSize = 25.sp)
        }

        items(105) {
            Text(text = "$it", fontSize = 15.sp)
        }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Screenb() {
    val toolbarHeight = 100.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }

    val state = rememberLazyListState()

    val pagerState = rememberPagerState()


    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = (toolbarOffsetHeightPx.value + delta).coerceAtMost(0f)
                toolbarOffsetHeightPx.value = newOffset
                return Offset.Zero
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {

        TopAppBar(
            modifier = Modifier
                .height(toolbarHeight)
                .offset {
                    IntOffset(
                        x = 0,
                        y = toolbarOffsetHeightPx.value
                            .coerceIn(-toolbarHeightPx, 0f)
                            .roundToInt()
                    )
                },
            title = { Text("toolbar offset is ${toolbarOffsetHeightPx.value}") }
        )


        HorizontalPager(modifier = Modifier, count = 5, key = { it }) {
            LazyColumn(
                state = state
            ) {
                items(100) { index ->
                    Text(
                        "I'm item $index",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }


    }
}


@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun Hi() {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenWidthPx = with(LocalDensity.current) { screenWidth.roundToPx().toFloat() }


    val toolbarHeight = 100.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val offsetHeightPx = remember { mutableStateOf(0f) }

    val state = rememberLazyListState()

    val pagerState = rememberPagerState()


    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y

                val newOffset = (offsetHeightPx.value + delta).coerceAtMost(0f)

                val isSame =  (newOffset == offsetHeightPx.value)

                Logger.log("offsetHeightPx(${offsetHeightPx.value}) + delta($delta)")  // 768
                Logger.logE(" = newOffset($newOffset)")

                offsetHeightPx.value = newOffset


                Logger.log(
                    "${
                        offsetHeightPx.value
                            .coerceIn(-screenWidthPx, 0f)
                            .roundToInt()
                    }"
                )


                return if (delta < 0 && (screenWidthPx + offsetHeightPx.value.coerceIn(-screenWidthPx, 0f)) != 0f
                ) {
                    Offset.Zero

                } else {
                    Offset.Zero
                }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {

        HorizontalPager(count = 5, modifier = Modifier.offset {
            IntOffset(
                x = 0,
                y = offsetHeightPx.value
                    .coerceIn(-screenWidthPx, 0f)
                    .roundToInt()
            )
        }) { page ->
            Text(
                text = "$page 입니다",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenWidth)
                    .background(androidx.compose.ui.graphics.Color(0x22000000))
            ) // todo replace with viewPager
        }

//        Spacer(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(44.dp)
//                .padding(top = screenWidth)
//                .background(Color.Yellow)
//                .offset {
//                    IntOffset(
//                        x = 0,
//                        y = offsetHeightPx.value
//                            .coerceIn(-screenWidthPx, 0f)
//                            .roundToInt()
//                    )
//                }
//        ) // todo replace with tabLayout

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = with(LocalDensity.current) {
                (screenWidthPx + offsetHeightPx.value.coerceIn(-screenWidthPx, 0f)).toDp()
            })) {
//            Spacer(
//                modifier = Modifier
//                    .height(with(LocalDensity.current) {
//                        (screenWidthPx + offsetHeightPx.value.coerceIn(-screenWidthPx, 0f)).toDp()
//                    })
//                    .background(Color.Green)
//            )

            HorizontalPager(count = 5) { page ->
                LazyColumn() {
                    items(100) { index ->
                        Column {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(color = androidx.compose.ui.graphics.Color.Black)
                            )
                            Text(
                                "I'm item $index", modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                }

            }


        }
    }
//    Spacer(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(44.dp)
//            .background(androidx.compose.ui.graphics.Color.Green)
//    ) // todo replace with toolbar
}
