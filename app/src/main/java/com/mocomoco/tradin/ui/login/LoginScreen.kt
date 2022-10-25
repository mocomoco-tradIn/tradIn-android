package com.mocomoco.tradin.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.R
import com.mocomoco.tradin.ui.theme.*

@Composable
fun LoginScreen() {

    Column(modifier = Modifier.fillMaxSize()) {
        Icon(painter = painterResource(id = R.drawable.ic_logo_login), contentDescription = null)

        Box(
            modifier = Modifier
                .weight(weight = 1f)
                .background(Orange3)
        ) {


        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1f)
        ) {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                painter = painterResource(id = R.drawable.background_login_crop_circle),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 0.dp),
                    painter = painterResource(id = R.drawable.background_login_line),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp, 0.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {

                    DefaultTradInButton(
                        text = "시작하기",
                        backgroundColor = Blue1,
                        enable = true,
                        textColor = White
                    ) {
                        // todo
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(49.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(0.dp, 8.dp)
                                .clickable {
                                    // todo
                                },
                            textAlign = TextAlign.Center,
                            text = stringResource(id = R.string.login_sign_up),
                            style = TradInTypography.subtitle1,
                            color = Gray1
                        )

                        Divider(
                            color = Gray1, modifier = Modifier
                                .fillMaxHeight(0.5f)
                                .width(1.dp)
                        )


                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(0.dp, 8.dp)
                                .clickable {
                                    // todo
                                },
                            text = stringResource(id = R.string.login_find_pw),
                            textAlign = TextAlign.Center,
                            style = TradInTypography.subtitle1,
                            color = Gray1
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(59.dp)
                    )

                }
            }
        }
    }
}


@Composable
fun DefaultTradInButton(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color,
    enable: Boolean,
    textColor: Color,
    onClick: () -> Unit
) {
    TextButton(
        onClick = {
            if (enable) {
                onClick.invoke()
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = Shapes.large)
            .border(defaultBorderStroke, Shapes.large)
    ) {
        Text(
            text = text,
            style = TradInTypography.h3,
            color = textColor,
        )
    }
}

@Preview(name = "asdf")
@Composable
fun ASd() {
    LoginScreen()
}


fun Modifier.baselinePadding(
    firstBaselineToTop: Dp,
    lastBaselineToBottom: Dp
) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]

    check(placeable[LastBaseline] != AlignmentLine.Unspecified)
    val lastBaseline = placeable[LastBaseline]

    val lastBaselineToBottomHeight = placeable.height - lastBaseline

    val lastBaselineToBottomDelta = lastBaselineToBottom.roundToPx() - lastBaselineToBottomHeight

    val totalHeight = placeable.height +
            (firstBaselineToTop.roundToPx() - firstBaseline)

    val placeableY = totalHeight - placeable.height
    layout(placeable.width, totalHeight + lastBaselineToBottomDelta) {
        placeable.placeRelative(0, placeableY)
    }
}
