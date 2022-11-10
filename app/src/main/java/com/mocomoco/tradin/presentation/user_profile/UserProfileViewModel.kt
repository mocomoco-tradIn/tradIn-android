package com.mocomoco.tradin.presentation.user_profile

import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.data.data.dto.response.trade.TradeDto
import com.mocomoco.tradin.data.data.repository.FeedRepository
import com.mocomoco.tradin.data.data.repository.TradeRepository
import com.mocomoco.tradin.model.Feed
import com.mocomoco.tradin.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val repository: FeedRepository,
    private val tradeRepository: TradeRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow(UserProfileState())
    val state: StateFlow<UserProfileState> = _state

    fun loadUserFeeds(userId: Int) {

    }

    fun loadUserTrades(userId: Int) {

    }

    fun like(feedsId: Int) {

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

data class Trade(
    val myProductImage: String,
    val otherNickname: String,
    val otherProductImage: String,
    val otherUserId: Int,
    val status: String,
    val tradeDate: String,
    val tradeId: Int
)