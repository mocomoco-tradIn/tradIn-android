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
import com.mocomoco.tradin.presentation.signup.agree.PolicyAgreementSubScreen
import com.mocomoco.tradin.presentation.signup.complete.CompleteSignupSubScreen
import com.mocomoco.tradin.presentation.signup.login_info.LoginInfoSubScreen
import com.mocomoco.tradin.presentation.signup.phon_auth.TelAuthSubScreen
import com.mocomoco.tradin.presentation.signup.user_info.UserInfoSubScreen

const val SIGNUP_PHASE_NUM = 5

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignupScreen(
    onClickBack: () -> Unit,
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
                        state = state.telAuthState,
                        onClickNext = { phone, _ ->
                            keyboardController?.hide()
                            viewModel.onCompletePhoneAuth(phone)
                        },
                        onClickAuthWithTel = { phone ->
                            keyboardController?.hide()
                            viewModel.postTelAuth(phone)
                        },
                        onClickAuthCoincide = { authNum ->
                            keyboardController?.hide()
                            viewModel.putAuthCoincide(authNum)
                        },
                        onTimeout = {
                            viewModel.onTimeout()
                        }
                    )
                }
                !state.completeLoginInfo -> {
                    LoginInfoSubScreen()
                }
                !state.completeUserInfo -> {
                    UserInfoSubScreen()
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










