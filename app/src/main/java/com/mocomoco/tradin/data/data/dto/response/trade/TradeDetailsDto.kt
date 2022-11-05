package com.mocomoco.tradin.data.data.dto.response.trade

data class TradeDetailsDto(
    val myNickname: String,
    val myProductId: Int,
    val myProductImage: String,
    val myProductTitle: String,
    val myUserId: Int,
    val otherNickname: String,
    val otherProductId: Int,
    val otherProductImage: String,
    val otherProductTitle: String,
    val otherUserId: Int,
    val status: String,
    val tradeDate: String,
    val tradeId: Int,
    val tradeMethod: String
)