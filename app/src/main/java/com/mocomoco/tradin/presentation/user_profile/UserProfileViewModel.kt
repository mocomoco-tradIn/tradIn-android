package com.mocomoco.tradin.presentation.user_profile

import androidx.lifecycle.viewModelScope
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.data.data.dto.request_body.FeedIdBody
import com.mocomoco.tradin.data.data.repository.FeedRepository
import com.mocomoco.tradin.data.data.repository.TradeRepository
import com.mocomoco.tradin.mapper.mapToFeed
import com.mocomoco.tradin.mapper.mapToTrade
import com.mocomoco.tradin.model.Feed
import com.mocomoco.tradin.model.Location
import com.mocomoco.tradin.model.Trade
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
    private val tradeRepository: TradeRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow(UserProfileState())
    val state: StateFlow<UserProfileState> = _state

    private val _successLikeEvent = MutableSharedFlow<Boolean>()
    val successLikeEvent = _successLikeEvent.asSharedFlow()

    val ceh = CoroutineExceptionHandler { _, exception ->
        viewModelScope.launch {
            _toastMessage.emit("에러발생 e:$exception")
        }
        _loading.value = false
    }

    fun load(userId: Int) = viewModelScope.launch(ceh + Dispatchers.IO) {
        _loading.value = true
        val feeds = feedRepository.getUserFeeds(userId, 0).map { it.mapToFeed() }
        val trades = tradeRepository.getUserTradeHistory(userId, 0).trades?.map { it.mapToTrade() } ?: listOf()

        _state.value = state.value.copy(
            feeds = feeds,
            trades = trades
        )

        _loading.value = false
    }

    fun like(feedsId: Int) = viewModelScope.launch(ceh + Dispatchers.IO) {
        _loading.value = true
        val isLike = feedRepository.postLikeFeed(FeedIdBody(feedsId)).isLikes
        _state.value = state.value.copy(
            feeds = state.value.feeds.map {
                if (it.id == feedsId) {
                    it.copy(
                        isLiked = isLike
                    )
                } else {
                    it
                }
            }
        )
        _loading.value = false
    }
}


data class UserProfileState(
    val userName: String = "",
    val userRegion: Location = Location(),
    val avatar: String = "",
    val interests: String = "",
    val feeds: List<Feed> = listOf(),
    val trades: List<Trade> = listOf()
)

