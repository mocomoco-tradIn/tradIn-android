package com.mocomoco.tradin.data.data.repository

import com.mocomoco.tradin.data.data.dto.request_body.EmailDuplicateBody
import com.mocomoco.tradin.data.data.dto.request_body.AuthCoincideBody
import com.mocomoco.tradin.data.data.dto.request_body.TelBody
import com.mocomoco.tradin.data.data.dto.response.PhoneAuthDto

interface SignupRepository {
    suspend fun postTelAuth(body: TelBody): PhoneAuthDto
    suspend fun putAuthCoincide(body: AuthCoincideBody)
    suspend fun postEmailDuplicate(body: EmailDuplicateBody)

}