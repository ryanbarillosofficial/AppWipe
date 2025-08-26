package com.ryanbarillosofficial.appwipe

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ryanbarillosofficial.appwipe.data.local.model.navigation.ScreenRoute
import com.ryanbarillosofficial.appwipe.ui.page.home.HomeScreen
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import com.ryanbarillosofficial.appwipe.ui.page.select_apps.SelectAppsScreen
import com.ryanbarillosofficial.appwipe.ui.page.select_apps.review.ReviewScreen

@Composable
fun AppWipeApp(
    navController: NavHostController = rememberNavController(),
    windowSize: WindowSizeClass
) {

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.Home.route,
    ) {
        /**
         * Home Screen
         *
         * Animation for navigation:
         * - On enter: Slide to the right
         * - On exit: Slide to the left
         */
        composable(
            route = ScreenRoute.Home.route,
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) }
        ) {
            HomeScreen(navController = navController)
        }
        /**
         * Select Apps Screen
         *
         * Animation for navigation:
         * - On enter: Slide to the left
         * - On exit: Slide to the right
         */
        composable(
            route = ScreenRoute.SelectApps.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },     // from HomeScreen -> Slide from right to left
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },     // to ReviewScreen -> Slide from right to left
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) }, // from ReviewScreen -> Slide from left to right
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }    // to HomeScreen -> Slide from left to right
        ) {
            /**
             * Pass a Context instance to get PackageManager
             * This is required to get a list of installed apps
             * and to uninstall apps
             */
//            val context = LocalContext.current
            SelectAppsScreen(
                routeTitle = ScreenRoute.SelectApps.title,
                navigateUp = { navController.popBackStack() },
                navigateForward = { navController.navigate(ScreenRoute.Review.route) }
            )
        }
        composable(
            route = ScreenRoute.Review.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) {
            ReviewScreen(
                routeTitle = ScreenRoute.Review.title,
                navigateUp = { navController.popBackStack() },
                navigateForward = { navController.popBackStack(ScreenRoute.Home.route, inclusive = false) }
            )
        }
    }
}