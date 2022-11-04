package com.mocomoco.tradin.presentation.main.add

import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.common.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(

) : BaseViewModel() {

    private val _state = MutableStateFlow(InventoryState(
        onClickAddInventoryItem = ::onClickAddInventoryItem,
        onClickAddFeedItem = ::onClickAddFeedItem,
        onClickInventoryItem = ::onClickInventoryItem,
        onClickCheckBox = ::onClickCheckBox
    ))
    val state: StateFlow<InventoryState> = _state

    private fun onClickAddInventoryItem() {
        Logger.log("onClickAddInventoryItem")
    }

    private fun onClickAddFeedItem() {
        Logger.log("onClickAddFeedItem")

    }

    private fun onClickInventoryItem() {
        Logger.log("onClickInventoryItem")

    }

    private fun onClickCheckBox(index: Int) {
        Logger.log("onClickCheckBox")

    }


}

data class InventoryState(
    val items: List<InventoryItem> = listOf(),
    val selectedItem: InventoryItem? = null,
    val onClickAddInventoryItem: () -> Unit = {},
    val onClickAddFeedItem: () -> Unit = {},
    val onClickInventoryItem: () -> Unit = {},
    val onClickCheckBox: (index: Int) -> Unit = {},
) {
    val showAddFeedButton: Boolean get() = selectedItem != null
    val showNone: Boolean get() = items.isEmpty()
}

data class InventoryItem(
    val id: Int = -1,
    val index: Int = 0,
    val imgUrl: String = "https://lirp.cdn-website.com/9f256b4f/dms3rep/multi/opt/Mindfulness-happy-640w.jpg",
    val checked: Boolean = false,
    val title: String = "타이틀입니다.타이틀입니다.타이틀입니다.타이틀입니다."
)