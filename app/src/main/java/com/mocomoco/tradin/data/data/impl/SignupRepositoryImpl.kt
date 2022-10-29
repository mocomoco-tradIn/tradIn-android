package com.mocomoco.tradin.data.data.impl


import com.mocomoco.tradin.data.common.handleResponse
import com.mocomoco.tradin.data.data.dto.request_body.EmailDuplicateBody
import com.mocomoco.tradin.data.data.dto.request_body.AuthCoincideBody
import com.mocomoco.tradin.data.data.dto.request_body.TelBody
import com.mocomoco.tradin.data.data.dto.response.EmailDuplicateDto
import com.mocomoco.tradin.data.data.dto.response.PhoneAuthDto
import com.mocomoco.tradin.data.data.repository.SignupRepository
import com.mocomoco.tradin.data.data.resource.remote.SignupApi
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(
    private val signupApi: SignupApi
) : SignupRepository {
    override suspend fun postTelAuth(body: TelBody): PhoneAuthDto {
        val response = signupApi.postTelAuth(body)
        return handleResponse(response)
    }

    override suspend fun putAuthCoincide(body: AuthCoincideBody) {
        return handleResponse(signupApi.putAuthCoincide(body))
    }

    override suspend fun postEmailDuplicate(body: EmailDuplicateBody): EmailDuplicateDto {
        return handleResponse(signupApi.postEmailDuplicate(body))
    }
}
