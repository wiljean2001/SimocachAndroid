package com.io.simocach_android.ui.pages.sensor.sections

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.zIndex
import com.io.simocach_android.ui.resources.theme.ThemeBase
import com.io.simocach_android.ui.resources.values.Dimens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//@OptIn(ExperimentalPagerApi::class)
//@Preview(showBackground = true, backgroundColor = 0xFF041B11)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SensorDetailHeader(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    tabItems: List<String> = listOf("Graph")
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()

            .clip(
                RoundedCornerShape(
                    topStart = Dimens.dp32, topEnd = Dimens.dp32,
                    bottomStart = Dimens.dp16, bottomEnd = Dimens.dp16
                )
            )
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        ThemeBase.colorPrimary.copy(alpha = 0.2f),
//                    ThemeBase.colorPrimary30,
                        ThemeBase.colorPrimary.copy(alpha = 0.0f),
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
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    )
                ),
                width = Dimens.dp1,
                shape = RoundedCornerShape(
                    topStart = Dimens.dp32, topEnd = Dimens.dp32,
                    bottomStart = Dimens.dp16, bottomEnd = Dimens.dp16
                )
            )
    ) {

        TabRow(
            selectedTabIndex = pagerState.currentPage,

//            backgroundColor = Color.Yellow,
            modifier = Modifier
                .background(color = Color.Transparent)
                .padding(horizontal = Dimens.dp8, vertical = Dimens.dp8)
//                .clip(RoundedCornerShape(Dimens.dp28))
            ,


            containerColor = Color.Transparent,
            divider = { Divider(color = Color.Transparent) },
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(
                            tabPositions[pagerState.currentPage]
//                            pagerState, tabPositions
                        )
                        .background(
                            brush = Brush.linearGradient(

                                listOf(
                                    ThemeBase.colorPrimary,
//                            MaterialTheme.colorScheme.primary,
//                    ThemeBase.colorPrimary30,
                                    ThemeBase.colorPrimary30,
//                    ThemeBase.colorPrimary20,
                                ),

                                start = Offset(0f, 0f),
                                end = Offset(0f, Float.POSITIVE_INFINITY)
                            ), shape = RoundedCornerShape(
                                topStart = Dimens.dp24, topEnd = Dimens.dp24,
                                bottomStart = Dimens.dp12, bottomEnd = Dimens.dp12
                            )
                        )
                        .width(Dimens.dp0)
                        .height(Dimens.dp48)
                        .zIndex(1f),

                    color = Color.Transparent,

                    )
            }
        ) {
            tabItems.forEachIndexed { index, title ->
                val color = remember {
                    Animatable(Color.Green)
                }

                LaunchedEffect(
                    pagerState.currentPage == index
                ) {
                    Log.d("Tabs", "LaunchedEffect: ${pagerState.currentPage} $index")
                    color.animateTo(if (pagerState.currentPage == index) ThemeBase.colorPrimary else Color.Transparent)
                }
                Tab(
                    text = {
                        Text(
                            title,
                            style = if (pagerState.currentPage == index) TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = Dimens.sp18
                            ) else TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = Dimens.sp16
                            )
                        )
                    },
                    selected = pagerState.currentPage == index,
                    modifier = Modifier
                        .background(
                            color = Color.Transparent,// color.value,

                            shape = RoundedCornerShape(
                                topStart = Dimens.dp28, topEnd = Dimens.dp28,
                                bottomStart = Dimens.dp12, bottomEnd = Dimens.dp12
                            )
                        )
                        .zIndex(2f),

                    onClick = {
                        coroutineScope.launch {
                            Log.d("Tabs", "onClick: $index")
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }


    }
}