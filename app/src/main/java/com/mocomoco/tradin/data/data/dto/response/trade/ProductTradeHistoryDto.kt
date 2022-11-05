package com.mocomoco.tradin.data.data.dto.response.trade

data class ProductTradeHistoryDto(
    val products: List<Product>,
    val title: String,
    val total: Int
)