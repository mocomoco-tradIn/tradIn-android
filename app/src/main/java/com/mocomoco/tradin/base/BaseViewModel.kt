package com.mocomoco.tradin.base

import androidx.lifecycle.ViewModel
import com.mocomoco.tradin.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel: ViewModel() {
    protected val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    protected val _toast = MutableStateFlow<Int>(R.string.common_error_message)
    val toast: StateFlow<Int> = _toast

}