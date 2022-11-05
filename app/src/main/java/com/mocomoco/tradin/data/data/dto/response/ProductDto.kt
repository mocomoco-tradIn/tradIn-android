package com.mocomoco.tradin.data.data.dto.response

data class ProductDto(
    val categoryId: Int,
    val categoryName: String,
    val content: String,
    val createdAt: String,
    val images: List<String>,
    val productId: Int,
    val regionCode: String,
    val regionName: String,
    val title: String,
    val tradeMethod: String,
    val tradeMethodId: Int
)