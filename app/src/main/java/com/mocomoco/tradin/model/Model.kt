package com.mocomoco.tradin.model

import com.mocomoco.tradin.data.data.dto.response.feeds.FeedDto


data class Location(
    val code: String = "11680",
    val display: String = ""
)

data class Feed(
    val id: Int = -1,
    val imgUrl: String = "",
    val isLiked: Boolean = false,
    val title: String = "",
    val location: String = "",
    val nickname: String = "",
    val likeCount: Int = 0,
    val status: FeedStatus = FeedStatus.NONE,
    val createdAt: String = ""
) {
    val invisible = id == -1
}

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


data class User(
    val avatar: String = "",
    val category: List<String> = listOf(),
    val email: String = "",
    val nickname: String = "",
    val regionCode: String = "",
    val regionName: String = "",
    val tel: String = "",
    val userId: Int
)