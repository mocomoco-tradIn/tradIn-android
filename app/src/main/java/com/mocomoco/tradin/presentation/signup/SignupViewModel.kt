package com.mocomoco.tradin.presentation.signup

import androidx.lifecycle.viewModelScope
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.common.*
import com.mocomoco.tradin.data.data.dto.request_body.AuthCoincideBody
import com.mocomoco.tradin.data.data.dto.request_body.EmailDuplicateBody
import com.mocomoco.tradin.data.data.dto.request_body.SignupBody
import com.mocomoco.tradin.data.data.dto.request_body.TelBody
import com.mocomoco.tradin.data.data.dto.response.NicknameDuplicateBody
import com.mocomoco.tradin.data.data.repository.SignupRepository
import com.mocomoco.tradin.presentation.theme.Blue1
import com.mocomoco.tradin.presentation.theme.Pink1
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    fun postTelAuth(input: String) = viewModelScope.launch(Dispatchers.IO) {
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
                userInputSignupInfo = state.userInputSignupInfo.copy(
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

    fun putAuthCoincide(authNum: String) = viewModelScope.launch(Dispatchers.IO) {
        _loading.value = true

        with(state.value) {
            try {
                signupRepository.putAuthCoincide(
                    AuthCoincideBody(code = authNum, tel = userInputSignupInfo.tel)
                )
                _state.value = copy(
                    telAuthState = telAuthState.copy(
                        authNumInputDesc = "인증 완료!",
                        authNumInputDescColor = Blue1,
                        completeRequestCoincide = true,
                        editableAuthNum = false
                    )
                )
            } catch (e: NotMatchedAuthNumberException) {
                _state.value = copy(
                    telAuthState = telAuthState.copy(
                        authNumInputDesc = "잘못된 입력입니다",
                        authNumInputDescColor = Pink1,
                        editableAuthNum = true,
                        authCoincideError = true
                    )
                )
            } catch (e: Exception) {
                _state.value = copy(
                    telAuthState = telAuthState.copy(
                        authNumInputDesc = "알 수 없는 오류가 발생했어요",
                        authNumInputDescColor = Pink1,
                        editableAuthNum = true,
                        authCoincideError = true
                    )
                )
            } finally {
                _loading.value = false
            }
        }
    }

    fun postEmailDuplicate(email: String) = viewModelScope.launch(Dispatchers.IO) {
        _loading.value = true

        with(state.value) {
            try {
                signupRepository.postEmailDuplicate(EmailDuplicateBody(email))
                _state.value = copy(
                    loginInfoState = loginInfoState.copy(
                        email = email, // todo 응답으로
                        isDuplicate = false
                    ),
                    userInputSignupInfo = userInputSignupInfo.copy(
                        email = email // todo 응답으로
                    )
                )
            } catch (e: DuplicateEmailException) {
                _state.value = copy(
                    loginInfoState = loginInfoState.copy(
                        email = "",
                        isDuplicate = true
                    ),
                    userInputSignupInfo = userInputSignupInfo.copy(
                        email = ""
                    )
                )
            } catch (e: Exception) {
                //_toast.value = R.string.common_error_message
            } finally {
                _loading.value = false
            }
        }
    }

    fun postNicknameDuplicate(nickname: String) = viewModelScope.launch(Dispatchers.IO) {
        _loading.value = true

        with(state.value) {
            try {
                signupRepository.postNicknameDuplicate(NicknameDuplicateBody(nickname))
                _state.value = copy(
                    userInfoState = userInfoState.copy(
                        nickname = nickname,
                        isDuplicate = false
                    )
                )
            } catch (e: DuplicateNicknameException) {
                _state.value = copy(
                    userInfoState = userInfoState.copy(
                        nickname = "",
                        isDuplicate = true
                    )
                )
            } catch (e: Exception) {

            } finally {
                _loading.value = false
            }
        }
    }

    private fun postSignup() = viewModelScope.launch(Dispatchers.IO) {
        _loading.value = true

        Logger.log("${state.value.userInputSignupInfo}")

        with(state.value) {
            try {
                signupRepository.postSignup(
                    SignupBody(
                        tel = userInputSignupInfo.tel,
                        email = userInputSignupInfo.email,
                        password = userInputSignupInfo.pw,
                        nickname = userInputSignupInfo.nickname,
                        regionCode = userInputSignupInfo.locationCode,
                        category = userInputSignupInfo.categories
                    )
                )
            } catch (e: Exception) {

            } finally {
                _loading.value = false
            }
        }
    }



    fun onTimeout() {
        with(state.value) {
            _state.value = copy(
                telAuthState = TelAuthState(
                    telInputDesc = "재전송하려면 인증하기를 눌러주세요"
                )
            )
        }
    }

    fun onSelectCategory(code: Int, selected: Boolean) {
        with(state.value) {
            _state.value = copy(
                userInfoState = userInfoState.copy(
                    categories = userInfoState.categories.map {
                        if (code == it.code) {
                            it.copy(
                                selected = selected
                            )
                        } else {
                            it
                        }
                    }
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
            userInputSignupInfo = state.userInputSignupInfo.copy(
                tel = phoneNum
            )
        )
    }

    fun onCompleteLoginInfo(email: String, pw: String) {
        val state = state.value
        _state.value = state.copy(
            completeLoginInfo = true,
            userInputSignupInfo = state.userInputSignupInfo.copy(
                email = email,
                pw = pw
            )
        )
    }

    fun onCompleteUserInfo(nickname: String, location: String, categories: List<Int>) {
        val state = state.value
        _state.value = state.copy(
            userInputSignupInfo = state.userInputSignupInfo.copy(
                nickname = nickname,
                locationCode = location,
                categories = categories
            )
        )
        postSignup()
    }

    companion object {
        const val AUTH_TIME_OUT = 1000 * 60 * 30
    }
}
