package com.mocomoco.tradin.presentation.signup

import androidx.lifecycle.viewModelScope
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.data.data.dto.request_body.PhoneAuthBody
import com.mocomoco.tradin.data.data.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow(SignupInfo())
    val state: StateFlow<SignupInfo> = _state

    fun authWithPhoneNum(body: PhoneAuthBody) = viewModelScope.launch {
        val dto = signupRepository.authenticateWithPhoneNum(body)
        Logger.log("viewModel $dto")
    }


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
