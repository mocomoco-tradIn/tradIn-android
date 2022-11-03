package com.mocomoco.tradin.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mocomoco.tradin.R
import com.mocomoco.tradin.presentation.common.*
import com.mocomoco.tradin.presentation.theme.Gray1
import com.mocomoco.tradin.presentation.theme.Gray2
import com.mocomoco.tradin.presentation.theme.TradInTypography

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onClickSignup: () -> Unit,
    onClickFindPassword: () -> Unit,
) {

//    val loading = viewModel.loading.collectAsState().value

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
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logo_login),
                        contentDescription = null
                    )

                    VerticalSpacer(14.dp)

                    Text(
                        text = stringResource(id = R.string.login_app_subtitle),
                        style = TradInTypography.subtitle1,
                        color = Gray2
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
                        ),
                        visualTransformation = { anotatedString ->
                            PasswordVisualTransformation().filter(anotatedString)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )

                    VerticalSpacer(dp = 20.dp)

                    CommonCheckBox(
                        checked = checkAutoLogin,
                        offIconId = R.drawable.ic_checkbox_off,
                        onIconId = R.drawable.ic_checkbox_on,
                        textId = R.string.login_auto,
                        onClick = { new ->
                            viewModel.setAutoLogin(new)
                            checkAutoLogin = new
                        }
                    )
                }


            }
        }


//        if (loading) {
//            RomCircularProgressIndicator()
//        }
    }
}
