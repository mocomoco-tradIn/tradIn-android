package com.mocomoco.tradin.data.data.repository

import com.mocomoco.tradin.data.data.dto.response.search.HotSearchKeywordDto
import com.mocomoco.tradin.data.data.dto.response.search.RelatedKeywordsDto
import com.mocomoco.tradin.data.data.dto.response.search.SearchResultsDto

interface SearchRepository {
    suspend fun getHotSearchKeywords(): HotSearchKeywordDto
    suspend fun getRelatedSearchKeywords(keyword: String): RelatedKeywordsDto
    suspend fun getSearchResult(
        keyword: String,
        sorted: Int,
        lastId: Int,
    ): SearchResultsDto
}