package com.mocomoco.tradin.data.data.dto.response.trade

data class Trade(
    val myProductImage: String,
    val otherNickname: String,
    val otherProductImage: String,
    val otherUserId: Int,
    val status: String,
    val tradeDate: String,
    val tradeId: Int
)