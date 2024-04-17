package com.io.simocach_android.ui.pages.home.sections

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.io.simocach_android.ui.pages.home.model.ModelHomeSensor
import com.io.simocach_android.ui.resources.sensors.SensorsIcons
import com.io.simocach_android.ui.resources.theme.Theme
import com.io.simocach_android.ui.resources.theme.ThemeBase
import com.io.simocach_android.ui.resources.values.Dimens
import com.io.simocach_android.ui.resources.values.TxtStyles

@Preview(showBackground = true, backgroundColor = 0xFF041B11)
@Composable
fun HomeHeader(
    sensor: ModelHomeSensor? = null, totalActive: Int = 0, onClickArrow : (isLeft: Boolean) -> Unit = {

    }
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()

            .clip(RoundedCornerShape(Dimens.dp18))
            .background(
                brush = Brush.linearGradient(

                    listOf(
                        ThemeBase.colorPrimary,
//                            MaterialTheme.colorScheme.primary,
//                    ThemeBase.colorPrimary30,
                        ThemeBase.colorPrimary40,
//                    ThemeBase.colorPrimary20,
                    ),

                    start = Offset(0f, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            )
            .border(
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                    )
                ),
                width = Dimens.dp1,
                shape = RoundedCornerShape(Dimens.dp18)
            )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = Dimens.dp5,
                    color = Theme.md_theme_dark_error,
                    shape = RoundedCornerShape(Dimens.dp1)
                )
                .blur(
                    Dimens.dp4,
                    BlurredEdgeTreatment(RoundedCornerShape(Dimens.dp5))
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = Dimens.dp6),
            verticalAlignment = Alignment.CenterVertically
        ) {

            var labelModifier = Modifier.clip(RoundedCornerShape(Dimens.dp18))
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f)
                ).border(
                    brush = Brush.verticalGradient(
                        listOf(

                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                        )
                    ),
                    width = Dimens.dp1,
                    shape = RoundedCornerShape(Dimens.dp18)
                )
            if (totalActive <= 0) {
                Log.d("HomeHeader", "totalActive: "+ totalActive)
                labelModifier = labelModifier.then(Modifier.weight(1f))
//                fillMaxWidth()
            }else {
//                weight(1f)
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = labelModifier



            ) {

                Box(
                    modifier =
                    Modifier
                        .blur(
                            Dimens.dp4,
                            BlurredEdgeTreatment(RoundedCornerShape(Dimens.dp18))
                        )
                )
                Text(
                    text = if (totalActive > 0) {
                        "$totalActive Activos"
                    } else {
                        "Dispositivos no conectados"
                    },
                    style = TxtStyles.h4,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier

                        .padding(
                            start = Dimens.dp12,
                            top = Dimens.dp16,
                            end = Dimens.dp18,
                            bottom = Dimens.dp18,
                        )
                )
            }
//            Spacer(modifier = Modifier.width(0.dp))


            if (sensor != null && totalActive > 0) {

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(all = Dimens.dp0),
                    horizontalArrangement = Arrangement.SpaceBetween,


                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = { /* doSomething() */
                        onClickArrow.invoke(true)
                    }) {
                        Icon(
                            Icons.Outlined.KeyboardArrowLeft,
                            tint = MaterialTheme.colorScheme.onSurface,

                            contentDescription = "Arrow Back",
                        )
                    }
//                Spacer(modifier = Modifier.width(Dimens.dp12))

                    Row(
                        modifier = Modifier.padding(all = Dimens.dp0),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

//                        Box(
//                            contentAlignment = Alignment.Center,
//
//                            modifier = Modifier
//
//                                .border(
//                                    Dimens.dp1,
//
//                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
//                                    shape = RoundedCornerShape(Dimens.dp16)
//                                )
//                                .clip(CircleShape)
//                                .background(
//                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
//                                )
//
//                        ) {
//                            Image(
//                                painterResource(id = SensorsIcons.MAP_TYPE_TO_ICON.get(sensor.type)),
//                                contentDescription = "${sensor.name}",
//                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
//                                modifier = Modifier
//                                    .width(Dimens.dp28)
//                                    .height(Dimens.dp28)
//                                    .padding(Dimens.dp5)
//                            )
//                        }

//                        Spacer(modifier = Modifier.width(Dimens.dp12))

                        Text(
                            text = sensor.name,
                            style = TxtStyles.h4,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

//                Spacer(modifier = Modifier.width(Dimens.dp12))


                    IconButton(onClick = { /* doSomething() */
                        onClickArrow.invoke(false)
                    }) {
                        Icon(
                            Icons.Outlined.KeyboardArrowRight,
                            tint = MaterialTheme.colorScheme.onSurface,

                            contentDescription = "Arrow Next",
                        )
                    }

                }


                /*Image(
                painterResource(id = R.drawable.ic_round_keyboard_arrow_right_24),
                contentDescription = "slide to left",
                colorFilter = ColorFilter.tint(Theme.md_theme_dark_onPrimary),
            )*/
                Spacer(modifier = Modifier.width(Dimens.dp12))
            }

        }
    }
}