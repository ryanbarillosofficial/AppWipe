package com.ryanbarillosofficial.appwipe

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ryanbarillosofficial.appwipe.model.navigation.ScreenRoute
import com.ryanbarillosofficial.appwipe.ui.page.home.HomeScreen
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import com.ryanbarillosofficial.appwipe.ui.page.select_apps.SelectAppsScreen

@Composable
fun AppWipeApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Home.route,
    ) {
        composable(
            route = ScreenRoute.Home.route,
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) }
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.SelectApps.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) {
            // Pass a Context instance to get PackageManager
            // This is required to get a list of installed apps
            // and to uninstall apps
            val context = LocalContext.current
            SelectAppsScreen(
                context = context,
                navigateUp = { navController.navigateUp() },
            )
        }
    }
}