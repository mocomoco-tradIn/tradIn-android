package com.mocomoco.tradin.data.data.dto.response.feeds

data class FeedDetailsDto(
    val category: String,
    val chatting: Int,
    val content: String,
    val createdAt: String,
    val feedId: Int,
    val hit: Int,
    val images: List<String>,
    val likes: Int,
    val nickname: String,
    val region: String,
    val status: String,
    val title: String,
    val tradeMethod: String,
    val tradeMethodDescription: String,
    val trades: Int,
    val userId: Int
)