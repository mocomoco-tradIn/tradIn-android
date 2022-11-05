package com.mocomoco.tradin.presentation.add

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mocomoco.tradin.R
import com.mocomoco.tradin.model.Category
import com.mocomoco.tradin.presentation.TradInDestinations.LOCATION_ROUTE
import com.mocomoco.tradin.presentation.common.*
import com.mocomoco.tradin.presentation.signup.components.InfoInputWithDescItem
import com.mocomoco.tradin.presentation.signup.components.InfoInputWithDescTextFieldItem
import com.mocomoco.tradin.presentation.theme.*
import com.mocomoco.tradin.util.asBitmap
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*


@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun AddScreen(
    viewModel: AddViewModel = hiltViewModel(),
    navEvent: (String) -> Unit,
) {

    val state = viewModel.state.collectAsState().value

    var itemName by rememberSaveable {
        mutableStateOf("")
    }

    var itemDesc by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    BackHandler {
        focusManager.clearFocus()
    }


    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()) { uris ->
            uris.forEach {
                viewModel.onAddImageFromGallery(it.asBitmap(context))
            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                viewModel.onAddImageFromGallery(it)
            }
        }

    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheet(
                onClickAlbum = {
                    galleryLauncher.launch("image/*")
                    scope.launch {
                        bottomSheetState.hide()
                    }
                },
                onClickCamera = {
                    cameraLauncher.launch()
                    scope.launch {
                        bottomSheetState.hide()
                    }
                }
            )
        },
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetState = bottomSheetState
    ) {

        Column {

            DefaultToolbar(
                showBack = true,
                title = state.title,
                rightButtons = listOf(
                    painterResource(id = R.drawable.ic_btn_complete) to { viewModel.onClickComplete() }
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                AddScreenSectionImage(
                    state,
                    onClickAddImage = {
                        scope.launch {
                            bottomSheetState.show()
                        }
                    }
                )

                VerticalSpacer(dp = 22.dp)

                AddScreenSectionCategories(
                    state, onClick = { category ->
                        viewModel.onClickCategory(category)
                    }
                )

                VerticalSpacer(dp = 30.dp)

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    InfoInputWithDescTextFieldItem(
                        title = "상품명",
                        input = itemName,
                        onInputChange = { itemName = it },
                        placeholderText = "최대 32자까지 입력가능",
                        editable = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    )

                    VerticalSpacer(dp = 16.dp)

                    InfoInputWithDescItem(title = "상세내용") {
                        TradInTextField(
                            value = itemDesc,
                            onValueChange = { itemDesc = it },
                            contentPaddingValues = PaddingValues(4.dp, 12.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Gray0,
                                backgroundColor = Transparent,
                                cursorColor = Gray0,
                                focusedIndicatorColor = Gray0,
                                unfocusedIndicatorColor = Gray3,
                                errorIndicatorColor = Pink1
                            ),
                            placeholder = {
                                Text(
                                    text = "상품 사용기간, 원가를 포함해 적으면 좋아요",
                                    style = MaterialTheme.typography.h5,
                                    color = Gray3,
                                    textAlign = TextAlign.Center
                                )
                            },
                        )
                    }

                    VerticalSpacer(dp = 16.dp)

                    InfoInputWithDescItem(
                        title = "희망 거래 방식",
                        descText = "기타의 경우 내용에 구체적인 내용을 기입해주세요",
                        descTextColor = Gray2
                    ) {
                        VerticalSpacer(dp = 8.dp)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            state.tradeMethodState.forEachIndexed { index, value ->
                                ToggleButton(
                                    modifier = Modifier.weight(1f),
                                    text = value.tradeMethod.display,
                                    enable = value.selected
                                ) {
                                    viewModel.onClickTradeMethod(value.tradeMethod)
                                }

                                if (index != state.tradeMethodState.size) {
                                    HorizontalSpacer(dp = 8.dp)
                                }
                            }
                        }
                    }

                    VerticalSpacer(dp = 16.dp)

                    InfoInputWithDescItem(
                        title = "희망 거래지역",
                        descTextColor = Gray2
                    ) {
                        DefaultTextFields(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navEvent(LOCATION_ROUTE)
                                },
                            value = state.location.display,
                            onValueChange = {
                                // do nothing
                            },
                            placeholderText = "희망 거래 지역을 설정해요",
                            enabled = false,
                            trailingIcon = {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_next),
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddScreenSectionImage(
    state: AddState,
    modifier: Modifier = Modifier,
    onClickAddImage: () -> Unit,
) {
    Column(modifier = modifier) {
        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
            if (state.showImageNone) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.ic_none_image),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onClickAddImage()
                        }
                    )
                }
            } else {
                state.imageUrls.forEach { url ->
                    item {
                        DefaultAsyncImage(
                            url, modifier = Modifier
                                .width(103.dp)
                                .height(103.dp)
                        )
                    }
                    item {
                        HorizontalSpacer(dp = 12.dp)
                    }
                }
                state.bitmaps.forEach { bitmap ->
                    item {
                        DefaultBitmapImage(
                            bitmap, modifier = Modifier
                                .width(103.dp)
                                .height(103.dp)
                        )
                    }
                    item {
                        HorizontalSpacer(dp = 12.dp)
                    }
                }
                item {
                    DefaultImage(
                        painterResource(id = R.drawable.ic_add_24_dp),
                        isCrop = false,
                        onClick = {
                            onClickAddImage()
                        },
                        modifier = Modifier
                            .width(103.dp)
                            .height(103.dp)
                            .background(Gray7)
                    )
                }
            }
        }
        VerticalSpacer(dp = 11.dp)
        Text(
            text = "사진은 최대 5장까지 첨부 가능합니다",
            style = RomTextStyle.text12,
            color = Gray2,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun AddScreenSectionCategories(
    state: AddState,
    modifier: Modifier = Modifier,
    onClick: (Category) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = "카테고리", style = RomTextStyle.text14, color = Gray0)

        VerticalSpacer(dp = 8.dp)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            state.categoryState.take(4).forEach {
                CategoryItem(
                    data = it.category,
                    selected = it.selected,
                    onClick = {
                        onClick(it.category)
                    }
                )
            }
        }
        VerticalSpacer(dp = 16.dp)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            state.categoryState.takeLast(4).forEach {
                CategoryItem(
                    data = it.category,
                    selected = it.selected,
                    onClick = {
                        onClick(it.category)
                    }
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    data: Category,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {},
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Image(painter = painterResource(id = data.iconResId), contentDescription = null)
            Image(
                painter = painterResource(id = if (selected) R.drawable.ic_checkbox_fill_on else R.drawable.ic_checkbox_fill_off),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 5.dp, y = 0.dp)
                    .clickable { onClick }
            )
        }

        VerticalSpacer(dp = 8.dp)

        Text(text = data.display, style = RomTextStyle.text14, color = Gray0)
    }
}

@Composable
fun BottomSheet(
    onClickAlbum: () -> Unit,
    onClickCamera: () -> Unit
) {
    val width = LocalConfiguration.current.screenWidthDp.dp + 4.dp
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {

        Text(
            text = "앨범에서 찾기",
            style = RomTextStyle.text16,
            color = Gray0,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickAlbum() }
                .padding(top = 24.dp, bottom = 18.dp),
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gray6)
        )

        Text(
            text = "카메라 촬영",
            style = RomTextStyle.text16,
            color = Gray0,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickCamera() }
                .padding(top = 18.dp, bottom = 24.dp),
            textAlign = TextAlign.Center
        )

    }

}


object FileUtil {
    // 임시 파일 생성
    fun createTempFile(context: Context, fileName: String): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, fileName)
    }

    // 파일 내용 스트림 복사
    fun copyToFile(context: Context, uri: Uri, file: File) {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        val buffer = ByteArray(4 * 1024)
        while (true) {
            val byteCount = inputStream!!.read(buffer)
            if (byteCount < 0) break
            outputStream.write(buffer, 0, byteCount)
        }

        outputStream.flush()
    }
}

object UriUtil {
    // URI -> File
    fun toFile(context: Context, uri: Uri): File {
        val fileName = getFileName(context, uri)

        val file = FileUtil.createTempFile(context, fileName)
        FileUtil.copyToFile(context, uri, file)

        return File(file.absolutePath)
    }

    // get file name & extension
    private fun getFileName(context: Context, uri: Uri): String {
        val name = uri.toString().split("/").last()
        val ext = context.contentResolver.getType(uri)!!.split("/").last()

        return "$name.$ext"
    }
}


fun Bitmap.asFile(context: Context): File {
    val wrapper = ContextWrapper(context)
    var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
    file = File(file, "${UUID.randomUUID()}.jpg")
    val stream: OutputStream = FileOutputStream(file)
    this.compress(Bitmap.CompressFormat.JPEG, 25, stream)
    stream.flush()
    stream.close()
    return file
}