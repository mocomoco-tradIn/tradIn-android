package com.mocomoco.tradin.data.data.dto.response.trade

data class TradeAcceptDto(
    val comsumerCheck: Boolean,
    val comsumerNickname: String,
    val comsumerProductId: Int,
    val comsumerProductImage: String,
    val comsumerProductTitle: String,
    val comsumerUserId: Int,
    val isOwner: Boolean,
    val ownerCheck: Boolean,
    val ownerNickname: String,
    val ownerProductId: Int,
    val ownerProductImage: String,
    val ownerProductTitle: String,
    val ownerUserId: Int,
    val status: String
)