package com.mocomoco.tradin.presentation.signup.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.presentation.common.DefaultRomButton
import com.mocomoco.tradin.presentation.common.SignupInputItem
import com.mocomoco.tradin.presentation.common.VerticalSpacer
import com.mocomoco.tradin.presentation.signup.LoginInfoState
import com.mocomoco.tradin.presentation.theme.*
import com.mocomoco.tradin.util.LoginRegex.checkEmailForm
import com.mocomoco.tradin.util.LoginRegex.checkPwForm

@Composable
fun LoginInfoSubScreen(
    state: LoginInfoState,
    onClickCheckDuplicate: (String) -> Unit,
    onClickNext: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp, 16.dp, 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        var emailText by remember {
            mutableStateOf("")
        }


        var pwText by remember {
            mutableStateOf("")
        }
        var pwCheckText by remember {
            mutableStateOf("")
        }

        val completeCheckEmailDuplicate = emailText.isNotEmpty() && state.email == emailText
        val checkEmailForm = emailText.checkEmailForm()

        val invalidPwForm = !pwText.checkPwForm()
        val pwCheckError = pwCheckText.isNotEmpty() && pwText != pwCheckText


        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "정보를 입력해주세요",
                style = RomTextStyle.text20,
                modifier = Modifier.padding(start = 2.dp),
                color = Black
            )

            VerticalSpacer(dp = 18.dp)

            SignupInputItem(
                title = "이메일",
                input = emailText,
                onInputChange = { emailText = it },
                placeholderText = "이메일을 입력해요",
                descText = when {
                    state.isDuplicate && checkEmailForm -> "이미 가입한 이메일입니다"
                    completeCheckEmailDuplicate -> "사용 가능한 이메일 입니다"
                    emailText.isEmpty() -> "@ 포함 입력"
                    !checkEmailForm -> "잘못된 형식입니다"
                    else -> ""
                },
                descTextColor = when {
                    state.isDuplicate && checkEmailForm -> Pink1
                    completeCheckEmailDuplicate -> Blue1
                    emailText.isEmpty() -> Gray2
                    !checkEmailForm -> Pink1
                    else -> Gray2
                },
                buttonText = if (!completeCheckEmailDuplicate) "중복확인" else "완료",
                editable = true,
                enableButton = !completeCheckEmailDuplicate && checkEmailForm,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            ) { email ->
                onClickCheckDuplicate(email)
            }

            VerticalSpacer(dp = 24.dp)

            SignupInputItem(
                title = "비밀번호 입력",
                input = pwText,
                onInputChange = { pwText = it },
                placeholderText = "안전한 비밀번호로 부탁해요",
                descText = when {
                    pwText.isNotEmpty() && invalidPwForm -> "잘못된 입력입니다 (영문, 숫자, 특수문자 포함 8자 이상)"
                    !invalidPwForm -> "사용 가능한 비밀번호"
                    else -> "영문, 숫자 ,특수문자 포함 8자 이상"
                },
                descTextColor = when {
                    pwText.isNotEmpty() && invalidPwForm -> Pink1
                    pwText.checkPwForm() -> Blue1
                    else -> Gray2
                },
                buttonText = "",
                enableButton = false,
                isError = pwCheckError,
                visualTransformation = { annotatedString ->
                    PasswordVisualTransformation().filter(annotatedString)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            VerticalSpacer(dp = 24.dp)

            SignupInputItem(
                title = "비밀번호 확인",
                input = pwCheckText,
                onInputChange = { pwCheckText = it },
                placeholderText = "비밀번호를 다시 한번 입력하세요",
                descText = if (pwCheckError) "비밀번호가 다릅니다" else "",
                descTextColor = if (pwCheckError) Pink1 else Transparent,
                visualTransformation = { annotatedString ->
                    PasswordVisualTransformation().filter(annotatedString)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            VerticalSpacer(dp = 16.dp)
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            VerticalSpacer(dp = 16.dp)

            DefaultRomButton(
                text = "다음",
                enable = completeCheckEmailDuplicate && checkEmailForm && !invalidPwForm && pwText == pwCheckText
            ) {
                onClickNext(emailText, pwText)
            }
        }
    }
}