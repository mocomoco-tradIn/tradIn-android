package com.mocomoco.tradin.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mocomoco.tradin.R

val TradInFontFamily = FontFamily(
    Font(R.font.noto_sans_thin, FontWeight.Thin),
    Font(R.font.noto_sans_light, FontWeight.Light),
    Font(R.font.noto_sans_regular, FontWeight.Normal),
    Font(R.font.noto_sans_medium, FontWeight.Medium),
    Font(R.font.noto_sans_bold, FontWeight.Bold),
)

// todo 향후 includeFontPadding 이 디폴트로 false 로 될 때 PlatformTextStyle 지우면 된다.
@OptIn(ExperimentalTextApi::class)
val TradInTypography = Typography(
    defaultFontFamily = TradInFontFamily,
    h1 = TextStyle(
        fontSize = 26.sp,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    h2 = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    h3 = TextStyle(
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    h4 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    h5 = TextStyle(
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    subtitle1 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    subtitle2 = TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    body1 = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    body2 = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    button = TextStyle(
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    caption = TextStyle(
        fontSize = 9.sp,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
)

@OptIn(ExperimentalTextApi::class)
object RomTextStyle {
    val text26 = TextStyle(
        fontSize = 26.sp,
        fontFamily = TradInFontFamily,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )

    val text20 = TextStyle(
        fontSize = 20.sp,
        fontFamily = TradInFontFamily,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )

    val text18 = TextStyle(
        fontSize = 18.sp,
        fontFamily = TradInFontFamily,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )

    val text17 = TextStyle(
        fontSize = 17.sp,
        fontFamily = TradInFontFamily,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )

    val text16 = TextStyle(
        fontSize = 16.sp,
        fontFamily = TradInFontFamily,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )

    val text15 = TextStyle(
        fontSize = 15.sp,
        fontFamily = TradInFontFamily,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )

    val text14 = TextStyle(
        fontSize = 14.sp,
        fontFamily = TradInFontFamily,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )

    val text13 = TextStyle(
        fontSize = 13.sp,
        fontFamily = TradInFontFamily,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )

    val text12 = TextStyle(
        fontSize = 12.sp,
        fontFamily = TradInFontFamily,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
    val text11 = TextStyle(
        fontSize = 11.sp,
        fontFamily = TradInFontFamily,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )

    val text9 = TextStyle(
        fontSize = 9.sp,
        fontFamily = TradInFontFamily,
        fontWeight = FontWeight.Medium,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
}
