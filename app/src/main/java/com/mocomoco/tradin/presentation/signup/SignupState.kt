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
    val userInfoState: UserInfoState = UserInfoState(),
    val userInputSignupInfo: UserInputSignupInfo = UserInputSignupInfo()
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

data class UserInfoState(
    val nickname: String = "",
    val locationDisplay: String = "",
    val locationCode: String = "",
    val categories: List<Category> =
        listOf(
            "의류",
            "서적/문구",
            "취미",
            "전자제품",
            "잡화",
            "아이돌 굿즈",
            "티켓/교환권"
        ).mapIndexed { index, display ->
            Category(
                display = display,
                code = index,
                selected = false
            )
        },
    val isDuplicate: Boolean = false
) {
    val selected3Items: Boolean
        get() = categories.count {
            it.selected
        } == 3

    data class Category(
        val display: String,
        val code: Int,
        val selected: Boolean
    )
}

data class UserInputSignupInfo(
    val tel: String = "",
    val email: String = "",
    val pw: String = "",
    val nickname: String = "",
    val locationCode: String = "",
    val categories: List<Int> = listOf(),
)
