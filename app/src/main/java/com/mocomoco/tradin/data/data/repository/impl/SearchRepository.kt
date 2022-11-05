package com.mocomoco.tradin.data.data.repository.impl

import com.mocomoco.tradin.data.common.handleResponse
import com.mocomoco.tradin.data.data.dto.response.search.HotSearchKeywordDto
import com.mocomoco.tradin.data.data.dto.response.search.RelatedKeywordsDto
import com.mocomoco.tradin.data.data.dto.response.search.SearchResultsDto
import com.mocomoco.tradin.data.data.repository.SearchRepository
import com.mocomoco.tradin.data.data.resource.remote.apis.SearchApi
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : SearchRepository {
    override suspend fun getHotSearchKeywords(): HotSearchKeywordDto {
        return handleResponse(searchApi.getHotSearchKeywords())
    }

    override suspend fun getRelatedSearchKeywords(keyword: String): RelatedKeywordsDto {
        return handleResponse(searchApi.getRelatedSearchKeywords(keyword))
    }

    override suspend fun getSearchResult(
        keyword: String,
        sorted: Int,
        lastId: Int
    ): SearchResultsDto {
        return handleResponse(searchApi.getSearchResult(keyword, sorted, lastId))
    }
}