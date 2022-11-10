package com.mocomoco.tradin.mapper

import com.mocomoco.tradin.data.data.dto.response.feeds.FeedDetailsDto
import com.mocomoco.tradin.data.data.dto.response.feeds.FeedDto
import com.mocomoco.tradin.model.Details
import com.mocomoco.tradin.model.Feed
import com.mocomoco.tradin.model.FeedStatus
import com.mocomoco.tradin.model.TradeMethod


fun FeedDto.mapToFeed(): Feed = Feed(
    id = this.feedId,
    imgUrl = this.image,
    isLiked = this.isLikes,
    title = this.title,
    location = this.region,
    nickname = this.nickname,
    likeCount = this.likesCount,
    status = when (this.status) {
        FeedStatus.PROGRESS.code -> FeedStatus.PROGRESS
        FeedStatus.WAIT.code -> FeedStatus.WAIT
        else -> FeedStatus.NONE
    },
    createdAt = this.createdAt
)

fun FeedDetailsDto.mapToDetails(): Details = Details(
    feedId = this.feedId,
    imageUrls = this.images ?: listOf(),
    title = this.title,
    profileImage = this.avatar,
    profileName = this.nickname,
    isLike = this.isLikes,
    likeCount = this.likes,
    tradeCount = this.trades,
    chatCount = this.chatting,
    viewCount = this.hit,
    createdAt = this.createdAt,
    body = this.content,
    location = this.region,
    tradeMethod = when (this.tradeMethod) {
        TradeMethod.Etc.id -> TradeMethod.Etc
        TradeMethod.Parcel.id -> TradeMethod.Parcel
        TradeMethod.Direct.id -> TradeMethod.Direct
        else -> TradeMethod.None
    },
    status = when (this.status) {
        FeedStatus.WAIT.code -> FeedStatus.WAIT
        FeedStatus.PROGRESS.code -> FeedStatus.PROGRESS
        else -> FeedStatus.NONE
    },
    category = this.category,
    userId = this.userId
)