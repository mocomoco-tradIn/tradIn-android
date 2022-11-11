package com.mocomoco.tradin.presentation.search

import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.data.data.repository.SearchRepository
import com.mocomoco.tradin.model.Feed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state


    fun search() {

    }

    fun onClickRelatedTexts(related: String) {

    }

    fun onClickClear() {

    }

    fun onChangeSearchText(new: String) {
        _state.value = state.value.copy(
            searchText = new
        )
    }
}

data class SearchState(
    val searchText: String = "",
    val relatedTexts: List<String> = listOf(),
    val result: List<Feed> = listOf(),
    val readyState: Boolean = true
)