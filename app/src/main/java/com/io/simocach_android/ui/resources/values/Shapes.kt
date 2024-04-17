package com.io.simocach_android.ui.resources.values

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object Shapes {

    object Common {

        val shapes = Shapes(
            small = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(12.dp),
            large = RoundedCornerShape(24.dp)
        )
    }


    object Space {
        val H4 = Modifier.height(Dimens.dp4)
        val H18 = Modifier.height(Dimens.dp18)
        val H20 = Modifier.height(Dimens.dp20)
        val H24 = Modifier.height(Dimens.dp24)
        val H28 = Modifier.height(Dimens.dp28)
        val H32 = Modifier.height(Dimens.dp32)
        val H48 = Modifier.height(Dimens.dp48)
        val H56 = Modifier.height(Dimens.dp56)
        val H64 = Modifier.height(Dimens.dp64)


        val W18 = Modifier.width(Dimens.dp18)
        val W20 = Modifier.width(Dimens.dp20)
        val W24 = Modifier.width(Dimens.dp24)
        val W28 = Modifier.width(Dimens.dp28)
        val W32 = Modifier.width(Dimens.dp32)
    }
}