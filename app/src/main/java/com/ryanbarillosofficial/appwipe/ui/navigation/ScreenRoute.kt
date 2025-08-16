package com.ryanbarillosofficial.appwipe.ui.navigation

sealed class ScreenRoute(val route: String) {
    object Home: ScreenRoute("home")
    object SelectApps: ScreenRoute("app_selection")

}