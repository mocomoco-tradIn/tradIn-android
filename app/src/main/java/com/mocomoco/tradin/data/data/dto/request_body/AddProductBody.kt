package com.mocomoco.tradin.data.data.dto.request_body

data class AddProductBody(
    val categoryId: Int,
    val content: String,
    val images: List<String>,
    val regionCode: String,
    val title: String,
    val tradeMethodId: Int
)