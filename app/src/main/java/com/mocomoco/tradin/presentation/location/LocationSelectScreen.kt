package com.mocomoco.tradin.presentation.location

import androidx.compose.runtime.Composable
import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.presentation.common.DefaultRomButton
import com.mocomoco.tradin.presentation.signup.SignupViewModel
import com.mocomoco.tradin.util.sharedActivityViewModel

@Composable
fun LocationSelectScreen(
    viewModel: SignupViewModel = sharedActivityViewModel(),
    back: () -> Unit
) {
    DefaultRomButton(text = "이거누르면 뒤로감", enable = true) {
        Logger.logE("${viewModel.state.value}")
        viewModel.onCompleteLocationInfo(
            "10010",
            "서울 동대문구"
        )
        back()
    }

}


