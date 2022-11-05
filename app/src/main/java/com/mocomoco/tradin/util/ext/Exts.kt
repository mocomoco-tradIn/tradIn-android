package com.mocomoco.tradin.util.ext

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.mocomoco.tradin.BuildConfig

@Composable
fun Float.pixelToDp(): Dp {
    with(LocalDensity.current) {
        return this@pixelToDp.toDp()
    }
}

fun String.addArgument(arg: String): String = "$this/$arg"
fun String.addArgument(arg: Int): String = "$this/$arg"
fun String.addArgument(arg: Boolean): String = "$this/$arg"

fun String.addErrorMsg(e: Exception): String {
    return if (BuildConfig.DEBUG) {
        this + e.message
    } else {
        this
    }
}

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}