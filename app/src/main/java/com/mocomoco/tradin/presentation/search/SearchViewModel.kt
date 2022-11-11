package com.mocomoco.tradin.presentation.search

import androidx.lifecycle.viewModelScope
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.data.data.repository.SearchRepository
import com.mocomoco.tradin.mapper.mapToFeed
import com.mocomoco.tradin.model.Feed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state

    private val ceh = CoroutineExceptionHandler { _, exception ->
        Logger.log("e: exception")
        viewModelScope.launch { // todo need test
            _toastMessage.emit("오류 발생 e:$exception")
        }
        when (exception) {

        }
    }

    val scope = viewModelScope + ceh

    fun search(keyword: String) = scope.launch(Dispatchers.IO) {
        if (keyword.isEmpty()) return@launch
        _loading.value = true
        val dto = searchRepository.getSearchResult(keyword, 0, 0)

        _state.value = state.value.copy(
            relatedTexts = listOf(),
            result = dto.feeds?.map { it.mapToFeed() } ?: listOf(),
            completeSearch = true
        )

        _loading.value = false
    }

    fun clear() {
        _state.value = SearchState()
    }

    fun onChangeSearchText(new: String) = scope.launch(Dispatchers.IO) {
        _state.value = state.value.copy(
            searchText = new,
            completeSearch = false,
            result = listOf()
        )

        if (new.isEmpty()) return@launch

        _loading.value = true

        val dto = searchRepository.getRelatedSearchKeywords(keyword = new)

        _state.value = state.value.copy(
            relatedTexts = dto.keywords ?: listOf()
        )

        _loading.value = false
    }
}

data class SearchState(
    val searchText: String = "",
    val relatedTexts: List<String> = listOf(),
    val result: List<Feed> = listOf(),
    val completeSearch: Boolean = false
)