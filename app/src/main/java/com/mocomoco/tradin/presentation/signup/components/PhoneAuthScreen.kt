package com.mocomoco.tradin.presentation.signup.phon_auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.presentation.common.DefaultRomButton
import com.mocomoco.tradin.presentation.common.DefaultTextFields
import com.mocomoco.tradin.presentation.common.HorizontalSpacer
import com.mocomoco.tradin.presentation.common.VerticalSpacer
import com.mocomoco.tradin.presentation.signup.SignupViewModel
import com.mocomoco.tradin.presentation.signup.TelAuthState
import com.mocomoco.tradin.presentation.theme.Black
import com.mocomoco.tradin.presentation.theme.Gray0
import com.mocomoco.tradin.presentation.theme.Pink1
import com.mocomoco.tradin.presentation.theme.RomTextStyle
import kotlinx.coroutines.delay

@Composable
fun TelAuthSubScreen(
    state: TelAuthState,
    onClickNext: (String, String) -> Unit,
    onClickAuthWithTel: (String) -> Unit,
    onClickAuthCoincide: (String) -> Unit,
    onTimeout: () -> Unit
) {

    var phoneNum by remember {
        mutableStateOf("")
    }

    var authNum by remember {
        mutableStateOf("")
    }

    var editablePhoneNum by remember {
        mutableStateOf(true)
    }

    var editableAuthNum by remember {
        mutableStateOf(true)
    }

    var remainingTime by remember {
        mutableStateOf("")
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp, 16.dp, 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = "본인 인증은 필수!",
                style = RomTextStyle.text20,
                modifier = Modifier.padding(start = 2.dp),
                color = Black
            )

            VerticalSpacer(dp = 18.dp)

            PhoneAuthTextFieldItem(
                title = "전화번호 인증",
                input = phoneNum,
                onInputChange = { phoneNum = it },
                placeholderText = "본인 명의 휴대폰 번호를 입력해요",
                descText = state.telInputDesc,
                descTextColor = state.telInputDescColor,
                editable = editablePhoneNum && state.editableTelNum,
                enableButton = phoneNum.isPhoneNumber() && !state.completeRequestAuth
            ) { number ->
                editablePhoneNum = false
                onClickAuthWithTel(number)
            }

            if (state.completeRequestAuth) {
                VerticalSpacer(dp = 24.dp)

                LaunchedEffect(key1 = state.completeRequestAuthTime) {
                    Logger.log("LaunchedEffect")

                    while (true) {
                        var diff = System.currentTimeMillis() - state.completeRequestAuthTime
                        Logger.log("diff $diff")
                        if (diff > SignupViewModel.AUTH_TIME_OUT) {
                            remainingTime = ""
                            onTimeout()
                            break
                        }
                        val display = SignupViewModel.AUTH_TIME_OUT - diff

                        val sec = (display / 1000) % 60
                        val min = (display / 1000 / 60) % 60
                        remainingTime = "%02d:%02d".format(min, sec)
                        delay(1000L)
                    }
                }

                PhoneAuthTextFieldItem(
                    title = "인증번호 입력",
                    input = authNum,
                    onInputChange = { authNum = it },
                    placeholderText = "",
                    descText = state.authNumInputDesc,
                    descTextColor = state.authNumInputDescColor,
                    buttonText = if (state.completeRequestCoincide) "완료" else "인증하기",
                    editable = editableAuthNum && state.editableAuthNum,
                    enableButton = authNum.isNotEmpty() && !state.completeRequestCoincide,
                    trailingIcon = {
                        Text(text = remainingTime, style = RomTextStyle.text11, color = Pink1)
                    }
                ) { number ->
                    editablePhoneNum = false
                    onClickAuthCoincide(number)
                }
            }
        }

        DefaultRomButton(text = "다음", enable = state.completeRequestCoincide) {
            onClickNext(phoneNum, authNum)
        }
    }
}


@Composable
fun PhoneAuthTextFieldItem(
    title: String,
    input: String,
    onInputChange: (String) -> Unit,
    placeholderText: String = "",
    descText: String,
    descTextColor: Color,
    buttonText: String = "인증하기",
    enableButton: Boolean,
    editable: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    onClickButton: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, style = RomTextStyle.text14, color = Gray0)

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            DefaultTextFields(
                value = input,
                onValueChange = onInputChange,
                placeholderText = placeholderText,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                enabled = editable,
                trailingIcon = trailingIcon
            )
            HorizontalSpacer(dp = 8.dp)
            DefaultRomButton(
                text = buttonText,
                enable = enableButton,
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .width(84.dp),
                textStyle = RomTextStyle.text14
            ) {
                onClickButton(input)
            }
        }
        VerticalSpacer(dp = 8.dp)

        Text(text = descText, style = RomTextStyle.text14, color = descTextColor)
    }
}

private fun String.isPhoneNumber() = this.length == 11 && this.startsWith("010")
