package com.mocomoco.tradin.presentation.main.home

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.data.data.dto.request_body.FeedIdBody
import com.mocomoco.tradin.data.data.repository.FeedRepository
import com.mocomoco.tradin.model.Category
import com.mocomoco.tradin.model.Feed
import com.mocomoco.tradin.model.Location
import com.mocomoco.tradin.model.SortType
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
            feedRepository.getHomeFeeds(
                region = location?.code,
                sorted = sortType.code,
                category = category?.code,
                lastId = lastId
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
    val location: Location = Location(),
    val sortType: SortType = SortType.POPULAR
) {
    data class ListState(
        val isLoading: Boolean
    )

    data class Event(
        val onChangeSortType: (SortType) -> Unit,
        val onChangeLocation: (Location) -> Unit,
        val onClickLike: (id: Int, isLike: Boolean) -> Unit
    )
}

