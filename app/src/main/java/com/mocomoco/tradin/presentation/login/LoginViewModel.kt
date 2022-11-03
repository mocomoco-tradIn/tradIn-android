package com.mocomoco.tradin.presentation.login

import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.data.data.dto.request_body.SignInBody
import com.mocomoco.tradin.data.data.repository.AuthRepository
import com.mocomoco.tradin.data.data.resource.local.PreferenceService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferenceService: PreferenceService
) : BaseViewModel() {

    fun login(email: String, pw: String) = viewModelScope.launch(Dispatchers.IO) {
        _loading.value = true
        val deviceToken = FirebaseMessaging.getInstance().token.result
        Logger.log("deviceToken $deviceToken")

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            val result = it.result
            Logger.log("deviceToken result ${result}")
        }

        try {
            authRepository.postSignIn(
                SignInBody(
                    id = email,
                    password = pw,
                    devicePlatform = "aos",
                    deviceToken = deviceToken
                )
            )
            _loading.value = false
        } catch (e: Exception) {

        }
    }

    fun setAutoLogin(enable: Boolean) {
        preferenceService.setAutoLogin(enable)
    }
}

data class LoginState(
    val isLoading: Boolean = false,
    val isError: Boolean = false
)