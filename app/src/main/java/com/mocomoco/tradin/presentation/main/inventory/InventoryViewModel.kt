package com.mocomoco.tradin.presentation.main.inventory

import androidx.lifecycle.viewModelScope
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.data.data.dto.response.products.Product
import com.mocomoco.tradin.data.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow(
        InventoryState(
            onClickAddInventoryItem = ::onClickAddInventoryItem,
            onClickAddFeedItem = ::onClickAddFeedItem,
            onClickInventoryItem = ::onClickInventoryItem,
            onClickCheckBox = ::onClickCheckBox
        )
    )
    val state: StateFlow<InventoryState> = _state

    init {
        load()
    }

    private fun load() = viewModelScope.launch(Dispatchers.IO) {
        val dto = productRepository.getProducts(0)
        _state.value = state.value.copy(
            products = dto.products.mapIndexed { index, value ->
                value.mapToInventoryItem(index)
            }
        )

    }

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
    val products: List<InventoryItem> = listOf(),
    val selectedItem: InventoryItem? = null,
    val onClickAddInventoryItem: () -> Unit = {},
    val onClickAddFeedItem: () -> Unit = {},
    val onClickInventoryItem: () -> Unit = {},
    val onClickCheckBox: (index: Int) -> Unit = {},
) {
    val showAddFeedButton: Boolean get() = selectedItem != null
    val showNone: Boolean get() = products.isEmpty()
}

data class InventoryItem(
    val id: Int = -1,
    val index: Int = 0,
    val imgUrl: String = "https://lirp.cdn-website.com/9f256b4f/dms3rep/multi/opt/Mindfulness-happy-640w.jpg",
    val checked: Boolean = false,
    val title: String = ""
)
fun Product.mapToInventoryItem(index: Int): InventoryItem {
    return InventoryItem(
        id = this.productId,
        index = index,
        imgUrl = image,
        title = title
    )
}