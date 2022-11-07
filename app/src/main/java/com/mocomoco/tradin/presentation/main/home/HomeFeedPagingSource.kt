package com.mocomoco.tradin.presentation.main.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mocomoco.tradin.data.common.Constants.PAGE_SIZE
import com.mocomoco.tradin.data.data.dto.response.feeds.FeedDto
import com.mocomoco.tradin.data.data.repository.FeedRepository
import com.mocomoco.tradin.model.Feed
import com.mocomoco.tradin.model.mapToFeed
import javax.inject.Inject

class HomeFeedPagingSource(
    private val feedRepository: FeedRepository,
    private val region: String? = null,
    private val sorted: Int? = null,
    private val category: Int? = null,
) : PagingSource<Int, Feed>() {
    //private var prevId: Int? = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Feed> {
        return try {
            val lastId = params.key ?: 0
            val dto = feedRepository.getHomeFeeds(
                region = region,
                sorted = sorted,
                category = category,
                lastId = lastId
            )

            //prevId = lastId

            val nextKey = if (dto.feeds.size < PAGE_SIZE) null else dto.lastId

            LoadResult.Page(
                data = dto.feeds.map { mapToFeed(it) },
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Feed>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey ?: state.closestPageToPosition(it)?.nextKey
            ?: 0
        } // todo
    }
}