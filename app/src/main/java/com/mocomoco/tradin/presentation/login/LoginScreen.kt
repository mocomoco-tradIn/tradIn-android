package com.mocomoco.tradin.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocomoco.tradin.R
import com.mocomoco.tradin.base.BaseScreen
import com.mocomoco.tradin.presentation.common.DefaultRomButton
import com.mocomoco.tradin.presentation.common.DefaultTextFields
import com.mocomoco.tradin.presentation.common.VerticalSpacer
import com.mocomoco.tradin.presentation.theme.*
import com.mocomoco.tradin.util.ext.showToast

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onClickSignup: () -> Unit,
    onClickFindPassword: () -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.toastMessage.collect {
            it.showToast(context)
        }
    }

    BaseScreen<LoginViewModel>() {
        var emailText by rememberSaveable {
            mutableStateOf("")
        }

        var passwordText by rememberSaveable {
            mutableStateOf("")
        }

        var checkAutoLogin by rememberSaveable {
            mutableStateOf(false)
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .align(Alignment.BottomCenter)
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

                        DefaultRomButton(
                            text = "시작하기",
                            enable = true,
                        ) {
                            viewModel.login(emailText, passwordText)
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
                                    .padding(4.dp, 8.dp)
                                    .clickable {
                                        onClickSignup()
                                    },
                                textAlign = TextAlign.Center,
                                text = stringResource(id = R.string.common_signup),
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
                                        onClickFindPassword()
                                    },
                                text = stringResource(id = R.string.login_find_pw),
                                textAlign = TextAlign.Center,
                                style = TradInTypography.subtitle1,
                                color = Gray1
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .padding(16.dp, 0.dp)
                    .align(Alignment.TopCenter),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Text(text = "오고가는 교환의 재미", style = RomTextStyle.text14, color = Gray2)
                    VerticalSpacer(14.dp)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logo_login),
                        contentDescription = null
                    )
                }


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    DefaultTextFields(
                        value = emailText,
                        onValueChange = { new -> emailText = new },
                        placeholderText = stringResource(id = R.string.login_input_placeholder_id),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    VerticalSpacer(14.dp)

                    DefaultTextFields(
                        value = passwordText,
                        onValueChange = { new -> passwordText = new },
                        placeholderText = stringResource(
                            id = R.string.login_input_placeholder_pw
                        ), visualTransformation = { anotatedString ->
                            PasswordVisualTransformation().filter(anotatedString)
                        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )

                    VerticalSpacer(dp = 20.dp)

                    Text(
                        text = "아이디 또는 비밀번호가 올바르지 않습니다.",
                        style = RomTextStyle.text13,
                        color = if (state.invalidAccount) Pink1 else Transparent
                    )
                }
            }
        }
    }
}
