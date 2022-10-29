package com.mocomoco.tradin.util

object LoginRegex {
    private const val emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
    private const val telRegex = "^010\\d{8}"
    private const val pwRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[\$@\$!%*#?&])[A-Za-z\\d\$@\$!%*#?&]{8,}\$"

    fun String.checkEmailForm(): Boolean = matches(Regex(emailRegex))

    fun String.checkTelForm(): Boolean = matches(Regex(telRegex))

    fun String.checkPwForm(): Boolean = matches(Regex(pwRegex))
}