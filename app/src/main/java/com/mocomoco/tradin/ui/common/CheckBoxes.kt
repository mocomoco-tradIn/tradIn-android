package com.mocomoco.tradin.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.ui.theme.Gray1

@Composable
fun CommonCheckBox(
    checked: Boolean,
    @DrawableRes offIconId: Int,
    @DrawableRes onIconId: Int,
    modifier: Modifier = Modifier,
    @StringRes textId: Int,
    onClick: (Boolean) -> Unit
) {

    Row(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            onClick(checked.not())
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(
                id = if (checked) {
                    onIconId
                } else {
                    offIconId
                }
            ),
            contentDescription = null
        )

        HorizontalSpacer(8.dp)

        Text(
            text = stringResource(id = textId),
            style = MaterialTheme.typography.subtitle2,
            color = Gray1
        )
    }


}