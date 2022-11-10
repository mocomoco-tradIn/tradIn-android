package com.mocomoco.tradin.presentation.details

import androidx.lifecycle.viewModelScope
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.data.data.dto.request_body.FeedIdBody
import com.mocomoco.tradin.data.data.dto.request_body.ReportFeedBody
import com.mocomoco.tradin.data.data.repository.FeedRepository
import com.mocomoco.tradin.mapper.mapToDetails
import com.mocomoco.tradin.model.Details
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

    private val _reportState = MutableStateFlow(ReportState())
    val reportState: StateFlow<ReportState> = _reportState

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

    fun like(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        _loading.value = true
        try {
            val dto = feedRepository.postLikeFeed(FeedIdBody(feedId = id))
            _state.value = state.value.copy(
                details = state.value.details.copy(
                    isLike = dto.isLikes,
                    likeCount = state.value.details.likeCount + (if (dto.isLikes) 1 else -1)
                )
            )
            _successLikeEvent.emit(dto.isLikes)
        } catch (e: Exception) {
            _toastMessage.emit("오류 발생 e:${e.message}")
        } finally {
            _loading.value = false
        }
    }

    fun getReportReasons() = viewModelScope.launch(Dispatchers.IO) {
        _loading.value = true
        try {
            val dto = feedRepository.getReportReasons()
            _reportState.value = reportState.value.copy(
                reasons = dto.reports.mapIndexed { index, reason ->
                    ReportState.ReportReason(
                        index = index,
                        title = reason
                    )
                } + ReportState.ReportReason(
                    index = dto.reports.size,
                    title = "기타"
                )
            )
        } catch (e: Exception) {
            _toastMessage.emit("오류 발생 e:${e.message}")
        } finally {
            _loading.value = false
        }
    }

    fun report(feedId: Int, reason: String) = viewModelScope.launch(Dispatchers.IO) {
        _loading.value = true
        try {
            feedRepository.postReportFeed(
                ReportFeedBody(
                    feedId = feedId,
                    content = reason
                )
            )
            _reportState.value = ReportState(completeReport = true)
            _toastMessage.emit("신고 완료, 롬롬정책을 위반했는지 검토할게요")
        } catch (e: Exception) {
            _toastMessage.emit("오류 발생 e:${e.message}")
        } finally {
            _loading.value = false
        }
    }

    fun onClickReportReason(reason: String, index: Int) {
        _reportState.value = reportState.value.copy(
            reasons = reportState.value.reasons.map {
                if (reason == it.title) {
                    it.copy(
                        isChecked = true
                    )
                } else {
                    it.copy(
                        isChecked = false
                    )
                }
            },
            selectedReason = reason,
            isSelected = true,
            isSelectEtc = index == reportState.value.reasons.lastIndex
        )
    }

    fun clearReportState() {
        _reportState.value = ReportState()
    }
}

data class DetailsState(
    val details: Details = Details(),
    val isReported: Boolean = false,
)

data class ReportState(
    val selectedReason: String = "",
    val isSelected: Boolean = false,
    val isSelectEtc: Boolean = false,
    val reasons: List<ReportReason> = listOf(),
    val completeReport: Boolean = false
) {
    data class ReportReason(
        val index: Int,
        val title: String = "",
        val isChecked: Boolean = false
    )
}


