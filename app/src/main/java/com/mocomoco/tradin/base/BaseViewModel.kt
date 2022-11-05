package com.mocomoco.tradin.base

import androidx.lifecycle.ViewModel
import com.mocomoco.tradin.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel: ViewModel() {
    protected val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    protected val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

}