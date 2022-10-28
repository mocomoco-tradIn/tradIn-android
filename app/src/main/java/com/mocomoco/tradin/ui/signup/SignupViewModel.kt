package com.mocomoco.tradin.ui.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.mocomoco.tradin.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor() : BaseViewModel() {
    private val _state = MutableStateFlow(SignupInfo())
    val state: StateFlow<SignupInfo> = _state


    fun onCompleteAgreement() {
        _state.value = state.value.copy(completeAgree = true)
    }

}

data class SignupInfo(
    val completeAgree: Boolean = false,
    val phoneNum: String = "",
    val phoneAuthNum: String = "",
    val completePhoneAuth: Boolean = false,
    val email: String = "",
    val pw: String = "",
    val completeLoginInfo: Boolean = false,
    val nickname: String = "",
    val location: String = "",
    val selectedCategories: List<String> = listOf(),
    val completeSignup: Boolean = false
)
