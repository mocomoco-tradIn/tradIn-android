package com.mocomoco.tradin.presentation.signup

sealed class Signup(open val phase: Int) {
    data class PolicyAgree(override val phase: Int = 0) : Signup(phase)
    data class SelfAuthentication(override val phase: Int = 1) : Signup(phase)
    data class LoginInfo(override val phase: Int = 2) : Signup(phase)
    data class UserInfo(override val phase: Int = 3) : Signup(phase)
    data class Complete(override val phase: Int = 4) : Signup(phase)
}