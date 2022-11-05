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
- region: 지역코드 (ex. 10110)
- sorted: 정렬 방법 (default: 0)
    - 0: 인기순
    - 1: 최신순
    - 2: 조회순
- category: 카테고리 (dafault: 0)
    - 0: 전체
    - 1: 의류
    - 2: 서적/문구
    - 3: 취미
    - 4: 전자제품
    - 5: 잡화
    - 6: 아이돌 굿즈
    - 7: 티켓/교환권
    - 8: 기타
- lastId : 마지막 피드의 feedId (default: 0)
- size: 수 (default: 30)
 */