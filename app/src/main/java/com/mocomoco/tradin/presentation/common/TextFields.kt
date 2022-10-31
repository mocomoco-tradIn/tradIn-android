package com.mocomoco.tradin.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.presentation.signup.components.SignupInfoInputItem
import com.mocomoco.tradin.presentation.theme.*

@Composable
fun DefaultTextFields(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    contentPaddingValues: PaddingValues = PaddingValues(4.dp, 12.dp),
    singleLine: Boolean = true,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        textColor = Gray0,
        backgroundColor = Transparent,
        cursorColor = Gray0,
        focusedIndicatorColor = Gray0,
        unfocusedIndicatorColor = Gray3,
        errorIndicatorColor = Pink1
    ),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    enabled: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false
    ) {
    TradInTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        contentPaddingValues = contentPaddingValues,
        singleLine = singleLine,
        placeholder = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.h5,
                color = Gray3
            )
        },
        colors = colors,
        visualTransformation = visualTransformation,
        textStyle = MaterialTheme.typography.h5,
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        trailingIcon = trailingIcon,
        isError = isError
    )
}


@Composable
fun TradInTextField(
    value: String,
    onValueChange: (String) -> Unit,
    contentPaddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape =
        MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    @OptIn(ExperimentalMaterialApi::class)
    BasicTextField(
        value = value,
        modifier = modifier
            .background(colors.backgroundColor(enabled).value, shape)
            .indicatorLine(enabled, isError, interactionSource, colors)
            .defaultMinSize(
                minWidth = TextFieldDefaults.MinWidth,
                minHeight = TextFieldDefaults.MinHeight
            ),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                singleLine = singleLine,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = contentPaddingValues
            )
        }
    )
}

@Composable
fun RightButtonTextField(
    input: String,
    onInputChange: (String) -> Unit,
    enableButton: Boolean = false,
    placeholderText: String = "",
    buttonText: String = "",
    editable: Boolean = true,
    isError: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    onClickButton: (String) -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        DefaultTextFields(
            value = input,
            onValueChange = onInputChange,
            placeholderText = placeholderText,
            keyboardOptions = keyboardOptions,
            modifier = Modifier.weight(1f),
            enabled = editable,
            trailingIcon = trailingIcon,
            isError = isError,
            visualTransformation = visualTransformation
        )
        HorizontalSpacer(dp = 8.dp)

        if (buttonText.isNotEmpty()) {
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
    }
}