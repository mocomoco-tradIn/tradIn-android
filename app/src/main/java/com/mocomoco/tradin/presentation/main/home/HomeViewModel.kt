package com.mocomoco.tradin.presentation.main.home

import androidx.lifecycle.viewModelScope
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.data.data.dto.request_body.FeedIdBody
import com.mocomoco.tradin.data.data.repository.FeedRepository
import com.mocomoco.tradin.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
) : BaseViewModel() {
    val a = "asdf"

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    private val _successLikeEvent = MutableSharedFlow<Boolean>()
    val successLikeEvent = _successLikeEvent.asSharedFlow()


    suspend fun load(
        sortType: SortType = SortType.POPULAR,
        location: Location? = null,
        category: Category? = null,
        lastId: Int = 0
    ) = viewModelScope.launch(Dispatchers.IO) {
        _state.value = state.value.copy(isFeedLoading = true)
        try {
            val dto = feedRepository.getHomeFeeds(
                region = location?.code,
                sorted = sortType.code,
                category = category?.code,
                lastId = lastId
            )

            _state.value = state.value.copy(
                feeds = state.value.feeds.toMutableList().apply {
                    addAll(dto.feeds.map { mapToFeed(it) })
                },
                location = dto.region,
                sortType = when (dto.sorted) {
                    SortType.POPULAR.display -> SortType.POPULAR
                    SortType.LATEST.display -> SortType.LATEST
                    else -> SortType.VIEW
                }
            )
        } catch (e: Exception) {

        } finally {
            _state.value = state.value.copy(isFeedLoading = false)
        }
    }

    fun like(id: Int) = viewModelScope.launch {
        _loading.value = true
        try {
            feedRepository.postLikeFeed(FeedIdBody(feedId = id))
        } catch (e: Exception) {

        } finally {
            _loading.value = false
        }
    }

}


data class HomeState(
    val feeds: List<Feed> = listOf(),
    val isFeedLoading: Boolean = false,
    val location: String = "",
    val sortType: SortType = SortType.POPULAR,
) {
    data class ListState(
        val isLoading: Boolean
    )
}

