package com.mocomoco.tradin.presentation.signup.agree

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
import com.mocomoco.tradin.R
import com.mocomoco.tradin.presentation.common.CommonCheckBox
import com.mocomoco.tradin.presentation.common.DefaultRomButton
import com.mocomoco.tradin.presentation.common.VerticalSpacer
import com.mocomoco.tradin.presentation.theme.Black
import com.mocomoco.tradin.presentation.theme.Gray0
import com.mocomoco.tradin.presentation.theme.Gray7
import com.mocomoco.tradin.presentation.theme.RomTextStyle


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
                style = RomTextStyle.text20,
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

            DefaultRomButton(
                text = "다음",
                enable = allCheck,
                textStyle = RomTextStyle.text14
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
            style = RomTextStyle.text13,
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