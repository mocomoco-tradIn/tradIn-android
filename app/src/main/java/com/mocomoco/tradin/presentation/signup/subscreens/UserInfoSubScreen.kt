package com.mocomoco.tradin.presentation.signup.subscreens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.mocomoco.tradin.R
import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.presentation.common.CategoryChip
import com.mocomoco.tradin.presentation.common.DefaultRomButton
import com.mocomoco.tradin.presentation.common.DefaultTextFields
import com.mocomoco.tradin.presentation.common.VerticalSpacer
import com.mocomoco.tradin.presentation.signup.UserInfoState
import com.mocomoco.tradin.presentation.signup.components.SignupInfoInputItem
import com.mocomoco.tradin.presentation.signup.components.SignupTextFieldInputItem
import com.mocomoco.tradin.presentation.theme.*
import com.mocomoco.tradin.util.LoginRegex.checkNickname

@Composable
fun UserInfoSubScreen(
    state: UserInfoState,
    onClickCheckNicknameDuplicate: (String) -> Unit,
    onClickLocation: () -> Unit,
    onClickChip: (Int, Boolean) -> Unit,
    onClickNext: (String, String, List<UserInfoState.Category>) -> Unit
) {
    var nicknameText by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val completeCheckNicknameDuplicate = nicknameText.isNotEmpty() && state.nickname == nicknameText
    val checkNicknameForm = nicknameText.checkNickname()
    var selectedLocation = state.locationCode.isNotEmpty()
    val check3Category = state.selected3Items

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "마지막 단계! 당신을 좀 더 알려주세요",
                style = RomTextStyle.text20,
                modifier = Modifier.padding(start = 2.dp),
                color = Black
            )

            VerticalSpacer(dp = 18.dp)

            SignupTextFieldInputItem(
                title = "닉네임",
                input = nicknameText,
                onInputChange = { nicknameText = it },
                placeholderText = "닉네임을 입력하세요",
                descText = when {
                    completeCheckNicknameDuplicate -> "멋진 닉네임이네요!"
                    !checkNicknameForm && nicknameText.isNotEmpty() -> "사용할 수 없는 닉네임입니다"
                    state.isDuplicate -> "이미 사용 중인 닉네임입니다"
                    else -> "한글 최대 8글자, 영어 최대 12글자 (특수문자 불가)"
                },
                descTextColor = when {
                    completeCheckNicknameDuplicate -> Blue1
                    !checkNicknameForm && nicknameText.isNotEmpty() -> Pink1
                    state.isDuplicate -> Pink1
                    else -> Gray2
                },
                buttonText = if (completeCheckNicknameDuplicate) "완료" else "중복확인",
                editable = true,
                enableButton = !completeCheckNicknameDuplicate && checkNicknameForm,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            ) { nickname ->
                onClickCheckNicknameDuplicate(nickname)
            }

            VerticalSpacer(dp = 24.dp)


            SignupInfoInputItem(
                title = "지역 설정",
                descText = "이후 마이페이지에서 변경할 수 있어요",
                descTextColor = Gray2
            ) {
                DefaultTextFields(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedLocation = true // todo onClickLocation()
                        },
                    value = state.locationDisplay,
                    onValueChange = {
                        // do nothing
                    },
                    placeholderText = "주로 교환을 진행할 지역을 설정하세요",
                    enabled = false,
                    trailingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = null
                        )
                    }
                )
            }

            VerticalSpacer(dp = 24.dp)

            SignupInfoInputItem(
                title = "관심 카테고리 설정",
                descText = "최대 3개 선택 가능",
                descTextColor = Gray2
            ) {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start),
                    mainAxisAlignment = FlowMainAxisAlignment.Start,
                    mainAxisSpacing = 8.dp,
                    crossAxisAlignment = FlowCrossAxisAlignment.Start,
                    crossAxisSpacing = 4.dp
                ) {
                    Logger.log("categories ${state.categories}")
                    state.categories.forEach {
                        CategoryChip(value = it.display, selected = it.selected) { selected ->
                            if (selected && check3Category) {
                                Toast.makeText(context, "최대 3개 선택 가능", Toast.LENGTH_SHORT).show()
                            } else {
                                onClickChip(it.code, selected)
                            }
                        }
                    }
                }
            }

            VerticalSpacer(dp = 16.dp)
        }

        Column(modifier = Modifier.fillMaxWidth()) {

            DefaultRomButton(
                text = "다음",
                enable = completeCheckNicknameDuplicate && selectedLocation && check3Category
            ) {
                onClickNext(
                    nicknameText,
                    "10110", // todo state.locationCode,
                    state.categories
                )
            }
            VerticalSpacer(dp = 24.dp)
        }
    }
}