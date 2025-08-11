package com.ryanbarillosofficial.appwipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ryanbarillosofficial.appwipe.ui.component.AppWipeTopAppBar
import com.ryanbarillosofficial.appwipe.ui.modifierWithPaddingOnStatusAndNavigationBars
import com.ryanbarillosofficial.appwipe.ui.navigation.PageDestination
import com.ryanbarillosofficial.appwipe.ui.page.appselection.AppSelection
import com.ryanbarillosofficial.appwipe.ui.page.home.Home
import com.ryanbarillosofficial.appwipe.ui.theme.AppWipeTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppWipeTheme {
                Scaffold(
                    topBar = {
                        AppWipeTopAppBar()
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppWipeApp(
                        modifier = modifierWithPaddingOnStatusAndNavigationBars.padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

// Navigation stuffs
enum class AppWipeNavigationAddress() {
    Home,
    AppSelection
}

@Composable
fun AppWipeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = PageDestination.Home.route,
    ) {
        composable(route = PageDestination.Home.route) {
            Home(
                navController = navController,
                modifier = modifier
            )
        }
        composable(route = PageDestination.AppSelection.route) {
            AppSelection(modifier = modifier)
        }
    }
}