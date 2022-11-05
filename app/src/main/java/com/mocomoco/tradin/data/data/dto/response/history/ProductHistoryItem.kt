package com.mocomoco.tradin.data.data.dto.response.history

data class ProductHistoryItem(
    val category: String,
    val createdAt: String,
    val image: String,
    val productId: Int,
    val title: String
)