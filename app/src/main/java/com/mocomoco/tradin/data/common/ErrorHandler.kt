package com.mocomoco.tradin.data.common

import com.google.gson.Gson
import com.mocomoco.tradin.common.*
import com.mocomoco.tradin.data.data.dto.response.ErrorDto
import retrofit2.Response

fun <T> handleResponse(response: Response<T>): T {
    Logger.log("response $response / body ${response.body()}")
    response.errorBody()?.let {
        val json = it.string()
        val errorDto = Gson().fromJson(json, ErrorDto::class.java)
        handleResponseCode(errorDto)
    }
    return response.body()!!
}

fun handleResponseCode(errorDto: ErrorDto) {
    Logger.log("error $errorDto")
    when (errorDto.code) {
        "A001" -> throw InvalidTelException(errorDto.message)
        "A005" -> throw NotMatchedAuthNumberException(errorDto.message)
        "A007" -> throw DuplicateEmailException(errorDto.message)
        else -> throw ServerException(errorDto.message)
    }
}