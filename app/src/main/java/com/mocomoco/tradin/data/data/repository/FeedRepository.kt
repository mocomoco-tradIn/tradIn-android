package com.mocomoco.tradin.data.data.repository

import com.mocomoco.tradin.data.data.dto.request_body.FeedIdBody
import com.mocomoco.tradin.data.data.dto.request_body.PutFeedBody
import com.mocomoco.tradin.data.data.dto.request_body.ReportFeedBody
import com.mocomoco.tradin.data.data.dto.request_body.UploadFeedBody
import com.mocomoco.tradin.data.data.dto.response.feeds.*

interface FeedRepository {
    suspend fun getHomeFeeds(
        region: String? = null,
        sorted: Int? = null,
        category: Int? = null,
        lastId: Int? = null
    ): HomeFeedsDto

    suspend fun getMyFeeds(lastId: Int): FeedsDto

    suspend fun getUserFeeds(id: Int, lastId: Int): FeedsDto

    suspend fun getFeedDetails(id: Int): FeedDetailsDto

    suspend fun postUploadFeed(body: UploadFeedBody): FeedIdDto

    suspend fun putFeed(body: PutFeedBody): FeedIdDto

    suspend fun deleteFeed(id: Int)

    suspend fun postReportFeed(body: ReportFeedBody)

    suspend fun postLikeFeed(body: FeedIdBody): FeedLikeDto

    suspend fun getLikedFeed(lastId: Int): LikedFeedsDto

    suspend fun getReportReasons(): ReportReasonsDto
}