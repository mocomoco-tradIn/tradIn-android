package com.mocomoco.tradin.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mocomoco.tradin.R
import com.mocomoco.tradin.presentation.theme.Gray0
import com.mocomoco.tradin.presentation.theme.Gray7
import com.mocomoco.tradin.presentation.theme.TradInTypography

@Composable
fun DefaultToolbar(
    modifier: Modifier = Modifier,
    showBack: Boolean = false,
    onClickBack: () -> Unit = {},
    title: String = ""
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(0.dp, 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            if (showBack) {
                Icon(
                    modifier = modifier.clickable {
                        onClickBack()
                    },
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null
                )
            }

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = title,
                style = TradInTypography.h4,
                color = Gray0
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp), color = Gray7
        )
    }
}
