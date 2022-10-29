package com.mocomoco.tradin.data.data.impl


import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.data.data.dto.request_body.PhoneAuthBody
import com.mocomoco.tradin.data.data.dto.response.PhoneAuthDto
import com.mocomoco.tradin.data.data.repository.SignupRepository
import com.mocomoco.tradin.data.data.resource.remote.SignupApi
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(
    private val signupApi: SignupApi
) : SignupRepository {
    override suspend fun authenticateWithPhoneNum(body: PhoneAuthBody): PhoneAuthDto {
        val response = signupApi.requestPhoneAuth(body)
        Logger.log("response code ${response.code()} / body ${response.body()}")
        Logger.log("response $response")
        return response.body()!!
    }


}
