package com.ryanbarillosofficial.appwipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ryanbarillosofficial.appwipe.ui.component.AppWipeTopAppBar
import com.ryanbarillosofficial.appwipe.ui.navigation.ScreenRoute
import com.ryanbarillosofficial.appwipe.ui.theme.AppWipeTheme
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppWipeTheme {
                MainActivityScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityScreen() {
    // Navigation States
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = ScreenRoute.getRoute(backStackEntry?.destination?.route)

    // Top app bar scroll behavior
    val scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    // Top App bar title texts
    var collapsedTitle by remember { mutableStateOf<Any>(currentRoute.title) }
    var expandedTitle by remember { mutableStateOf<Any>(currentRoute.title) }

    LaunchedEffect(currentRoute) {
        // Reset the scroll state of the TopAppBar
        // This brings it back to the fully expanded state.
        scrollBehavior.state.heightOffset = 0f
        scrollBehavior.state.contentOffset = 0f

        // If you're managing titles based on route, update them here too
        // (This part depends on your title management strategy)
        // For example, if currentRoute.title is a String:
        // if (currentRoute.title is String) {
        //    collapsedTitle = currentRoute.title
        //    expandedTitle = currentRoute.title
        // }
    }

    Scaffold(
        topBar = {
            AppWipeTopAppBar(
                expandedTitle = expandedTitle,
                collapsedTitle = collapsedTitle,
                scrollBehavior = scrollBehavior,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        AppWipeApp(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            onTitlesChange = { newCollapsedTitle, newExpandedTitle ->
                collapsedTitle = newCollapsedTitle
                expandedTitle = newExpandedTitle
            }
        )
    }
}
