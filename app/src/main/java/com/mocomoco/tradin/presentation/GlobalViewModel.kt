package com.mocomoco.tradin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mocomoco.tradin.data.data.resource.local.PreferenceService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val preferenceService: PreferenceService
): ViewModel() {

    private val _splashLoading = MutableStateFlow(true)
    val splashLoading = _splashLoading.asStateFlow()


    init {
        viewModelScope.launch {
            delay(300L)
            _splashLoading.value = false
        }
    }


    fun showOnBoarding(): Boolean {
        return preferenceService.showBoarding()
    }

    fun isLogin(): Boolean = preferenceService.isLogin()
}