package com.mocomoco.tradin.presentation.signup.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.presentation.common.DefaultRomButton
import com.mocomoco.tradin.presentation.common.SignupInputItem
import com.mocomoco.tradin.presentation.common.VerticalSpacer
import com.mocomoco.tradin.presentation.signup.SignupViewModel
import com.mocomoco.tradin.presentation.signup.TelAuthState
import com.mocomoco.tradin.presentation.theme.Black
import com.mocomoco.tradin.presentation.theme.Gray2
import com.mocomoco.tradin.presentation.theme.Pink1
import com.mocomoco.tradin.presentation.theme.RomTextStyle
import com.mocomoco.tradin.util.LoginRegex.checkTelForm
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

            SignupInputItem(
                title = "전화번호 인증",
                input = phoneNum,
                onInputChange = { phoneNum = it },
                placeholderText = "본인 명의 휴대폰 번호를 입력해요",
                descText = if (phoneNum.isEmpty()) "010 포함 '-' 제외 입력" else state.telInputDesc,
                descTextColor = if (phoneNum.isEmpty()) Gray2 else state.telInputDescColor,
                buttonText = "인증하기",
                editable = editablePhoneNum && state.editableTelNum,
                enableButton = phoneNum.checkTelForm() && !state.completeRequestAuth
            ) { number ->
                editablePhoneNum = false
                onClickAuthWithTel(number)
            }

            if (state.completeRequestAuth) {
                VerticalSpacer(dp = 24.dp)

                if (state.completeRequestCoincide.not()) {
                    LaunchedEffect(key1 = state.completeRequestAuthTime) {
                        while (true) {
                            var diff = System.currentTimeMillis() - state.completeRequestAuthTime

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
                } else {
                    remainingTime = ""
                }

                SignupInputItem(
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
                    },
                    isError = state.authCoincideError
                ) { number ->
                    editablePhoneNum = false
                    onClickAuthCoincide(number)
                }
            }

            VerticalSpacer(dp = 16.dp)
        }

        DefaultRomButton(text = "다음", enable = state.completeRequestCoincide) {
            onClickNext(phoneNum, authNum)
        }
    }
}