package com.mocomoco.tradin.data.data.repository.impl

import com.mocomoco.tradin.data.common.handleResponse
import com.mocomoco.tradin.data.data.dto.request_body.FeedIdBody
import com.mocomoco.tradin.data.data.dto.request_body.PutFeedBody
import com.mocomoco.tradin.data.data.dto.request_body.ReportFeedBody
import com.mocomoco.tradin.data.data.dto.request_body.UploadFeedBody
import com.mocomoco.tradin.data.data.dto.response.feeds.*
import com.mocomoco.tradin.data.data.repository.FeedRepository
import com.mocomoco.tradin.data.data.resource.remote.apis.FeedApi
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    private val feedApi: FeedApi
) : FeedRepository {
    override suspend fun getHomeFeeds(
        region: String?,
        sorted: Int?,
        category: Int?,
        lastId: Int?
    ): HomeFeedsDto {
        return handleResponse(feedApi.getHomeFeeds(region, sorted, category, lastId))
    }

    override suspend fun getMyFeeds(lastId: Int): FeedsDto {
        return handleResponse(feedApi.getMyFeeds(lastId))
    }

    override suspend fun getUserFeeds(id: Int, lastId: Int): FeedsDto {
        return handleResponse(feedApi.getUserFeeds(id, lastId))
    }

    override suspend fun getFeedDetails(id: Int): FeedDetailsDto {
        return handleResponse(feedApi.getFeedDetails(id))
    }

    override suspend fun postUploadFeed(body: UploadFeedBody): FeedIdDto {
        return handleResponse(feedApi.postUploadFeed(body))
    }

    override suspend fun putFeed(body: PutFeedBody): FeedIdDto {
        return handleResponse(feedApi.putFeed(body))
    }

    override suspend fun deleteFeed(id: Int) {
        return handleResponse(feedApi.deleteFeed(id))
    }

    override suspend fun postReportFeed(body: ReportFeedBody) {
        return handleResponse(feedApi.postReportFeed(body))
    }

    override suspend fun postLikeFeed(body: FeedIdBody): FeedLikeDto {
        return handleResponse(feedApi.postLikeFeed(body))
    }

    override suspend fun getLikedFeed(lastId: Int): LikedFeedsDto {
        return handleResponse(feedApi.getLikedFeed(lastId))
    }

    override suspend fun getReportReasons(): ReportReasonsDto {
        return handleResponse(feedApi.getReportReasons())
    }
}