package com.mocomoco.tradin.util.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Float.pixelToDp(): Dp {
    with(LocalDensity.current) {
        return this@pixelToDp.toDp()
    }
}