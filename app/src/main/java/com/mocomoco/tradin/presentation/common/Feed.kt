package com.mocomoco.tradin.presentation.common

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mocomoco.tradin.R
import com.mocomoco.tradin.model.Feed
import com.mocomoco.tradin.model.FeedStatus
import com.mocomoco.tradin.presentation.theme.*

@Composable
fun FeedItem(
    feed: Feed,
    onClickLike: (id: Int) -> Unit,
    onClickFeed: (Feed) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                onClickFeed(feed)
            }
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(feed.imgUrl).crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .border(borderStrokeBlack2, shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
            )

            Image(painter = painterResource(
                id = if (feed.isLiked) R.drawable.ic_like_on_36_dp else R.drawable.ic_like_off_36_dp
            ),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset((-4).dp, (-4).dp)
                    .clip(RoundedCornerShape(50f))
                    .clickable { onClickLike(feed.id) })

        }

        VerticalSpacer(dp = 12.dp)

        Text(text = feed.title, style = RomTextStyle.text14, color = Gray0)
        VerticalSpacer(dp = 4.dp)

        Text(
            text = "${feed.location} · ${feed.nickname}",
            style = RomTextStyle.text12,
            color = Gray2,
            overflow = TextOverflow.Ellipsis
        )
        VerticalSpacer(dp = 10.dp)

        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            Row(
                modifier = Modifier
                    .border(BorderStroke(1.dp, Gray4), shape = RoundedCornerShape(50f))
                    .clip(RoundedCornerShape(50f))
                    .padding(vertical = 2.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_like_off_21_dp),
                    contentDescription = null
                )
                Text(
                    text = if (feed.likeCount > 999) "999+" else "${feed.likeCount}",
                    style = RomTextStyle.text12,
                    color = Gray1
                )
            }
            HorizontalSpacer(dp = 8.dp)

            if (feed.status != FeedStatus.NONE) {
                Text(
                    text = feed.status.display,
                    style = RomTextStyle.text12,
                    color = feed.status.textColor,
                    fontWeight = FontWeight(700),
                    modifier = Modifier
                        .background(
                            color = feed.status.backgroundColor,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                )
            }

        }
        VerticalSpacer(dp = 8.dp)


        Text(
            text = feed.createdAt,
            style = RomTextStyle.text12,
            fontWeight = FontWeight(400),
            color = Gray3
        )
        VerticalSpacer(dp = 10.dp)
    }

}
