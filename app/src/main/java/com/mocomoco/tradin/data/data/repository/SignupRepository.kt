package com.mocomoco.tradin.data.data.repository

import com.mocomoco.tradin.data.data.dto.request_body.PhoneAuthBody
import com.mocomoco.tradin.data.data.dto.response.PhoneAuthDto

interface SignupRepository {
    suspend fun authenticateWithPhoneNum(body: PhoneAuthBody): PhoneAuthDto
}