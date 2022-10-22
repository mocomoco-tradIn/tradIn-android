package com.mocomoco.tradin.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mocomoco.tradin.R
import com.mocomoco.tradin.ui.TradInDestinations

// Set of Material typography styles to start with
val TradInFontFamily = FontFamily(
    Font(R.font.noto_sans_thin, FontWeight.Thin),
    Font(R.font.noto_sans_light, FontWeight.Light),
    Font(R.font.noto_sans_regular, FontWeight.Normal),
    Font(R.font.noto_sans_medium, FontWeight.Medium),
    Font(R.font.noto_sans_bold, FontWeight.Bold),
)
val TradInTypography = Typography(
    defaultFontFamily = TradInFontFamily,
    h1 = TextStyle(
        fontSize = 26.sp
    ),
    h2 = TextStyle(
        fontSize = 18.sp
    ),
    h3 = TextStyle(
        fontSize = 17.sp
    ),
    h4 = TextStyle(
        fontSize = 16.sp
    ),
    h5 = TextStyle(
        fontSize = 15.sp
    ),
    subtitle1 = TextStyle(
        fontSize = 14.sp
    ),
    subtitle2 = TextStyle(
        fontSize = 13.sp
    ),
    body1 = TextStyle(
        fontSize = 12.sp
    ),
    body2 = TextStyle(
        fontSize = 11.sp
    ),
    button = TextStyle(
        fontSize = 11.sp
    ),
    caption = TextStyle(
        fontSize = 9.sp
    )
)