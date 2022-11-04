package com.mocomoco.tradin.presentation.common

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mocomoco.tradin.presentation.theme.borderStrokeBlack2


@Composable
fun DefaultAsyncImage(url: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = modifier
            .border(borderStrokeBlack2, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun DefaultImage(
    painter: Painter,
    modifier: Modifier = Modifier,
    isCrop: Boolean = true,
    onClick: () -> Unit = {}
) {
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .border(borderStrokeBlack2, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        contentScale = if (isCrop) ContentScale.Crop else ContentScale.None,
    )
}

@Composable
fun DefaultBitmapImage(
    bitmap: Bitmap,
    modifier: Modifier = Modifier,
    isCrop: Boolean = true,
    onClick: () -> Unit = {}
) {
    Image(
        bitmap.asImageBitmap(),
        contentDescription = null,
        modifier = modifier
            .border(borderStrokeBlack2, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        contentScale = if (isCrop) ContentScale.Crop else ContentScale.None,
    )
}