package com.io.simocach_android.ui.pages.sensor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.io.simocach_android.R
import com.io.simocach_android.domains.sensors.MySensor
import com.io.simocach_android.domains.sensors.provider.SensorsProvider
import com.io.simocach_android.ui.pages.sensor.sections.SensorChart
import com.io.simocach_android.ui.pages.sensor.sections.SensorDetail
import com.io.simocach_android.ui.pages.sensor.sections.SensorDetailCurrentValue
import com.io.simocach_android.ui.pages.sensor.sections.SensorDetailHeader
import com.io.simocach_android.ui.resources.values.Dimens
import com.io.simocach_android.ui.resources.values.TxtStyles

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalTextApi::class, ExperimentalFoundationApi::class, // ExperimentalPagerApi::class
)
@Preview(showBackground = true, backgroundColor = 0xFF041B11)
@Composable
fun SensorPage(
    modifier: Modifier = Modifier, navController: NavController? = null,
    type: Int = MySensor.TYPE_TEMPERATURE,
    viewModel: SensorViewModel = viewModel(
        factory = SensorViewModelFactory(
            type
        )// viewModelSensor
    )
//    viewModel: SensorViewModel = SensorViewModel()
){
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val tabItems = listOf("Graph")//"Visual",
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabItems.size })

    val sensorFlowState = SensorsProvider.getInstance().listenSensor(type)
        .collectAsState(initial = SensorsProvider.getInstance().getSensor(type));
    val sensorState = remember {
        sensorFlowState
    }

    val sensorRms = viewModel.mSensorModulus.collectAsState(initial = 0.0f)

    val sensorRmsState = remember {sensorRms}

    Scaffold(
        topBar = {
            // AppBar
            TopAppBar(

//            backgroundColor = Color.Transparent,
                colors = if (lazyListState.firstVisibleItemIndex > 0) TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f), //Add your own color here, just to clarify.
                ) else TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent //Add your own color here, just to clarify.
                ),

                modifier = Modifier.padding(horizontal = Dimens.dp16),

                navigationIcon = {

                    /*AppBarIcon(
                        icon = imageResource(
                            id = R.drawable.ic_menu_black_24dp)
                    ) {
                        // Open nav drawer
                    }*/

                    IconButton(
                        onClick = { navController?.navigateUp() },
//                    modifier = Modifier.fillMaxHeight()
                    ) {
                        Icon(Icons.Rounded.KeyboardArrowLeft, "back")

                        /* Image(
                             painterResource(id = R.drawable.ic_round_keyboard_arrow_left_24),
                             contentDescription = "slide to left",
                             colorFilter = ColorFilter.tint(Color(0xFFFFFFFF)),
                             alignment = Alignment.Center,
                         )*/
                    }

                },
                title = {
                    Text(
                        text = "${sensorState.value?.name}",
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        style = TxtStyles.h4,
                        fontWeight = FontWeight(400),
                        modifier = modifier.fillMaxWidth(),
                    )
                }, actions = {
                    Box(Modifier.padding(horizontal = Dimens.dp20)) {
                        Image(

                            painterResource(id = R.drawable.pic_logo),
                            modifier = Modifier
                                .alpha(0f)
                                .width(Dimens.dp32)
                                .height(Dimens.dp36),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }
            )
        },
        /*
        TODO use in future, don't remove
        floatingActionButton = {
            FloatingActionButton(
                onClick = { *//*TODO*//* },
                shape = RoundedCornerShape(50),
                containerColor = Color.Transparent,

                modifier = Modifier
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                JLThemeBase.colorPrimary.copy(alpha = 0.3f),
                                JLThemeBase.colorPrimary.copy(alpha = 0.1f),
                            )
                        ),
                        shape = RoundedCornerShape(50.dp)
                    )
                    .border(
                        brush = Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            )
                        ),
                        width = Dimens.dp1,
                        shape = RoundedCornerShape(50.dp)
                    ),
                elevation = FloatingActionButtonDefaults.elevation(0.dp)

            ) {

                Icon(Icons.Rounded.Settings, "settings")

            }
        }*/
    ) {
        LazyColumn(

            modifier = Modifier
                .consumeWindowInsets(it)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),

                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.02f),
                        )
                    )
                ),
//                .fillMaxSize()
//                .background(JLThemeBase.colorPrimary10)
//                .consumedWindowInsets

//                .padding(start = Dimens.dp32, end = Dimens.dp32),
            contentPadding = it,
            state = lazyListState
        ) {
            // Header
            item {
                Box(
                    modifier = Modifier.padding(
                        start = Dimens.dp32,
                        end = Dimens.dp32
                    ),
                ) {
                    SensorDetailHeader(pagerState, coroutineScope)
                }
            }
            item {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .height(Dimens.dp350)
                        .background(color = Color.Transparent)
                ) { page ->

                    if (page == 0 && sensorState.value != null) {
                        SensorChart(
                            sensorState.value!!,
                            mpChartDataManager= viewModel.getChartDataManager(sensorState.value!!.type),
                            sensorPacketFlow = viewModel.mSensorPacketFlow
                        )
                    }
                    /* Text(
                         text = tabItems[page],
                         modifier = Modifier.padding(Dimens.dp64),
                         color = Color.White
                     )*/
                }
            }

            // Plotting area
            item {
                Spacer(modifier = Modifier.height(Dimens.dp16))
            }

            item {
                Box(
                    modifier = Modifier.padding(
                        start = Dimens.dp32,
                        end = Dimens.dp32
                    ),
                ) { SensorDetailCurrentValue(sensorType = type , value = sensorRmsState.value) }
            }

            item {
                Spacer(modifier = Modifier.height(Dimens.dp12))
            }

            item {
                Box(
                    modifier = Modifier.padding(
                        start = Dimens.dp32,
                        end = Dimens.dp32
                    ),
                ) {
                    SensorDetail(sensorState.value?.info ?: mutableMapOf())
                }
            }

            item {
                Spacer(modifier = Modifier.height(Dimens.dp72))
            }
        }
    }

}