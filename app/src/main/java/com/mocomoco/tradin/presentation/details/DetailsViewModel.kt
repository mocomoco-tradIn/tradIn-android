package com.mocomoco.tradin.presentation.details

import androidx.lifecycle.viewModelScope
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.data.data.dto.request_body.FeedIdBody
import com.mocomoco.tradin.data.data.dto.response.feeds.FeedDetailsDto
import com.mocomoco.tradin.data.data.repository.FeedRepository
import com.mocomoco.tradin.model.FeedStatus
import com.mocomoco.tradin.model.TradeMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val feedRepository: FeedRepository,

    ) : BaseViewModel() {
    private val _state = MutableStateFlow(DetailsState())
    val state: StateFlow<DetailsState> = _state

    private val _successLikeEvent = MutableSharedFlow<Boolean>()
    val successLikeEvent = _successLikeEvent.asSharedFlow()

    fun getDetails(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        _loading.value = false
        try {
            val dto = feedRepository.getFeedDetails(id)

            _state.value = state.value.copy(
                details = dto.mapToDetails(),
                isReported = false
            )
        } catch (e: Exception) {
            _toastMessage.emit("오류 발생 e:${e.message}")
        } finally {
            _loading.value = false
        }
    }

    fun like(id: Int) = viewModelScope.launch {
        _loading.value = true
        try {
            var like = true
            val dto = feedRepository.postLikeFeed(FeedIdBody(feedId = id))
            _state.value = state.value.copy(
                details = state.value.details.copy(
                    isLike = dto.isLikes
                )
            )
            _successLikeEvent.emit(like)
        } catch (e: Exception) {
            _toastMessage.emit("오류 발생 e:${e.message}")
        } finally {
            _loading.value = false
        }
    }


}

data class DetailsState(
    val details: Details = Details(),
    val isReported: Boolean = false
)

data class Details(
    val feedId: Int = 0,
    val category: String = "",
    val imageUrls: List<String> = listOf(),
    val title: String = "",
    val profileImage: String = "",
    val profileName: String = "",
    val isLike: Boolean = false,
    val likeCount: Int = 0,
    val tradeCount: Int = 0,
    val chatCount: Int = 0,
    val viewCount: Int = 0,
    val createdAt: String = "",
    val body: String = "",
    val location: String = "",
    val tradeMethod: TradeMethod = TradeMethod.None,
    val status: FeedStatus = FeedStatus.NONE,
    val userId: Int = 0
)

fun FeedDetailsDto.mapToDetails(): Details = Details(
    feedId = this.feedId,
    imageUrls = this.images ?: listOf(),
    title = this.title,
    profileImage = "", // todo
    profileName = this.nickname,
    isLike = false, // todo
    likeCount = this.likes,
    tradeCount = this.trades,
    chatCount = this.chatting,
    viewCount = this.hit,
    createdAt = this.createdAt,
    body = this.content,
    location = this.region,
    tradeMethod = when (this.tradeMethod) {
        TradeMethod.Etc.id -> TradeMethod.Etc
        TradeMethod.Parcel.id -> TradeMethod.Parcel
        TradeMethod.Direct.id -> TradeMethod.Direct
        else -> TradeMethod.None
    },
    status = when (this.status) {
        FeedStatus.WAIT.code -> FeedStatus.WAIT
        FeedStatus.PROGRESS.code -> FeedStatus.PROGRESS
        else -> FeedStatus.NONE
    },
    category = this.category,
    userId = this.userId
)
