package com.mocomoco.tradin.data.data.repository

import com.mocomoco.tradin.data.data.dto.request_body.FeedIdBody
import com.mocomoco.tradin.data.data.dto.request_body.OfferProductBody
import com.mocomoco.tradin.data.data.dto.request_body.TradeIdBody
import com.mocomoco.tradin.data.data.dto.response.trade.*

interface TradeRepository {
    suspend fun postTradeRoom(body: FeedIdBody): TradeIdDto

    suspend fun postOfferProduct(id: Int, body: OfferProductBody): OfferProductDto

    suspend fun postAcceptProduct(id: Int, body: TradeIdBody): TradeAcceptDto

    suspend fun postTerminateTrade(id: Int, body: TradeIdBody)

    suspend fun postCancelTrade(id: Int, body: TradeIdBody)

    suspend fun getTradeDetails(id: Int): TradeDetailsDto

    suspend fun getMyTradeHistory(lastId: Int): TradeHistoryDto

    suspend fun getUserTradeHistory(id: Int, lastId: Int): TradeHistoryDto

    suspend fun postProductTradeHistory(id: Int): ProductTradeHistoryDto
}
