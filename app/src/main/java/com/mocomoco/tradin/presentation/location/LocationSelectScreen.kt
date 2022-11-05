package com.mocomoco.tradin.presentation.location

import androidx.compose.runtime.Composable
import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.model.Location
import com.mocomoco.tradin.presentation.add.AddViewModel
import com.mocomoco.tradin.presentation.common.DefaultRomButton
import com.mocomoco.tradin.presentation.signup.SignupViewModel
import com.mocomoco.tradin.util.sharedActivityViewModel

@Composable
fun LocationSelectScreen(
    signupViewModel: SignupViewModel = sharedActivityViewModel(),
    addViewModel: AddViewModel = sharedActivityViewModel(),
    back: () -> Unit
) {
    DefaultRomButton(text = "이거누르면 뒤로감", enable = true) {
        Logger.logE("${signupViewModel.state.value}")
        signupViewModel.onCompleteLocationInfo(
            "11680",
            "서울 동대문구"
        )
        addViewModel.onSelectLocation(
            Location(
                "11680",
                "서울 동대문구"
            )
        )
        back()
    }

}


