package com.mocomoco.tradin.presentation.login

import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.common.InvalidAccountException
import com.mocomoco.tradin.data.data.dto.request_body.SignInBody
import com.mocomoco.tradin.data.data.repository.AuthRepository
import com.mocomoco.tradin.data.data.resource.local.PreferenceService
import com.mocomoco.tradin.util.ext.addErrorMsg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferenceService: PreferenceService
) : BaseViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun login(email: String, pw: String) = viewModelScope.launch(Dispatchers.IO) {
        _loading.value = true
        val deviceToken = FirebaseMessaging.getInstance().token.await()
        try {
            val dto = authRepository.postSignIn(
                SignInBody(
                    id = email,
                    password = pw,
                    devicePlatform = "aos",
                    deviceToken = deviceToken
                )
            )

            preferenceService.setAccessTokenKey(dto.accessToken.key)
            preferenceService.setAccessToken(dto.accessToken.value)
            preferenceService.setRefreshTokenKey(dto.refreshToken.key)
            preferenceService.setRefreshToken(dto.refreshToken.value)
            preferenceService.setLocation(dto.info.regionCode)

            _state.value = state.value.copy(
                invalidAccount = false,
                completeLogin = true
            )

            _toastMessage.emit("성공적으로 로그인되었어요")
            _loading.value = false
        } catch (e: InvalidAccountException) {
            _state.value = state.value.copy(
                invalidAccount = true,
            )
        } catch (e: Exception) {
            _toastMessage.emit("알 수 없는 에러발생".addErrorMsg(e))
        }
    }
}

data class LoginState(
    val invalidAccount: Boolean = false,
    val completeLogin: Boolean = false
)