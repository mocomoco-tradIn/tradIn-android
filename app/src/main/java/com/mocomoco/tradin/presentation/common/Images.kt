package com.mocomoco.tradin.presentation.common

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mocomoco.tradin.R
import com.mocomoco.tradin.presentation.theme.borderStrokeBlack2


@Composable
fun BorderAsyncImage(url: String, modifier: Modifier = Modifier) {
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
fun DefaultAsyncImage(url: String, modifier: Modifier = Modifier, contentScale: ContentScale = ContentScale.Crop) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale,
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


@Composable
fun ProfileImage52Dp(url: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        if (url.isEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.ic_default_profile_image_30_dp),
                contentDescription = null,
                modifier = modifier.size(52.dp),
                contentScale = ContentScale.Fit
            )
        } else {
            DefaultAsyncImage(
                url = url,
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(50))
            )
        }
    }
}