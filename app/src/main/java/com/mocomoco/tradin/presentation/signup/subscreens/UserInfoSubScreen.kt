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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.mocomoco.tradin.presentation.common.CategoryChip
import com.mocomoco.tradin.presentation.common.DefaultRomButton
import com.mocomoco.tradin.presentation.common.DefaultTextFields
import com.mocomoco.tradin.presentation.common.VerticalSpacer
import com.mocomoco.tradin.presentation.signup.UserInfoState
import com.mocomoco.tradin.presentation.signup.components.InfoInputWithDescItem
import com.mocomoco.tradin.presentation.signup.components.InfoInputWithDescTextFieldItem
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
    var nicknameText by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val completeCheckNicknameDuplicate = nicknameText.isNotEmpty() && state.nickname == nicknameText
    val checkNicknameForm = nicknameText.checkNickname()
    val selectedLocation = state.locationCode.isNotEmpty()
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
                text = "????????? ??????! ????????? ??? ??? ???????????????",
                style = RomTextStyle.text20,
                modifier = Modifier.padding(start = 2.dp),
                color = Black
            )

            VerticalSpacer(dp = 18.dp)

            InfoInputWithDescTextFieldItem(
                title = "?????????",
                input = nicknameText,
                onInputChange = { nicknameText = it },
                placeholderText = "???????????? ???????????????",
                descText = when {
                    completeCheckNicknameDuplicate -> "?????? ??????????????????!"
                    !checkNicknameForm && nicknameText.isNotEmpty() -> "????????? ??? ?????? ??????????????????"
                    state.isDuplicate -> "?????? ?????? ?????? ??????????????????"
                    else -> "?????? ?????? 8??????, ?????? ?????? 12?????? (???????????? ??????)"
                },
                descTextColor = when {
                    completeCheckNicknameDuplicate -> Blue1
                    !checkNicknameForm && nicknameText.isNotEmpty() -> Pink1
                    state.isDuplicate -> Pink1
                    else -> Gray2
                },
                buttonText = if (completeCheckNicknameDuplicate) "??????" else "????????????",
                editable = true,
                enableButton = !completeCheckNicknameDuplicate && checkNicknameForm,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            ) { nickname ->
                onClickCheckNicknameDuplicate(nickname)
            }

            VerticalSpacer(dp = 24.dp)


            InfoInputWithDescItem(
                title = "?????? ??????",
                descText = "?????? ????????????????????? ????????? ??? ?????????",
                descTextColor = Gray2
            ) {
                DefaultTextFields(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onClickLocation()
                        },
                    value = state.locationDisplay,
                    onValueChange = {
                        // do nothing
                    },
                    placeholderText = "?????? ????????? ????????? ????????? ???????????????",
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

            InfoInputWithDescItem(
                title = "?????? ???????????? ??????",
                descText = "?????? 3??? ?????? ??????",
                descTextColor = Gray2
            ) {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .align(Alignment.Start),
                    mainAxisAlignment = FlowMainAxisAlignment.Start,
                    mainAxisSpacing = 8.dp,
                    crossAxisAlignment = FlowCrossAxisAlignment.Start,
                    crossAxisSpacing = 4.dp
                ) {
                    state.categories.forEach {
                        CategoryChip(value = it.display, selected = it.selected) { selected ->
                            if (selected && check3Category) {
                                Toast.makeText(context, "?????? 3??? ?????? ??????", Toast.LENGTH_SHORT).show()
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
                text = "??????",
                enable = completeCheckNicknameDuplicate && selectedLocation && check3Category
            ) {
                onClickNext(
                    nicknameText,
                    state.locationCode,
                    state.categories
                )

            }
            VerticalSpacer(dp = 24.dp)
        }
    }
}