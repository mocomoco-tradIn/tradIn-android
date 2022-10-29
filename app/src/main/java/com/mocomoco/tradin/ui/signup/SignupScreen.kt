package com.mocomoco.tradin.ui.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocomoco.tradin.R
import com.mocomoco.tradin.ui.common.*
import com.mocomoco.tradin.ui.theme.*

const val SIGNUP_PHASE_NUM = 5

@Composable
fun SignupScreen(
    onClickBack: () -> Unit,
    viewModel: SignupViewModel = hiltViewModel()
) {

    val collect = viewModel.state.collectAsState()

    val state = collect.value


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
                !state.completeSignup -> {
                    3
                }
                else -> {
                    4
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        when {
            !state.completeAgree -> {
                PolicyAgreementSubScreen() {
                    viewModel.onCompleteAgreement()
                }
            }
            !state.completePhoneAuth -> {
                PhoneAuthSubScreen()
            }
            !state.completeLoginInfo -> {
                LoginInfoSubScreen()
            }
            !state.completeSignup -> {
                UserInfoSubScreen()
            }
            else -> {
                CompleteSignupSubScreen()
            }
        }
    }
}

@Composable
fun PolicyAgreementSubScreen(
    onCompleteAgreement: () -> Unit
) {
    var policyCheck by remember {
        mutableStateOf(false)
    }

    var privacyCheck by remember {
        mutableStateOf(false)
    }

    val allCheck = policyCheck && privacyCheck


    Column(
        modifier = Modifier
            .padding(16.dp, 0.dp, 16.dp, 0.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.signup_policy_agree_title),
                style = TradInTextStyle.text20,
                modifier = Modifier.padding(start = 2.dp),
                color = Black
            )

            VerticalSpacer(dp = 16.dp)

            AgreementItem(
                policy = "아무튼 동의해아무튼 동의해아무튼 동의해아무튼 동의해아무튼 아무튼 동의해아무튼 동아무튼 동의해아무튼 동아무튼 동의해아무튼 동아무튼 동의해아무튼 동아무튼 동의해아무튼 동아무튼 동의해아무튼 동아무튼 동의해아무튼 동동의해아무튼 동의해아무튼 동의해아무튼 동의해아무튼 동의해아무튼 동의해아무튼 동의해아무튼 동의해아무튼 동의해",
                check = policyCheck
            ) {
                policyCheck = it
            }
            
            VerticalSpacer(dp = 24.dp)

            AgreementItem(
                policy = "아무튼 동의해아무튼 아무튼 동의해아무튼 동아무튼 동의해아무튼 동아무튼 동의해아무튼 동아무튼 동의해아무튼 동아무튼 동의아무튼 동의해아무튼 동아무튼 동의해아무튼 동아무튼 동의해아무튼 동해아무튼 동아무튼 동의해아무튼 동동의해아무튼 동의해아무튼 동의해아무튼 동의해아무튼 동의해아무튼 동의해아무튼 동의해아무튼 동의해아무튼 동의해아무튼 동의해아무튼 동의해아무튼 동의해",
                check = privacyCheck
            ) {
                privacyCheck = it
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            CommonCheckBox(
                checked = allCheck,
                offIconId = R.drawable.ic_checkbox_off,
                onIconId = R.drawable.ic_checkbox_on,
                textId = R.string.common_agree_all,
                modifier = Modifier.padding(top = 14.dp),
                onClick = { new ->
                    if (!allCheck && new) {
                        policyCheck = true
                        privacyCheck = true
                    } else if (allCheck && !new) {
                        policyCheck = false
                        privacyCheck = false
                    }
                }
            )
            
            VerticalSpacer(dp = 33.dp)

            LongRomButton(
                text = "다음",
                backgroundColor = if (allCheck) Blue1 else Gray7,
                enable = allCheck,
                textColor = if (allCheck) White else Gray2
            ) {
                onCompleteAgreement()
            }

            VerticalSpacer(dp = 8.dp)
        }
    }
}

@Composable
fun AgreementItem(
    policy: String,
    check: Boolean,
    onClickCheck: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp),
        shape = RoundedCornerShape(10.dp),
        color = Gray7
    ) {
        Text(
            text = policy,
            style = TradInTextStyle.text13,
            color = Gray0,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
        )
    }

    VerticalSpacer(dp = 14.dp)

    CommonCheckBox(
        checked = check,
        offIconId = R.drawable.ic_checkbox_off,
        onIconId = R.drawable.ic_checkbox_on,
        textId = R.string.signup_policy_agree_checkbox_title,
        onClick = { new ->
            onClickCheck(new)
        }
    )
}

@Composable
fun PhoneAuthSubScreen() {

}

@Composable
fun LoginInfoSubScreen() {

}

@Composable
fun UserInfoSubScreen() {

}

@Composable
fun CompleteSignupSubScreen() {

}






