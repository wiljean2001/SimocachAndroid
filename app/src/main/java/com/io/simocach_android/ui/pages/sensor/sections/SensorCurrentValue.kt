package com.io.simocach_android.ui.pages.sensor.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import com.io.simocach_android.domains.sensors.SensorsConstants
import com.io.simocach_android.ui.resources.effects.drawColoredShadow
import com.io.simocach_android.ui.resources.values.Dimens
import com.io.simocach_android.ui.resources.values.TxtStyles

//@OptIn(ExperimentalPagerApi::class)
//@Preview(showBackground = true, backgroundColor = 0xFF041B11)
@Composable
fun SensorDetailCurrentValue(sensorType: Int = -1, value: Float= 0.0f) {
    val hasUnit = SensorsConstants.hasUnit(sensorType)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = Dimens.dp6),
        verticalAlignment = Alignment.Top,

        ) {
        Box(
            modifier = Modifier
                .size(Dimens.dp20)
                .background(Color(0xFFFFCA10), shape = CircleShape)
                .border(
                    width = Dimens.dp1, brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        )
                    ), shape = CircleShape
                )
                .drawColoredShadow(
                    Color(0xFFFFCA10), offsetY = Dimens.dp12,
                    shadowRadius = Dimens.dp8, borderRadius = Dimens.dp8, alpha = 0.5f
                ),
            contentAlignment = Alignment.Center

        ) {
            Box(
                modifier = Modifier
                    .size(Dimens.dp12)
                    .border(
                        width = Dimens.dp2,
                        color = MaterialTheme.colorScheme.onSurface,
                        shape = CircleShape
                    )
            )

        }

        Spacer(modifier = Modifier.width(Dimens.dp18))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dimens.dp12, end = Dimens.dp28)
        ) {

            Row(   modifier = Modifier
                .fillMaxWidth()
                ,verticalAlignment = Alignment.CenterVertically,) {


                if(hasUnit){
                    Text(
                        text = buildAnnotatedString {
                            append("Valor actual en ")

                            SensorsConstants.getUnit(this, sensorType)
                        },
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center,
                        style = TxtStyles.h4,
                    )
                }else{
                    Text(
                        text = "Current Value ",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center,
                        style = TxtStyles.h4,
                    )
                }


            }

            Text(
                text = "$value",
                fontSize = Dimens.sp48,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Spacer(modifier = Modifier.width(Dimens.dp18))


    }
}