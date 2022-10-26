package com.mocomoco.tradin.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.R
import com.mocomoco.tradin.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen() {

    var emailText by remember {
        mutableStateOf("")
    }

    var passwordText by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .weight(weight = 1f)
                .padding(16.dp, 0.dp)
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_logo_login),
                contentDescription = null
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(14.dp)
            )

            Text(
                text = stringResource(id = R.string.login_app_subtitle),
                style = TradInTypography.subtitle1,
                color = Gray2
            )

            // todo 패딩 없는 텍스트필드 만들기 위해 Basic tkdyd
            val textFieldColors = TextFieldDefaults.textFieldColors(
                textColor = Gray0,
                focusedIndicatorColor = Gray0,
                unfocusedIndicatorColor = Gray3,
                cursorColor = Gray0,
            )
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .indicatorLine(
                        enabled = true,
                        isError = false,
                        interactionSource = remember { MutableInteractionSource() },
                        colors = textFieldColors
                    )
                    .background(Blue1),
                value = emailText,
                textStyle = TradInTypography.h5,
                onValueChange = { newText: String ->
                    emailText = newText
                },
                decorationBox = { innerTextField ->
                    TextFieldDefaults.TextFieldDecorationBox(
                        value = emailText,
                        innerTextField = {
                            Text(text = emailText)
                            innerTextField
                        },
                        placeholder = {
                            Text(
                                modifier = Modifier.background(Orange0),
                                text = stringResource(id = R.string.login_input_placeholder_id),
                                style = TradInTypography.h5,
                                color = Gray3
                            )
                        },
                        enabled = true,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = remember { MutableInteractionSource() },
                        colors = textFieldColors,
                        contentPadding = PaddingValues(0.dp)
                    )
                },
                singleLine = true
            )


            TextField(
                value = passwordText,
                onValueChange = { new -> passwordText = new },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Transparent),
                textStyle = TradInTypography.h5,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.login_input_placeholder_pw),
                        style = TradInTypography.h5,
                        color = Gray3
                    )
                },
                colors = textFieldColors
            )


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
                painter = painterResource(id = R.drawable.background_login_bottom),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp, 0.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    DefaultTradInButton(
                        text = "시작하기",
                        backgroundColor = Blue1,
                        enable = true,
                        textColor = White
                    ) {
                        // todo
                    }

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

//                    Spacer(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(59.dp)
//                    )

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
