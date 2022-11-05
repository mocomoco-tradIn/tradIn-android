package com.mocomoco.tradin.data.data.repository.impl

import com.mocomoco.tradin.data.common.handleResponse
import com.mocomoco.tradin.data.data.dto.request_body.FeedIdBody
import com.mocomoco.tradin.data.data.dto.request_body.OfferProductBody
import com.mocomoco.tradin.data.data.dto.request_body.TradeIdBody
import com.mocomoco.tradin.data.data.dto.response.trade.*
import com.mocomoco.tradin.data.data.repository.TradeRepository
import com.mocomoco.tradin.data.data.resource.remote.apis.TradeApi
import javax.inject.Inject

class TradeRepositoryImpl @Inject constructor(
    private val tradeApi: TradeApi
) : TradeRepository {
    override suspend fun postTradeRoom(body: FeedIdBody): TradeIdDto {
        return handleResponse(tradeApi.postTradeRoom(body))
    }

    override suspend fun postOfferProduct(id: Int, body: OfferProductBody): OfferProductDto {
        return handleResponse(tradeApi.postOfferProduct(id, body))
    }

    override suspend fun postAcceptProduct(id: Int, body: TradeIdBody): TradeAcceptDto {
        return handleResponse(tradeApi.postAcceptProduct(id, body))
    }

    override suspend fun postTerminateTrade(id: Int, body: TradeIdBody) {
        return handleResponse(tradeApi.postTerminateTrade(id, body))
    }

    override suspend fun postCancelTrade(id: Int, body: TradeIdBody) {
        return handleResponse(tradeApi.postCancelTrade(id, body))
    }

    override suspend fun getTradeDetails(id: Int): TradeDetailsDto {
        return handleResponse(tradeApi.getTradeDetails(id))
    }

    override suspend fun getMyTradeHistory(lastId: Int): TradeHistoryDto {
        return handleResponse(tradeApi.getMyTradeHistory(lastId))
    }

    override suspend fun getUserTradeHistory(id: Int, lastId: Int): TradeHistoryDto {
        return handleResponse(tradeApi.getUserTradeHistory(id, lastId))
    }

    override suspend fun postProductTradeHistory(id: Int): ProductTradeHistoryDto {
        return handleResponse(tradeApi.postProductTradeHistory(id))
    }
}