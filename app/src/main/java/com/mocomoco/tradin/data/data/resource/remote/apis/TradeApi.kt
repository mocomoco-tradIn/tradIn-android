package com.mocomoco.tradin.data.data.resource.remote.apis

import com.mocomoco.tradin.data.common.Constants
import com.mocomoco.tradin.data.data.dto.request_body.FeedIdBody
import com.mocomoco.tradin.data.data.dto.request_body.OfferProductBody
import com.mocomoco.tradin.data.data.dto.request_body.TradeIdBody
import com.mocomoco.tradin.data.data.dto.response.trade.*
import retrofit2.Response
import retrofit2.http.*

interface TradeApi {
    @POST("/api/v1/tarde") // 채팅방 이동(생성)하기
    suspend fun postTradeRoom(
        @Body body: FeedIdBody
    ): Response<TradeIdDto>

    @POST("/api/v1/trade/{id}/offer") // 교환 물품 올리기
    suspend fun postOfferProduct(
        @Path("id") id: Int,
        @Body body: OfferProductBody
    ): Response<OfferProductDto>

    @POST("/api/v1/trade/{id}/accept") // 교환 물품 확인(체크)하기
    suspend fun postAcceptProduct(
        @Path("id") id: Int,
        @Body body: TradeIdBody
    ): Response<TradeAcceptDto>

    @POST("/api/v1/trade/{id}/terminate") // 교환 완료(종료)하기
    suspend fun postTerminateTrade(
        @Path("id") id: Int,
        @Body body: TradeIdBody
    ): Response<Unit>

    @POST("/api/v1/trade/{id}/cancel") // 교환 취소(예약 취소) 하기
    suspend fun postCancelTrade(
        @Path("id") id: Int,
        @Body body: TradeIdBody
    ): Response<Unit>

    @GET("/api/v1/trade/{id}") // 교환내역 상세보기
    suspend fun getTradeDetails(
        @Path("id") id: Int
    ): Response<TradeDetailsDto>

    @GET("/api/v1/trade/user/me") //  내 교환내역 보기
    suspend fun getMyTradeHistory(
        @Query("lastId") lastId: Int,
        @Query("size") size: Int = Constants.PAGE_SIZE
    ): Response<TradeHistoryDto>

    @GET("/api/v1/trade/user/{id}") // 타 유저 교환내역 보기
    suspend fun getUserTradeHistory(
        @Path("id") id: Int,
        @Query("lastId") lastId: Int,
        @Query("size") size: Int = Constants.PAGE_SIZE
    ): Response<TradeHistoryDto>

    @POST("/api/v1/trade/product/{id}") // todo
    suspend fun postProductTradeHistory(
        @Path("id") id: Int
    ): Response<ProductTradeHistoryDto>
}