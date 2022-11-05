package com.mocomoco.tradin.data.data.dto.request_body

data class PutFeedBody(
    val categoryId: Int,
    val content: String,
    val feedId: Int,
    val images: List<String>,
    val regionCode: String,
    val title: String,
    val tradeMethodId: Int
)