package com.mocomoco.tradin.presentation.add

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mocomoco.tradin.base.BaseViewModel
import com.mocomoco.tradin.common.Logger
import com.mocomoco.tradin.data.data.dto.request_body.AddProductBody
import com.mocomoco.tradin.data.data.dto.response.ImageUrlDto
import com.mocomoco.tradin.data.data.repository.ProductRepository
import com.mocomoco.tradin.model.Category
import com.mocomoco.tradin.model.Location
import com.mocomoco.tradin.model.TradeMethod
import com.mocomoco.tradin.presentation.nav.Arguments.FROM_INVENTORY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val application: Application,
    private val productRepository: ProductRepository
) : BaseViewModel() {

    private val fromInventory = savedStateHandle[FROM_INVENTORY] ?: false

    private val _state = MutableStateFlow(
        AddState(
            title = if (fromInventory) "내 서랍에 추가" else "피드에 올리기"
        )
    )
    val state: StateFlow<AddState> = _state


    fun onAddImageFromDevice(bitmap: Bitmap) = with(state.value) {
        _state.value = copy(
            bitmaps = bitmaps.toMutableList().apply { add(bitmap) }
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

    fun onClickComplete(name: String, desc: String) = viewModelScope.launch(Dispatchers.IO) {
        _loading.value = true
        try {
            with(state.value) {
                Logger.log("onClickComplete postInfo $this")
                val dto = productRepository.postUploadProduct(
                    AddProductBody(
                        categoryId = postInfo.selectedCategory.code,
                        title = name,
                        content = desc, // todo 스테이트 데이터로 교체
                        images = listOf(
                            "https://d1p1ltj3ad3g5i.cloudfront.net/local/images/1/2022-10-31-00:26:34-1",
                            "https://d1p1ltj3ad3g5i.cloudfront.net/local/images/1/2022-10-31-00:26:34-1"
                        ), // todo 이미지 업로드
                        regionCode = postInfo.location.code,
                        tradeMethodId = postInfo.selectedTradeMethod.code
                    )
                )
                _toastMessage.emit("성공적으로 상품을 추가 했어요!")

                _state.value = state.value.copy(
                    completeAdd = true
                )
                Logger.log("onClickComplete success $dto")
            }
        } catch (e: Exception) {
            Logger.log("onClickComplete e $e")

            _toastMessage.emit("아직 채워지지 않은 정보가 있어요!")
        } finally {
            _loading.value = false
        }
    }

    private fun uploadImage() = viewModelScope.launch(Dispatchers.IO) {
        Logger.log("onClickComplete")

        _loading.value = true
        _toastMessage.emit("이미지 업로드 중")

        val bodies = withContext(Dispatchers.Default) {
            state.value.bitmaps.map {
                Logger.log("onClickComplete asFile 0")
                val file = it.asFile(application)
                Logger.log("onClickComplete asFile 1")

                Logger.log("onClickComplete asRequestBody 0")
                val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                Logger.log("onClickComplete asRequestBody 1")

                Logger.log("onClickComplete createFormData 0")
                val body = MultipartBody.Part.createFormData("images", file.name, requestBody)
                Logger.log("onClickComplete createFormData 1 \n headers ${body.headers} \n ${body.body.contentType()}")

                body
            }
        }

        Logger.log("job start")


        val jobs = mutableListOf<Deferred<ImageUrlDto>>()
        try {
            productRepository.postUploadImage(bodies.first())

        } catch (e: Exception) {
            Logger.logE("error $e")
        }
//        bodies.forEach {
//            jobs.add(
//                async {
//                    productRepository.postUploadImage(it)
//                }
//            )
//        }
//        val imageUrls = jobs.awaitAll()
//            .filter {
//                Logger.logE("awaitAll dto $it")
//                it.url.isNotEmpty()
//            }.map {
//                it.url.first()
//            }
//        _state.value = state.value.copy(
//            postInfo = state.value.postInfo.copy(
//                imageUrls = imageUrls
//            )
//        )


        _toastMessage.emit("이미지 업로드 완료")
    }
}

data class AddState(
    val title: String = "",
    val bitmaps: List<Bitmap> = listOf(),
    val imageUrls: List<String> = listOf(),
    val completeAdd: Boolean = false,
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
    ),
    val postInfo: PostInfo = PostInfo(),
) {
    data class PostInfo(
        val imageUrls: List<String> = listOf(),
        val itemName: String = "", // todo delete
        val itemDesc: String = "", // todo delete
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

    val showImageNone: Boolean get() = postInfo.imageUrls.isEmpty() && bitmaps.isEmpty()
}
