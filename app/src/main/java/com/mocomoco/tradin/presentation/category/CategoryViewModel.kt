package com.mocomoco.tradin.presentation.category

import androidx.lifecycle.viewModelScope
import com.mocomoco.tradin.base.BaseViewModel
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
class CategoryViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
    private val preferenceService: PreferenceService,
) : BaseViewModel() {

    private val _state = MutableStateFlow(CategoryState())
    val state: StateFlow<CategoryState> = _state

    private val _successLikeEvent = MutableSharedFlow<Boolean>()
    val successLikeEvent = _successLikeEvent.asSharedFlow()

    fun load(categoryId: Int, sortType: SortType = SortType.POPULAR, lastId: Int = 0) =
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val dto = feedRepository.getHomeFeeds(
                    region = preferenceService.getLocationCode(),
                    sorted = sortType.code,
                    category = categoryId,
                    lastId = lastId
                )

                _state.value = state.value.copy(
                    title = dto.category,
                    feedCount = dto.totalCount,
                    sortType = sortType,
                    feeds = dto.feeds.map { mapToFeed(it) }
                )
            } catch (e: Exception) {

            } finally {
                _loading.value = false
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


    fun showSortDialog(show: Boolean) {
        _state.value = state.value.copy(
            showSortDialog = show
        )
    }

}

data class CategoryState(
    val title: String = "",
    val feedCount: Int = 0,
    val sortType: SortType = SortType.POPULAR,
    val feeds: List<Feed> = listOf(),
    val showSortDialog: Boolean = false,
)