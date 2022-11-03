package com.mocomoco.tradin.presentation.signup.subscreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.R
import com.mocomoco.tradin.presentation.common.DefaultRomButton
import com.mocomoco.tradin.presentation.common.VerticalSpacer
import com.mocomoco.tradin.presentation.theme.Gray0
import com.mocomoco.tradin.presentation.theme.Gray3
import com.mocomoco.tradin.presentation.theme.RomTextStyle

@Composable
fun CompleteSignupSubScreen(
    onClickNext: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_clapping_hands),
                contentDescription = null
            )
            VerticalSpacer(dp = 14.dp)
            Text(
                text = "회원가입 완료",
                style = RomTextStyle.text17, color = Gray0
            )
            VerticalSpacer(dp = 6.dp)
            Text(
                text = "롬롬에 오신 걸 환영해요",
                style = RomTextStyle.text13, color = Gray3
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            DefaultRomButton(
                text = "회원가입 완료",
                enable = true
            ) {
                onClickNext.invoke()
            }
            VerticalSpacer(dp = 24.dp)
        }
    }
}

@Preview
@Composable
fun PreviewCompleteSignupSubScreen() {
    CompleteSignupSubScreen() {

    }
}