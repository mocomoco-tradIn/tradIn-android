package com.mocomoco.tradin.presentation.signup

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewModelScope
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.common.InvalidTelException
import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.common.ServerException
import com.mocomoco.tradin.data.data.dto.request_body.AuthCoincideBody
import com.mocomoco.tradin.data.data.dto.request_body.EmailDuplicateBody
import com.mocomoco.tradin.data.data.dto.request_body.TelBody
import com.mocomoco.tradin.data.data.repository.SignupRepository
import com.mocomoco.tradin.presentation.theme.Blue1
import com.mocomoco.tradin.presentation.theme.Gray2
import com.mocomoco.tradin.presentation.theme.Pink1
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow(SignupState())
    val state: StateFlow<SignupState> = _state

    fun postTelAuth(input: String) = viewModelScope.launch {
        val state = state.value
        try {
            _loading.value = true
            val tel = signupRepository.postTelAuth(TelBody(tel = input)).tel
            _state.value = state.copy(
                telAuthState = state.telAuthState.copy(
                    completeRequestAuth = true,
                    completeRequestAuthTime = System.currentTimeMillis(),
                    tel = tel,
                    telInputDesc = "인증번호가 전송되었어요",
                    telInputDescColor = Blue1,
                    editableTelNum = false
                ),
                userInputInfo = state.userInputInfo.copy(
                    tel = tel,
                )
            )
        } catch (e: InvalidTelException) {
            _state.value = state.copy(
                telAuthState = state.telAuthState.copy(
                    telInputDesc = e.message,
                    telInputDescColor = Pink1,
                )
            )
        } catch (e: Exception) {
            Logger.log("error Exception $e")
        } finally {
            _loading.value = false
        }
    }

    fun putAuthCoincide(authNum: String) = viewModelScope.launch {
        _loading.value = true

        with(state.value) {
            try {
                signupRepository.putAuthCoincide(
                    AuthCoincideBody(code = authNum, tel = userInputInfo.tel)
                )
                _state.value = copy(
                    telAuthState = telAuthState.copy(
                        authNumInputDesc = "인증 완료!",
                        authNumInputDescColor = Blue1,
                        completeRequestCoincide = true,
                        editableAuthNum = false
                    )
                )
            } catch (e: ServerException) {
                _state.value = copy(
                    telAuthState = telAuthState.copy(
                        authNumInputDesc = "잘못된 입력입니다",
                        authNumInputDescColor = Pink1,
                        editableAuthNum = true
                    )
                )
            } catch (e: Exception) {
                _state.value = copy(
                    telAuthState = telAuthState.copy(
                        authNumInputDesc = "알 수 없는 오류가 발생했어요",
                        authNumInputDescColor = Pink1,
                        editableAuthNum = true
                    )
                )            } finally {
                _loading.value = false
            }
        }
    }

    fun postEmailDuplicate(body: EmailDuplicateBody) = viewModelScope.launch {
        _loading.value = true

        with(state.value) {

        }
    }

    fun onTimeout() {
        with (state.value) {
            _state.value = copy(
                telAuthState = TelAuthState(
                    telInputDesc = "재전송하려면 인증하기를 눌러주세요"
                )
            )
        }
    }

    fun onCompleteAgreement() {
        _state.value = state.value.copy(completeAgree = true)
    }

    fun onCompletePhoneAuth(phoneNum: String) {
        val state = state.value
        _state.value = state.copy(
            completePhoneAuth = true,
            userInputInfo = state.userInputInfo.copy(
                tel = phoneNum
            )
        )
    }

    fun onCompleteLoginInfo(email: String, pw: String) {
        val state = state.value
        _state.value = state.copy(
            completeLoginInfo = true,
            userInputInfo = state.userInputInfo.copy(
                email = email,
                pw = pw
            )
        )
    }

    fun onCompleteUserInfo(nickname: String, location: String, categories: List<Int>) {
        val state = state.value
        _state.value = state.copy(
            completeUserInfo = true,
            userInputInfo = state.userInputInfo.copy(
                nickname = nickname,
                location = location,
                categories = categories
            )
        )
    }

    companion object {
        const val AUTH_TIME_OUT = 1000 * 60 * 30
    }
}
