package com.mocomoco.tradin.model

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

data class Details(
    val feedId: Int = 0,
    val category: String = "",
    val imageUrls: List<String> = listOf(),
    val title: String = "",
    val profileImage: String = "",
    val profileName: String = "",
    val isLike: Boolean = false,
    val likeCount: Int = 0,
    val tradeCount: Int = 0,
    val chatCount: Int = 0,
    val viewCount: Int = 0,
    val createdAt: String = "",
    val body: String = "",
    val location: String = "",
    val tradeMethod: TradeMethod = TradeMethod.None,
    val status: FeedStatus = FeedStatus.NONE,
    val userId: Int = 0
)
