package com.mocomoco.tradin.data.data.resource.remote.apis

import com.mocomoco.tradin.data.common.Constants
import com.mocomoco.tradin.data.data.dto.response.search.HotSearchKeywordDto
import com.mocomoco.tradin.data.data.dto.response.search.RelatedKeywordsDto
import com.mocomoco.tradin.data.data.dto.response.search.SearchResultsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("/api/v1/search/hot")
    suspend fun getHotSearchKeywords():Response<HotSearchKeywordDto>

    @GET("/api/v1/search/related")
    suspend fun getRelatedSearchKeywords(
        @Query("keyword") keyword: String
    ): Response<RelatedKeywordsDto>

    @GET("/api/v1/search")
    suspend fun getSearchResult(
        @Query("keyword") keyword: String,
        @Query("sorted") sorted: Int,
        @Query("lastId") lastId: Int,
        @Query("size") size: Int = Constants.PAGE_SIZE
    ): Response<SearchResultsDto>
}