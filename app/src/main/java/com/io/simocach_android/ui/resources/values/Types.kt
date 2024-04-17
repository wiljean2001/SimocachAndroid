package com.io.simocach_android.ui.resources.values

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(

    headlineSmall = TxtStyles.h5.merge(
        other = TextStyle(
            lineHeight = Dimens.sp16,
            letterSpacing = Dimens.sp0_8,
        )
    ),

    headlineMedium = TxtStyles.h3.merge(
        other = TextStyle(
            lineHeight = Dimens.sp22,
            letterSpacing = Dimens.sp0_4,
        )
    ),

    headlineLarge = TxtStyles.h1.merge(
        other = TextStyle(
            lineHeight = Dimens.sp36,
            letterSpacing = Dimens.sp0_4,
        )
    ),

    bodyLarge = TxtStyles.p1.merge(
        other = TextStyle(
            lineHeight = Dimens.sp22,
            letterSpacing = Dimens.sp0_6,
        )
    ),

    titleLarge = TxtStyles.h4.merge(
        other = TextStyle(
            lineHeight = Dimens.sp20,
            letterSpacing = Dimens.sp0_0,
        )
    ),


    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)