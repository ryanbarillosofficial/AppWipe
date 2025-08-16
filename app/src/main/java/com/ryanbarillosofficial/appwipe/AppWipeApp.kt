package com.ryanbarillosofficial.appwipe

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ryanbarillosofficial.appwipe.ui.navigation.ScreenRoute
import com.ryanbarillosofficial.appwipe.ui.page.home.HomeScreen
import com.ryanbarillosofficial.appwipe.ui.page.select_apps.SelectAppsScreen

@Composable
fun AppWipeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Home.route,
    ) {
        composable(route = ScreenRoute.Home.route) {
            HomeScreen(
                navController = navController,
                modifier = modifier
            )
        }
        composable(route = ScreenRoute.SelectApps.route) {
            // Pass a Context instance to get PackageManager
            val context = LocalContext.current
            SelectAppsScreen(
                modifier = modifier,
                context = context
            )
        }
    }
}