package com.mocomoco.tradin.data.data.dto.request_body

data class UploadFeedBody(
    val categoryId: Int,
    val content: String,
    val images: List<String>,
    val productId: Int,
    val regionCode: String,
    val title: String,
    val tradeMethodId: Int
)