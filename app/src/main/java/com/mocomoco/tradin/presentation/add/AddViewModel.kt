package com.mocomoco.tradin.presentation.add

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.model.Category
import com.mocomoco.tradin.model.Location
import com.mocomoco.tradin.model.TradeMethod
import com.mocomoco.tradin.presentation.nav.Arguments.FROM_INVENTORY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val fromInventory = savedStateHandle[FROM_INVENTORY] ?: false

    private val _state = MutableStateFlow(
        AddState(
            title = if (fromInventory) "내 서랍에 추가" else "피드에 올리기"
        )
    )
    val state: StateFlow<AddState> = _state


    fun onAddImage(bitmap: Bitmap) {

    }

    fun onSelectLocation(location: Location) {

    }

    fun onClickCategory(category: Category) {

    }

    fun onClickComplete() {

    }
}

data class AddState(
    val title: String = "",
    val imageUrls: List<String> = listOf(),
    val selectedCategory: Category = Category.None,
    val itemName: String = "",
    val itemDesc: String = "",
    val selectedTradeMethod: TradeMethod = TradeMethod.None,
    val location: Location = Location(),
    val categoryState: List<CategoryState> = listOf(
        CategoryState(Category.Cloth),
        CategoryState(Category.Book),
        CategoryState(Category.Hobby),
        CategoryState(Category.Electronic),
        CategoryState(Category.Stuff),
        CategoryState(Category.Idol),
        CategoryState(Category.Ticket),
        CategoryState(Category.Etc),
    ),
    val tradeMethodState: List<TradeMethodState> = listOf(
        TradeMethodState(TradeMethod.Direct),
        TradeMethodState(TradeMethod.Parcel),
        TradeMethodState(TradeMethod.Etc)
    )
) {
    data class CategoryState(
        val category: Category,
        val selected: Boolean = false
    )

    data class TradeMethodState(
        val tradeMethod: TradeMethod,
        val selected: Boolean = false
    )

    val showImageNone: Boolean get() = imageUrls.isEmpty()
}