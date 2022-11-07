package com.mocomoco.tradin.base

import androidx.lifecycle.ViewModel
import com.mocomoco.tradin.R
import com.mocomoco.tradin.common.Logger
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel: ViewModel() {
    protected val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    protected val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    init {
        Logger.logE("init ${this.javaClass.name.split(".").last()}")
    }

    override fun onCleared() {
        super.onCleared()
        Logger.logE("onCleared ${this.javaClass.name.split(".").last()}")
    }
}