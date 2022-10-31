package com.mocomoco.tradin.presentation.signup

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocomoco.tradin.R
import com.mocomoco.tradin.presentation.common.DefaultToolbar
import com.mocomoco.tradin.presentation.common.RomCircularProgressIndicator
import com.mocomoco.tradin.presentation.common.SignupProgressBar
import com.mocomoco.tradin.presentation.common.VerticalSpacer
import com.mocomoco.tradin.presentation.signup.subscreens.*

const val SIGNUP_PHASE_NUM = 5

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignupScreen(
    onClickBack: () -> Unit,
    onNavEvent: (String) -> Unit,
    viewModel: SignupViewModel = hiltViewModel()
) {

    val loading = viewModel.loading.collectAsState().value

    val state = viewModel.state.collectAsState().value

    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            DefaultToolbar(
                showBack = true,
                onClickBack = {
                    onClickBack()
                },
                title = stringResource(id = R.string.common_signup)
            )

            VerticalSpacer(dp = 16.dp)

            SignupProgressBar(
                total = SIGNUP_PHASE_NUM,
                current = when {
                    !state.completeAgree -> {
                        0
                    }
                    !state.completePhoneAuth -> {
                        1
                    }
                    !state.completeLoginInfo -> {
                        2
                    }
                    !state.completeUserInfo -> {
                        3
                    }
                    else -> {
                        4
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
            )

            VerticalSpacer(dp = 18.dp)

            when {
                !state.completeAgree -> {
                    PolicyAgreementSubScreen() {
                        viewModel.onCompleteAgreement()
                    }
                }
                !state.completePhoneAuth -> {
                    TelAuthSubScreen(
                        state = state.telAuthState, onClickAuthWithTel = { phone ->
                            keyboardController?.hide()
                            viewModel.postTelAuth(phone)
                        }, onClickAuthCoincide = { authNum ->
                            keyboardController?.hide()
                            viewModel.putAuthCoincide(authNum)
                        }, onTimeout = {
                            viewModel.onTimeout()
                        }, onClickNext = { phone, _ ->
                            keyboardController?.hide()
                            viewModel.onCompletePhoneAuth(phone)
                        })
                }
                !state.completeLoginInfo -> {
                    LoginInfoSubScreen(
                        state = state.loginInfoState,
                        onClickCheckDuplicate = { email ->
                            keyboardController?.hide()
                            viewModel.postEmailDuplicate(email)
                        },
                        onClickNext = { email, pw ->
                            keyboardController?.hide()
                            viewModel.onCompleteLoginInfo(email, pw)
                        }
                    )
                }
                !state.completeUserInfo -> {
                    UserInfoSubScreen(
                        state = state.userInfoState,
                        onClickCheckNicknameDuplicate = { nickname ->
                            viewModel.postNicknameDuplicate(nickname)
                        },
                        onClickChip = { code, selected ->
                            viewModel.onSelectCategory(code, selected)
                        },
                        onClickLocation = {
                            // onNavEvent("") todo 지역설정 화면으로 이동
                        },
                        onClickNext = { nickname, locationCode, categoriesCode ->
                            viewModel.onCompleteUserInfo(
                                nickname,
                                locationCode,
                                categoriesCode.map { it.code }
                            )
                        }
                    )
                }
                else -> {
                    CompleteSignupSubScreen()
                }
            }
        }

        if (loading) {
            RomCircularProgressIndicator()
        }
    }
}










