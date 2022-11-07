package com.mocomoco.tradin.model

import com.mocomoco.tradin.data.data.dto.response.feeds.FeedDto


data class Location(
    val code: String = "11680",
    val display: String = ""
)

data class Feed(
    val id: Int,
    val imgUrl: String,
    val isLiked: Boolean,
    var title: String,
    val location: String,
    val nickname: String,
    val likeCount: Int,
    val status: FeedStatus,
    val createdAt: String
)
fun mapToFeed(dto: FeedDto): Feed = Feed(
    id = dto.feedId,
    imgUrl = dto.image,
    isLiked = dto.isLikes,
    title = dto.title,
    location = dto.region,
    nickname = dto.nickname,
    likeCount = dto.likesCount,
    status = when (dto.status) {
        FeedStatus.PROGRESS.code -> FeedStatus.PROGRESS
        FeedStatus.WAIT.code -> FeedStatus.WAIT
        else -> FeedStatus.NONE
    },
    createdAt = dto.createdAt
)