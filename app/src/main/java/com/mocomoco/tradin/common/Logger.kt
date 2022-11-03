package com.mocomoco.tradin.common

import android.util.Log

object Logger {
    // Timber 로 교체예정
    fun log(content: String) {
        Log.d("SR-N", content)
    }

    fun logE(content: String) {
        Log.e("SR-N", content)
    }
}