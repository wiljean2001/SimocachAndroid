package com.io.simocach_android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.io.simocach_android.domains.sensors.MySensor
import com.io.simocach_android.ui.pages.SplashPage
import com.io.simocach_android.ui.pages.home.HomePage
import com.io.simocach_android.ui.pages.sensor.SensorPage

sealed class NavDirectionsApp(val route: String) {
    data object Root : NavDirectionsApp("root")
    data object HomePage : NavDirectionsApp("home_page")
    data object SensorDetailPage : NavDirectionsApp("sensor_detail_page")
    data object AboutPage : NavDirectionsApp("about_page")
    data object Splash : NavDirectionsApp("splash_page")
}

@Composable
fun NavGraphApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavDirectionsApp.Splash.route) {
        // startDestination "labs1"
//        val viewModelHome: HomeViewModel = HomeViewModel()
//        val viewModelSensor: SensorViewModel = SensorViewModel()
        composable(NavDirectionsApp.Splash.route) { SplashPage(navController) }
        composable(NavDirectionsApp.HomePage.route) {
            HomePage(
                navController = navController
            )
        }
        composable("${NavDirectionsApp.SensorDetailPage.route}/{type}", listOf(navArgument("type") {
            type = NavType.IntType
        })) {
            SensorPage(
                navController = navController,
                type = it.arguments?.getInt("type") ?: MySensor.TYPE_TEMPERATURE,
                // TODO might be creating bug

            )
        }
//        composable(NavDirectionsApp.AboutPage.route) { AboutPage(navController = navController) }
    }

}