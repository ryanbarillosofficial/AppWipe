package com.ryanbarillosofficial.appwipe.ui.page.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import com.ryanbarillosofficial.appwipe.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ryanbarillosofficial.appwipe.ui.components.NavigationCardButton
import com.ryanbarillosofficial.appwipe.data.local.model.navigation.ScreenRoute
import com.ryanbarillosofficial.appwipe.ui.components.AppWipeTopAppBar
import com.ryanbarillosofficial.appwipe.ui.components.ConstrainedWidthLayout
import com.ryanbarillosofficial.appwipe.ui.components.paddingGap
import com.ryanbarillosofficial.appwipe.ui.page.home.components.HomeText
import com.ryanbarillosofficial.appwipe.ui.theme.AppWipeTheme

const val placeholderInt: Int = 0

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    navController: NavHostController,
) {
    val uiState by homeViewModel.uiState.collectAsState()
    val screenTitle = R.string.app_name

    // Top App Bar State
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    // Scroll state of the content
    val columnScrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppWipeTopAppBar(
                collapsedTitle = screenTitle,
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.verticalScroll(columnScrollState)
                .padding(innerPadding)
                .padding(paddingGap)
        ) {
            ConstrainedWidthLayout {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(paddingGap * 2),
                ) {
                    HomeText(placeholderInt)
                    // Pick Apps Button
                    NavigationCardButton(
                        titleText = stringResource(R.string.select_apps_title),
                        descriptionText = stringResource(R.string.select_apps_description),
                        iconImageVector = Icons.Default.Add,
                        onClick = { navController.navigate(ScreenRoute.SelectApps.route) },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    // View List Button
                    NavigationCardButton(
                        titleText = stringResource(R.string.view_list_title),
                        descriptionText = stringResource(R.string.view_list_description),
                        iconImageVector = Icons.AutoMirrored.Filled.ViewList,
//                onClick = { navController.navigate(ScreenRoute.ViewList.route) },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    NavigationCardButton(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable()
fun HomeScreenPreview() {
    AppWipeTheme {
        HomeScreen(navController = rememberNavController())
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=720dp,height=360dp,dpi=240"
)
@Composable()
fun HomeScreenLandscapePreview() {
    AppWipeTheme {
        HomeScreen(
//            modifier = Modifier.fillMaxSize(),
            navController = rememberNavController()
        )
    }
}