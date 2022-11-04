package com.mocomoco.tradin.presentation.signup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.presentation.common.RightButtonTextField
import com.mocomoco.tradin.presentation.common.VerticalSpacer
import com.mocomoco.tradin.presentation.theme.Gray0
import com.mocomoco.tradin.presentation.theme.RomTextStyle
import com.mocomoco.tradin.presentation.theme.Transparent

@Composable
fun InfoInputWithDescItem(
    title: String,
    descText: String = "",
    descTextColor: Color = Transparent,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, style = RomTextStyle.text14, color = Gray0)

        content()

        VerticalSpacer(dp = 6.dp)
        Text(text = descText, style = RomTextStyle.text14, color = descTextColor)
    }
}

@Composable
fun InfoInputWithDescTextFieldItem(
    title: String,
    input: String,
    onInputChange: (String) -> Unit,
    enableButton: Boolean = false,
    descText: String = "",
    descTextColor: Color = Transparent,
    placeholderText: String = "",
    buttonText: String = "",
    editable: Boolean = true,
    isError: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    keyboardActions: KeyboardActions = KeyboardActions(),
    maxLines: Int = Int.MAX_VALUE,
    onClickButton: (String) -> Unit = {}
) {
    InfoInputWithDescItem(
        title = title,
        descText = descText,
        descTextColor = descTextColor
    ) {
        RightButtonTextField(
            input = input,
            onInputChange = onInputChange,
            placeholderText = placeholderText,
            keyboardOptions = keyboardOptions,
            editable = editable,
            trailingIcon = trailingIcon,
            isError = isError,
            visualTransformation = visualTransformation,
            buttonText = buttonText,
            enableButton = enableButton,
            maxLines = maxLines,
            keyboardActions = keyboardActions
        ) { text ->
            onClickButton(text)
        }
    }
}
