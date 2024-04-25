package com.io.simocach_android.ui.pages.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.io.simocach_android.R
import com.io.simocach_android.ui.resources.values.Dimens
import com.io.simocach_android.ui.resources.values.Shapes
import com.io.simocach_android.ui.resources.values.TxtStyles
import com.io.simocach_android.ui.pages.about.sections.AboutCommunity

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class, ExperimentalTextApi::class
)
@Preview(showBackground = true, backgroundColor = 0xFF041B11)
@Composable
fun AboutPage(modifier: Modifier = Modifier, navController: NavController? = null) {
    val lazyListState = rememberLazyListState()

    val uriHandler = LocalUriHandler.current
    Scaffold(topBar = {

        TopAppBar(
            title = {
                Text(
                    text = "",
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    style = TxtStyles.h4,
                    fontWeight = FontWeight(400),
                    modifier = modifier.fillMaxWidth(),
                )
            },
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
            //            backgroundColor = Color.Transparent,
            colors = if (lazyListState.firstVisibleItemIndex > 0) TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f), //Add your own color here, just to clarify.
            ) else TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Transparent //Add your own color here, just to clarify.
            )
        )
    }
    ) {

        Box(
            modifier = Modifier
                .consumeWindowInsets(it)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.02f),
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY

                    )
                )


        ) {

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(start = Dimens.dp32, end = Dimens.dp32),
//            con = it,

            ) {

                Spacer(modifier = com.io.simocach_android.ui.resources.values.Shapes.Space.H64)
                Image(
                    painterResource(id = R.drawable.pic_about_gradient),
                    modifier = Modifier
                        .weight(1f)
                        .alpha(0.2f),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(start = Dimens.dp32, end = Dimens.dp32),
//            con = it,

            ) {

                Spacer(modifier = Shapes.Space.H64)

                Image(
                    painterResource(id = R.drawable.pic_logo),
                    modifier = Modifier
                        .width(Dimens.dp64)
                        .height(Dimens.dp64)
                        .align(alignment = Alignment.CenterHorizontally),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(Dimens.dp24))

                Text(
                    modifier = Modifier
                        .width(width = 300.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    text = "Hola!, gracias por utilizar SIMOCACH.",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.87f),
                    textAlign = TextAlign.Center,
                    style = TxtStyles.h2,
                )

                Spacer(modifier = Modifier.weight(1f))
//            Spacer(modifier = Modifier.)

                Text(
                    modifier = Modifier
                        .width(width = 300.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    text = "¿Quieres saber más?",
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    style = TxtStyles.h3,
                )
                Spacer(modifier = Modifier.height(Dimens.dp6))
                Text(
                    modifier = Modifier
                        .width(width = 300.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    text = "No sé qué poner xd.",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center,
                    style = TxtStyles.h5,
                )

                Spacer(modifier = Modifier.height(Dimens.dp36))

                AboutCommunity()

                Spacer(modifier = Modifier.height(Dimens.dp36))

                ElevatedButton(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF030303)),
                    modifier = Modifier.height(Dimens.dp56),
                    shape = RoundedCornerShape(Dimens.dp16),
                    onClick = {
                        uriHandler.openUri("https://github.com/wiljean2001/SimocachAndroid.git")
                    }) {
                    Icon(
                        modifier = Modifier.size(size = Dimens.dp24),
                        tint = Color.White,
                        painter = painterResource(R.drawable.ic_github),
                        contentDescription = "print"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        color = Color.White,
                        text = "Github", textAlign = TextAlign.Center,
                        style = TxtStyles.h5,
                    )
                }

//                Spacer(modifier = Modifier.height(Dimens.dp12))

//                ElevatedButton(
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE1D34)),
//                    modifier = Modifier.height(Dimens.dp56),
//                    shape = RoundedCornerShape(Dimens.dp16),
//                    onClick = {
//                        uriHandler.openUri("https://junkielabs.in/")
//                    }) {
//                    Text(
//                        modifier = Modifier.weight(1f),
//                        color = Color.White,
//                        text = "Junkie Labs", textAlign = TextAlign.Center,
//                        style = TxtStyles.h5,
//                    )
//                }
                Spacer(modifier = Modifier.height(Dimens.dp28))
//
            }

        }


    }
}