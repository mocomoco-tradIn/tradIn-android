package com.mocomoco.tradin.data.data.resource.remote.apis

import com.mocomoco.tradin.data.common.Constants.PAGE_SIZE
import com.mocomoco.tradin.data.data.dto.request_body.FeedIdBody
import com.mocomoco.tradin.data.data.dto.request_body.PutFeedBody
import com.mocomoco.tradin.data.data.dto.request_body.ReportFeedBody
import com.mocomoco.tradin.data.data.dto.request_body.UploadFeedBody
import com.mocomoco.tradin.data.data.dto.response.feeds.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedApi {
    @GET("/api/v1/feed")
    suspend fun getHomeFeeds(
        @Query("region") region: String? = null,
        @Query("sorted") sorted: Int? = null,
        @Query("category") category: Int? = null,
        @Query("lastId") lastId: Int? = null,
        @Query("size") size: Int? = null
    ): Response<HomeFeedsDto>

    @GET("/api/v1/feed/me")
    suspend fun getMyFeeds(
        @Query("lastId") lastId: Int,
        @Query("size") size: Int = PAGE_SIZE
    ): Response<FeedsDto>

    @GET("/api/v1/feed/user/{id}")
    suspend fun getUserFeeds(
        @Path("id") id: Int,
        @Query("lastId") lastId: Int,
        @Query("size") size: Int = PAGE_SIZE
    ): Response<FeedsDto>

    @GET("/api/v1/feed/{id}")
    suspend fun getFeedDetails(
        @Path("id") id: Int
    ): Response<FeedDetailsDto>

    @POST("/api/v1/feed")
    suspend fun postUploadFeed(
        @Body body: UploadFeedBody
    ): Response<FeedIdDto>

    @PUT("/api/v1/feed")
    suspend fun putFeed(
        @Body body: PutFeedBody
    ): Response<FeedIdDto>

    @DELETE("/api/v1/feed/{id}")
    suspend fun deleteFeed(
        @Path("id") id: Int
    ): Response<Unit>

    @POST("/api/v1/feed/report")
    suspend fun postReportFeed(
        @Body body: ReportFeedBody
    ): Response<Unit>

    @POST("/api/v1/feed/likes")
    suspend fun postLikeFeed(
        @Body body: FeedIdBody
    ): Response<FeedLikeDto>

    @GET("/api/v1/feed/likes")
    suspend fun getLikedFeed(
        @Query("lastId") lastId: Int,
        @Query("size") size: Int = PAGE_SIZE
    ): Response<LikedFeedsDto>

    @GET("/api/v1/feed/report")
    suspend fun getReportReasons():  Response<ReportReasonsDto>
}

/*
- region: ???????????? (ex. 10110)
- sorted: ?????? ?????? (default: 0)
    - 0: ?????????
    - 1: ?????????
    - 2: ?????????
- category: ???????????? (dafault: 0)
    - 0: ??????
    - 1: ??????
    - 2: ??????/??????
    - 3: ??????
    - 4: ????????????
    - 5: ??????
    - 6: ????????? ??????
    - 7: ??????/?????????
    - 8: ??????
- lastId : ????????? ????????? feedId (default: 0)
- size: ??? (default: 30)
 */