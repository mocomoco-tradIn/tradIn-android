package com.mocomoco.tradin.data.data.dto.response.feeds

data class LikedFeedsDto(
    val feeds: List<FeedDto>?,
    val lastId: Int,
    val size: Int
)