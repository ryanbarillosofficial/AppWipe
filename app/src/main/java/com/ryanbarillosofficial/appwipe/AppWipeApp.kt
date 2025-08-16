package com.ryanbarillosofficial.appwipe

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ryanbarillosofficial.appwipe.ui.navigation.ScreenRoute
import com.ryanbarillosofficial.appwipe.ui.paddingGap
import com.ryanbarillosofficial.appwipe.ui.page.home.HomeScreen
import com.ryanbarillosofficial.appwipe.ui.page.select_apps.SelectAppsScreen

@Composable
fun AppWipeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onTitlesChange: (Any, Any) -> Unit = { _, _ -> },
) {
    val newModifier = modifier.padding(horizontal = paddingGap)
//    val newModifier = modifier
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Home.route,
    ) {
        composable(route = ScreenRoute.Home.route) {
            HomeScreen(
                navController = navController,
                modifier = newModifier,
                onTitlesChange = onTitlesChange
            )
        }
        composable(route = ScreenRoute.SelectApps.route) {
            // Pass a Context instance to get PackageManager
            val context = LocalContext.current
            SelectAppsScreen(
                modifier = newModifier,
                context = context,
                onTitlesChange = onTitlesChange
            )
        }
    }
}