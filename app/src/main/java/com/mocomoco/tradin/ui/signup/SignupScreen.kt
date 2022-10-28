package com.mocomoco.tradin.ui.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocomoco.tradin.R
import com.mocomoco.tradin.ui.common.DefaultToolbar
import com.mocomoco.tradin.ui.common.SignupProgressBar

@Composable
fun SignupScreen(
    onClickBack: () -> Unit,
    viewModel: SignupViewModel = hiltViewModel()
) {

    val state = viewModel.state.value

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
            5, 1, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )


        when {
            !state.completeAgree -> {
                PolicyAgreementSubScreen()
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
fun PolicyAgreementSubScreen() {

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






