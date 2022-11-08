package com.mocomoco.tradin.data.data.dto.response.feeds

data class HomeFeedsDto(
    val category: String,
    val feeds: List<FeedDto>?,
    val lastId: Int,
    val region: String,
    val size: Int,
    val sorted: String,
    val totalCount: Int
)