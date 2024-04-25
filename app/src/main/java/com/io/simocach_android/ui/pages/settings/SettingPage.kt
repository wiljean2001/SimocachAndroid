package com.io.simocach_android.ui.pages.settings

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.io.simocach_android.ui.resources.values.Dimens
import com.io.simocach_android.ui.resources.values.TxtStyles

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class, ExperimentalTextApi::class
)
@Preview(showBackground = true, backgroundColor = 0xFF041B11)
@Composable
fun SettingPage(modifier: Modifier = Modifier, navController: NavController? = null) {

    val lazyListState = rememberLazyListState()

//    val uriHandler = LocalUriHandler.current

    val context = LocalContext.current
    val sharedPreferences =
        remember { context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE) }
    var websocketIp by remember {
        mutableStateOf(
            sharedPreferences.getString(
                "websocket_ip",
                "192.168.4.1"
            ) ?: "192.168.4.1"
        )
    }

    Scaffold(topBar = {

        TopAppBar(
            title = {
                Text(
                    text = "Configuraciones",
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
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = websocketIp,
                    onValueChange = {
                        websocketIp = it
                        val editor = sharedPreferences.edit()
                        editor.putString("websocket_ip", websocketIp)
                        editor.apply()
                    },
                    label = { Text("WebSocket IP") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("192.168.4.1") },
                )
            }
        }
    }

}