package com.mocomoco.tradin.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.R
import com.mocomoco.tradin.presentation.theme.*

@Composable
fun DefaultToolbar(
    modifier: Modifier = Modifier,
    showBack: Boolean = false,
    onClickBack: () -> Unit = {},
    title: String = "",
    rightButtons: List<Pair<Painter, () -> Unit>> = listOf(),
    backgroundColor: Color = White,
    showBottomLine: Boolean = true
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {

            Image(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .alpha(if (showBack) 1f else 0f)
                        .clickable {
                            if (showBack) {
                                onClickBack()
                            }
                        },
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null
            )


            Text(
                modifier = Modifier.align(Alignment.Center),
                text = title,
                style = RomTextStyle.text16,
                color = Gray0,
                fontWeight = FontWeight(500)
            )

            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                rightButtons.forEach { pair ->
                    Image(
                        painter = pair.first,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 16.dp, top = 4.dp, bottom = 4.dp)
                            .clickable { pair.second.invoke() }
                    )
                }
            }
        }
        if (showBottomLine) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp), color = Gray7
            )
        }
    }
}


@Composable
fun StartTitleToolbar(
    modifier: Modifier = Modifier,
    showBack: Boolean = false,
    backIconColor: Color = Black,
    onClickBack: () -> Unit = {},
    title: String = "",
    rightButtons: List<Pair<Painter, () -> Unit>> = listOf(),
    backgroundColor: Color = White,
    showBottomLine: Boolean = true
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {

                if (showBack) {
                    Icon(
                        modifier = modifier
                            .clip(RoundedCornerShape(50))
                            .clickable {
                                onClickBack()
                            },
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        tint = backIconColor
                    )
                }

                HorizontalSpacer(dp = 8.dp)

                Text(
                    text = title,
                    style = RomTextStyle.text16,
                    color = Gray0,
                    fontWeight = FontWeight(500),
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row {
                rightButtons.forEach { pair ->
                    Image(
                        painter = pair.first,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 16.dp, top = 4.dp, bottom = 4.dp)
                            .clickable { pair.second.invoke() }
                    )
                }
            }
        }

        if (showBottomLine){
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp), color = Gray7
            )
        }
    }
}

