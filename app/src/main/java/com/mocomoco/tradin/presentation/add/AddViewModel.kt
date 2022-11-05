package com.mocomoco.tradin.presentation.add

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.common.Logger
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


    fun onAddImageFromGallery(bitmap: Bitmap) = with(state.value) {
        _state.value = copy(
            postInfo = postInfo.copy(
                bitmaps = postInfo.bitmaps.toMutableList().apply { add(bitmap) }
            )
        )
    }

    fun onSelectLocation(location: Location) = with(state.value) {
        _state.value = copy(
            postInfo = postInfo.copy(
                location = location
            )
        )
    }

    fun onClickCategory(category: Category) {
        Logger.log("clicked Category $category")
        with(state.value) {
            _state.value = copy(
                categoryStates = categoryStates.map {
                    if (category.code == it.category.code) {
                        Logger.log("same Category $category")
                        it.copy(
                            selected = true
                        )
                    } else {
                        it.copy(
                            selected = false
                        )
                    }
                },
                postInfo = postInfo.copy(
                    selectedCategory = category
                )
            )
        }
    }

    fun onClickTradeMethod(tradeMethod: TradeMethod) = with(state.value) {
        _state.value = copy(
            tradeMethodStates = tradeMethodStates.map {
                if (tradeMethod.code == it.tradeMethod.code) {
                    it.copy(
                        selected = true
                    )
                } else {
                    it.copy(
                        selected = false
                    )
                }
            },
            postInfo = postInfo.copy(
                selectedTradeMethod = tradeMethod
            )
        )
    }

    fun onClickComplete() {

    }
}

data class AddState(
    val title: String = "",
    val postInfo: PostInfo = PostInfo(),
    val categoryStates: List<CategoryState> = listOf(
        CategoryState(Category.Cloth),
        CategoryState(Category.Book),
        CategoryState(Category.Hobby),
        CategoryState(Category.Electronic),
        CategoryState(Category.Stuff),
        CategoryState(Category.Idol),
        CategoryState(Category.Ticket),
        CategoryState(Category.Etc),
    ),
    val tradeMethodStates: List<TradeMethodState> = listOf(
        TradeMethodState(TradeMethod.Direct),
        TradeMethodState(TradeMethod.Parcel),
        TradeMethodState(TradeMethod.Etc)
    )
) {
    data class PostInfo(
        val imageUrls: List<String> = listOf(),
        val bitmaps: List<Bitmap> = listOf(),
        val itemName: String = "",
        val itemDesc: String = "",
        val selectedCategory: Category = Category.None,
        val selectedTradeMethod: TradeMethod = TradeMethod.None,
        val location: Location = Location()
    )

    data class CategoryState(
        val category: Category,
        val selected: Boolean = false
    )

    data class TradeMethodState(
        val tradeMethod: TradeMethod,
        val selected: Boolean = false
    )

    val showImageNone: Boolean get() = postInfo.imageUrls.isEmpty() && postInfo.bitmaps.isEmpty()
}