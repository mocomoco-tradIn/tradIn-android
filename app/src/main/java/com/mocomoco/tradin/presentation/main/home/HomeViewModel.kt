package com.mocomoco.tradin.presentation.main.home

import androidx.lifecycle.viewModelScope
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.data.data.dto.request_body.FeedIdBody
import com.mocomoco.tradin.data.data.repository.FeedRepository
import com.mocomoco.tradin.data.data.resource.local.PreferenceService
import com.mocomoco.tradin.model.Feed
import com.mocomoco.tradin.model.SortType
import com.mocomoco.tradin.model.mapToFeed
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
    private val preferenceService: PreferenceService
) : BaseViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    private val _successLikeEvent = MutableSharedFlow<Boolean>()
    val successLikeEvent = _successLikeEvent.asSharedFlow()

    fun load(
        sortType: SortType? = null,
        lastId: Int = 0
    ) = viewModelScope.launch(Dispatchers.IO) {
        _loading.value = true
        _state.value = state.value.copy(isFeedLoading = true)
        try {
            Logger.log("${preferenceService.getLocationCode()}")
            val dto = feedRepository.getHomeFeeds(
                region = preferenceService.getLocationCode(),
                sorted = sortType?.code ?: state.value.sortType.code,
                lastId = lastId
            )

            _state.value = state.value.copy(
                feeds = dto.feeds?.map { mapToFeed(it) } ?: listOf(),
                location = dto.region,
                sortType = when (dto.sorted) {
                    SortType.POPULAR.display -> SortType.POPULAR
                    SortType.LATEST.display -> SortType.LATEST
                    else -> SortType.VIEW
                },
            )
        } catch (e: Exception) {
            _toastMessage.emit("오류 발생 e:${e.message}")
        } finally {
            _loading.value = false
            _state.value = state.value.copy(isFeedLoading = false)
        }
    }

    fun like(id: Int) = viewModelScope.launch {
        _loading.value = true
        try {
            var like = true
            feedRepository.postLikeFeed(FeedIdBody(feedId = id))
            _state.value = state.value.copy(
                feeds = state.value.feeds.map {
                    if (it.id == id) {
                        like = !it.isLiked
                        it.copy(
                            isLiked = !it.isLiked
                        )
                    } else {
                        it
                    }
                }
            )
            _successLikeEvent.emit(like)
        } catch (e: Exception) {
            _toastMessage.emit("오류 발생 e:${e.message}")
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
)

