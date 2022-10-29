package com.mocomoco.tradin.presentation.signup

import androidx.compose.ui.graphics.Color
import com.mocomoco.tradin.presentation.theme.Gray0
import com.mocomoco.tradin.presentation.theme.Gray2
import com.mocomoco.tradin.presentation.theme.Transparent

data class SignupState(
    val completeAgree: Boolean = false,
    val completePhoneAuth: Boolean = false,
    val completeLoginInfo: Boolean = false,
    val completeUserInfo: Boolean = false,
    val telAuthState: TelAuthState = TelAuthState(),
    val loginInfoState: LoginInfoState = LoginInfoState(),
    val userInputInfo: UserInputInfo = UserInputInfo()
)

data class TelAuthState(
    val completeRequestAuth: Boolean = false,
    val completeRequestAuthTime: Long = 0,
    val completeRequestCoincide: Boolean = false,
    val tel: String = "",
    val telInputDesc: String = "010 포함 '-' 제외 입력",
    val telInputDescColor: Color = Gray2,
    val authNumInputDesc: String = "",
    val authNumInputDescColor: Color = Transparent,
    val telBottomLineColor: Color = Gray0,
    val editableTelNum: Boolean = true,
    val editableAuthNum: Boolean = true,
    val authCoincideError: Boolean = false,
)

data class LoginInfoState(
    val email: String = "",
    val isDuplicate: Boolean = false
)

data class UserInputInfo(
    val tel: String = "",
    val email: String = "",
    val pw: String = "",
    val nickname: String = "",
    val location: String = "",
    val categories: List<Int> = listOf(),
)
