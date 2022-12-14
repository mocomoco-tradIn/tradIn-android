package com.mocomoco.tradin.data.data.repository

import com.mocomoco.tradin.data.data.dto.request_body.*
import com.mocomoco.tradin.data.data.dto.request_body.NicknameDuplicateBody
import com.mocomoco.tradin.data.data.dto.response.PhoneAuthDto
import com.mocomoco.tradin.data.data.dto.response.login.SignInDto

interface AuthRepository {
    suspend fun postTelAuth(body: TelBody): PhoneAuthDto
    suspend fun putAuthCoincide(body: AuthCoincideBody)
    suspend fun postEmailDuplicate(body: EmailDuplicateBody)
    suspend fun postNicknameDuplicate(body: NicknameDuplicateBody)
    suspend fun postSignup(body: SignupBody)
    suspend fun postSignIn(body: SignInBody): SignInDto
}