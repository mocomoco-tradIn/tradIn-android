package com.mocomoco.tradin.presentation.user_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mocomoco.tradin.presentation.TradInDestinations.BACK
import com.mocomoco.tradin.presentation.common.*
import com.mocomoco.tradin.presentation.theme.*
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        DefaultToolbar(
            modifier = Modifier.fillMaxWidth(),
            showBack = true,
            onClickBack = {
                onNavEvent(BACK)
            },
            title = state.userName
        )

        UserProfileSection(state)

        TabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    // modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 2.dp,
                    color = Blue1
                )
            }
        ) {
            // Add tabs for all of our pages
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            count = pages.size,
            state = pagerState,
        ) { page ->

            if (page == 0) {
                Box(modifier = Modifier.fillMaxSize().background(Color.Green))
            } else {
                Box(modifier = Modifier.fillMaxSize().background(Color.Red))
            }

        }


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
    Column(modifier = Modifier.fillMaxWidth()) {
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