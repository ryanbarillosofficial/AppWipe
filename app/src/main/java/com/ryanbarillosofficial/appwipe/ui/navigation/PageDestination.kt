package com.ryanbarillosofficial.appwipe.ui.navigation

sealed class PageDestination(val route: String) {
    object Home: PageDestination("home")
    object AppSelection: PageDestination("app_selection")

}