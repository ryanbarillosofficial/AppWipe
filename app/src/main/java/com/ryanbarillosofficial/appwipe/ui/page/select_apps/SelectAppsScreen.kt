package com.ryanbarillosofficial.appwipe.ui.page.select_apps

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ryanbarillosofficial.appwipe.R
import com.ryanbarillosofficial.appwipe.ui.components.AppWipeTopAppBar
import com.ryanbarillosofficial.appwipe.ui.components.paddingGap
import com.ryanbarillosofficial.appwipe.ui.page.select_apps.components.ApplicationInfoCard
import kotlinx.coroutines.launch
import androidx.hilt.navigation.compose.hiltViewModel
import com.ryanbarillosofficial.appwipe.ui.components.LoadingScreen

/**
 * Reference(s) needed to complete this page:
 * https://developer.android.com/reference/android/content/pm/PackageManager
 * https://tomas-repcik.medium.com/listing-all-installed-apps-in-android-13-via-packagemanager-3b04771dc73
 * e
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAppsScreen(
    @StringRes routeTitle: Int,
    context: Context,
    viewModel: SelectAppsViewModel = hiltViewModel(),
    navigateForward: () -> Unit = { },
    navigateUp: () -> Unit = { },
) {
    // View Model
    val uiState by viewModel.uiState.collectAsState()
    val selectedAppsCount = uiState.selectedApps.size

    // Top bar scroll state
    val screenTitle: Any = if (selectedAppsCount > 0) {
        "$selectedAppsCount selected"
    } else {
        routeTitle
    }
    // Top App Bar State
    val scrollBehavior = if (uiState.isLoading) {
        TopAppBarDefaults.pinnedScrollBehavior()
    } else {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    }

    // Navigation state
    val actualNavigateUp: () -> Unit = if (selectedAppsCount > 0) {
        { viewModel.clearSelectedApps() }
    } else {
        navigateUp
    }
    // Handle system back press
    // BackHandler should be enabled only when there's a custom action to perform
    // or if you want to conditionally prevent back navigation.
    // In this case, it's always enabled to apply the custom logic.
    BackHandler(enabled = true) { // Or conditionally enable: enabled = selectedAppsCount > 0
        actualNavigateUp()
    }

    // Dropdown Menu for the top bar
    var showDropdownMenu by remember { mutableStateOf<Boolean>(false) }
    val systemAppsToggleText = if (uiState.showSystemApps) {
        stringResource(R.string.hide_system_apps)
    } else {
        stringResource(R.string.show_system_apps)
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppWipeTopAppBar(
                collapsedTitle = screenTitle,
                scrollBehavior = scrollBehavior,
                navigateUp = actualNavigateUp,
                actions = {
                    if (!uiState.isLoading) {
                        IconButton(onClick = { showDropdownMenu = !showDropdownMenu }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = stringResource(R.string.more_options)
                            )
                            DropdownMenu(
                                expanded = showDropdownMenu,
                                onDismissRequest = { showDropdownMenu = false },
                                shape = RoundedCornerShape(16.dp),

                                ) {
                                DropdownMenuItem(
                                    text = { Text(text = systemAppsToggleText) },
                                    onClick = {
                                        viewModel.showSystemApps()
                                        coroutineScope.launch {
                                            listState.scrollToItem(index = 0)
                                        }
                                        showDropdownMenu = false
                                    }

                                )
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (selectedAppsCount > 0) {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.navigateForward(navigateForward) },
                    icon = { Icon(
                        imageVector = Icons.Filled.RateReview,
                        contentDescription = stringResource(R.string.review_description)
                    ) },
                    text = { Text(stringResource(R.string.review_title)) },
                )
            }
        }
    )
    { innerPadding ->
        if (uiState.isLoading) {
            LoadingScreen(modifier = Modifier.fillMaxSize())
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(paddingGap),
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(paddingGap)
            ) {
//        Spacer(modifier = Modifier.height(paddingGap))
//        Text(text = "Last Selected App: $lastSelectedApp")
//            Text(
//                text = "More Options",
//                modifier = Modifier.align(Alignment.Start)
//            )
                // Scroll to top when showSystemApps changes
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(paddingGap)
                ) {
                    items(items = uiState.installedApps) { applicationInfo ->
                        ApplicationInfoCard(
                            icon = applicationInfo.icon,
                            label = applicationInfo.label,
                            isSelected = applicationInfo.isSelected,
                            onClick = {
                                viewModel.selectApp(applicationInfo)
                            }
                        )
                    }
                }
            }
        }
    }
}



//@Preview(showBackground = true)
//@Composable()
//fun AppSelectionPreview() {
//    AppWipeTheme {
//        SelectAppsScreen(context = LocalContext.current)
//    }
//}