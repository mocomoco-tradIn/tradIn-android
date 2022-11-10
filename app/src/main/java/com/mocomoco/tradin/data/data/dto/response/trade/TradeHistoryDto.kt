package com.mocomoco.tradin.data.data.dto.response.trade

data class TradeHistoryDto(
    val lastId: Int,
    val progress: List<Progres>,
    val size: Int,
    val trades: List<TradeDto>
)