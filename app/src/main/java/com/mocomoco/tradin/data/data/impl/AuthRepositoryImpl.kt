package com.mocomoco.tradin.data.data.impl


import com.mocomoco.tradin.data.common.handleResponse
import com.mocomoco.tradin.data.data.dto.request_body.*
import com.mocomoco.tradin.data.data.dto.response.NicknameDuplicateBody
import com.mocomoco.tradin.data.data.dto.response.PhoneAuthDto
import com.mocomoco.tradin.data.data.dto.response.login.SignInDto
import com.mocomoco.tradin.data.data.repository.AuthRepository
import com.mocomoco.tradin.data.data.resource.remote.apis.AuthApi
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun postTelAuth(body: TelBody): PhoneAuthDto {
        val response = authApi.postTelAuth(body)
        return handleResponse(response)
    }

    override suspend fun putAuthCoincide(body: AuthCoincideBody) {
        return handleResponse(authApi.putAuthCoincide(body))
    }

    override suspend fun postEmailDuplicate(body: EmailDuplicateBody) {
        return handleResponse(authApi.postEmailDuplicate(body))
    }

    override suspend fun postNicknameDuplicate(body: NicknameDuplicateBody) {
        return handleResponse(authApi.postNickDuplicate(body))
    }

    override suspend fun postSignup(body: SignupBody) {
        return handleResponse(authApi.postSignup(body))
    }

    override suspend fun postSignIn(body: SignInBody): SignInDto {
        return handleResponse(authApi.postSignIn(body))
    }
}
