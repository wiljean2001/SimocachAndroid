package com.io.simocach_android.ui.resources.values

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.io.simocach_android.R

object TxtStyles {

    private val fontsJost = FontFamily(
        Font(R.font.jost_light, FontWeight.Light),
        Font(R.font.jost_regular),
        Font(R.font.jost_medium, FontWeight.Medium),
        Font(R.font.jost_bold, FontWeight.Bold)
    )

    val h1 = TextStyle(
        fontFamily = fontsJost,
        fontWeight = FontWeight.Medium,
        fontSize = Dimens.sp42
    )

    val h2 = TextStyle(
        fontFamily = fontsJost,
        fontWeight = FontWeight.Normal,
        fontSize = Dimens.sp32
    )

    val h3 = TextStyle(
        fontFamily = fontsJost,
        fontWeight = FontWeight.Normal,
        fontSize = Dimens.sp28
    )

    val h4 = TextStyle(
        fontFamily = fontsJost,
        fontWeight = FontWeight.Normal,
        fontSize = Dimens.sp20
    )

    val h5 = TextStyle(
        fontFamily = fontsJost,
        fontWeight = FontWeight.Normal,
        fontSize = Dimens.sp18
    )

    val h6 = TextStyle(
        fontFamily = fontsJost,
        fontWeight = FontWeight.Normal,
        fontSize = Dimens.sp14
    )

    val p1 = TextStyle(
        fontFamily = fontsJost,
        fontWeight = FontWeight.Normal,
        fontSize = Dimens.sp20
    )

    val p2 = TextStyle(
        fontFamily = fontsJost,
        fontWeight = FontWeight.Normal,
        fontSize = Dimens.sp16
    )

    val p3 = TextStyle(
        fontFamily = fontsJost,
        fontWeight = FontWeight.Normal,
        fontSize = Dimens.sp12
    )


    val button = TextStyle(
        fontFamily = fontsJost,
        fontWeight = FontWeight.Medium
    )

    val caption = TextStyle(
        fontFamily = fontsJost,
        fontWeight = FontWeight.Normal,
        fontSize = Dimens.sp12
    )


}