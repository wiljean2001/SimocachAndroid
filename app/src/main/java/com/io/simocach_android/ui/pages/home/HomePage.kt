package com.io.simocach_android.ui.pages.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.io.simocach_android.R
import com.io.simocach_android.ui.pages.home.items.*
import com.io.simocach_android.ui.pages.home.sections.*
import com.io.simocach_android.ui.navigation.NavDirectionsApp
import com.io.simocach_android.ui.resources.theme.ThemeBase
import com.io.simocach_android.ui.resources.values.Dimens
import com.io.simocach_android.ui.resources.values.Shapes
import com.io.simocach_android.ui.resources.values.TxtStyles
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class
//    ExperimentalLayoutApi::class, ExperimentalTextApi::class,
//    ExperimentalAnimationApi::class, // ExperimentalPagerApi::class
)
@Preview(showBackground = true, backgroundColor = 0xFF0C100E) // 0xFF041B11
@Composable
fun HomePage(
    modifier: Modifier = Modifier, navController: NavController? = null,
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.Factory()
    )

) {

    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
//    val sensorsProvider = SensorsProviderComposable()
//    val sensors = remember { sensorsProvider }

    val sensorUiState = viewModel.mUiState.collectAsState()
//    var sensorUiState = viewModel.mUiCurrentSensorState.collectAsState()

    val isAtTop = remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex == 0 && lazyListState.firstVisibleItemScrollOffset == 0
        }
    }

    val sd = viewModel.mActiveSensorListFlow.collectAsState(initial = mutableListOf())

    val activeSensorStateList = remember { sd }

    val pagerState = rememberPagerState(
        pageCount = { activeSensorStateList.value.size },
    )
// TODOS:



    Scaffold(topBar = {

        TopAppBar(

//            backgroundColor = Color.Transparent,
            colors = if (!isAtTop.value) TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f), //Add your own color here, just to clarify.
            ) else TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Transparent //Add your own color here, just to clarify.
            ),


            navigationIcon = {
                Box(Modifier.padding(horizontal = Dimens.dp20)) {
                    IconButton(onClick = { navController?.navigate(NavDirectionsApp.SettingPage.route) }) {
                        Image(
                            painterResource(id = R.drawable.pic_logo),
                            modifier = Modifier
                                .width(Dimens.dp32)
                                .height(Dimens.dp36),
    //                            .clickable { navController?.navigate(NavDirectionsApp.SettingPage.route) },
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )

                    }
                }

            },
            title = {
                Text(
                    text = "Simocach",
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    style = TxtStyles.h4,
                    fontWeight = FontWeight(400),
                    modifier = modifier.fillMaxWidth(),
                )
            },
            actions = {
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
            },

            )
    },
        floatingActionButton = {

            AnimatedVisibility(
                visible = lazyListState.isScrollInProgress,
//                modifier = Modifier.fillMaxSize(),
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                FloatingActionButton(
                    onClick = { navController?.navigate(NavDirectionsApp.AboutPage.route) },
                    shape = RoundedCornerShape(50),
                    containerColor = Color.Transparent,

                    modifier = Modifier
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    ThemeBase.colorPrimary.copy(alpha = 0.3f),
                                    ThemeBase.colorPrimary.copy(alpha = 0.1f),
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
                    elevation = FloatingActionButtonDefaults.elevation(Dimens.dp0)

                ) {

                    Icon(Icons.Rounded.Info, "about")

                }
            }
        }

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
//                .background(ThemeBase.colorPrimary10)
//                .consumedWindowInsets ,
            contentPadding = it,
            state = lazyListState
        ) {

            item {
                Spacer(modifier = Shapes.Space.H24)
            }
            // Header
            item {
                Box(
                    modifier = Modifier.padding(
                        start = Dimens.dp32,
                        end = Dimens.dp32
                    ),
                ) {
                    HomeHeader(
                        sensorUiState.value.currentSensor,
                        totalActive = sensorUiState.value.activeSensorCounts,
                        onClickArrow = { isLeft ->

                            val currentPage = pagerState.currentPage
//                            var totalPage = pagerState.pageCount
                            val totalPage = sensorUiState.value.activeSensorCounts

                            if (!isLeft && currentPage + 1 < totalPage) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(currentPage + 1)
                                }
                            } else if (isLeft && currentPage > 0 && totalPage > 0) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(currentPage - 1)
                                }
                            }
                        }
                    )
                }
            }
            // Plotting area
            item {
//                Spacer(modifier = Modifier.height(Dimens.dp350))

                HomeSensorGraphPager(viewModel = viewModel, pagerState = pagerState)

            }

            // Available Sensors
            item {
                Box(
                    modifier = Modifier
                        .padding(
                            start = Dimens.dp40, end = Dimens.dp32,
                            top = Dimens.dp12, bottom = Dimens.dp16
                        ),
                ) {
                    Text(
                        text = "Available Sensors",
                        fontSize = Dimens.sp16,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(Dimens.dp16)) }

            items(viewModel.mSensorsList.windowed(2, 2, true)) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.dp32)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,


                        ) {

                        for (i in item.indices) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
//                                    .fillParentMaxWidth(0.5f)
//                                    .padding(bottom = Dimens.dp8)
                                /*.clickable(
                                    enabled = true,
                                    onClickLabel = "Card Click",
                                    onClick = {
                                        navController?.navigate(NavDirectionsLabs.DetailPage.route)
                                    }
                                )*/

                            ) {
                                HomeSensorItem(
                                    modelSensor = item[i],
                                    /* se = item[i].sensorName,
                                     sensorValue = item[i].sensorValue,
                                     sensorUnit = item[i].sensorUnit,
                                     sensorIcon = item[i].sensorIcon*/

                                    onCheckChange = { type: Int, isChecked: Boolean ->
                                        viewModel.onSensorChecked(type, isChecked)

                                    },
                                    onClick = {
                                        navController?.navigate("${NavDirectionsApp.SensorDetailPage.route}/${it}")
                                    }
                                )

                            }

                            if (i < item.size - 1) {
                                Spacer(modifier = Modifier.width(Dimens.dp8))
                            }
                        }
                        if (item.size % 2 != 0) {
                            Spacer(modifier = Modifier.width(Dimens.dp8))

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                            )
                        }

                    }
                }
                Spacer(modifier = Modifier.height(Dimens.dp8))

            }
        }
    }

}

