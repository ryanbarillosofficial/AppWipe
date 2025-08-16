package com.ryanbarillosofficial.appwipe.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.ryanbarillosofficial.appwipe.R

sealed class ScreenRoute(
    val route: String,
    @StringRes val title: Int,
) {
    object Home: ScreenRoute(
        route = "home",
        title = R.string.app_name,
    )
    object SelectApps: ScreenRoute(
        route = "app_selection",
        title = R.string.select_apps_title,
    )
    object ViewList: ScreenRoute(
        route = "view_list",
        title = R.string.view_list_title,
    )


    /**
     * This obtains the route of the current screen.
     * For use when implementing backStackEntry for Navigation
     */
    companion object {
        fun getRoute(route: String?): ScreenRoute {
            return when (route) {
                Home.route -> Home
                SelectApps.route -> SelectApps
                else -> Home

            }
        }
    }
}