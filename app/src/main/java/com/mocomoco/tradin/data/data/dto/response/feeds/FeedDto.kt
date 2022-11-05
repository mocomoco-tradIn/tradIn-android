package com.mocomoco.tradin.data.data.dto.response.feeds

data class FeedDto(
    val createdAt: String,
    val feedId: Int,
    val image: String,
    val isLikes: Boolean,
    val likesCount: Int,
    val nickname: String,
    val region: String,
    val status: String,
    val title: String
)
