package com.mocomoco.tradin.data.common

object Constants {
    const val BASE_URL_DEV = "https://dev.tradin.shop/"
    const val BASE_URL_PROD = "https://tradin.shop/"

    const val PAGE_SIZE = 20

    const val BASE_SOCKET_URL_DEV =  "wss://dev.tradin.shop/ws"
    const val BASE_SOCKET_URL_PROD =  "wss://tradin.shop/ws"

}

fun setDevSocketUserId(userId: Int): String {
    return "${Constants.BASE_SOCKET_URL_DEV}?uid={$userId}"
}

fun setProdSocketUserId(userId: Int): String {
    return "${Constants.BASE_SOCKET_URL_PROD}?uid={$userId}"
}