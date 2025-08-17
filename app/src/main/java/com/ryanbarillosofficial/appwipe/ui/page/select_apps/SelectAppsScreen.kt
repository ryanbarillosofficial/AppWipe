package com.ryanbarillosofficial.appwipe.ui.page.select_apps

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ryanbarillosofficial.appwipe.R
import com.ryanbarillosofficial.appwipe.ui.components.AppWipeTopAppBar
import com.ryanbarillosofficial.appwipe.ui.paddingGap
import com.ryanbarillosofficial.appwipe.ui.page.select_apps.components.ApplicationInfoCard
import kotlinx.coroutines.launch

/**
 * Reference(s) needed to complete this page:
 * https://developer.android.com/reference/android/content/pm/PackageManager
 * https://tomas-repcik.medium.com/listing-all-installed-apps-in-android-13-via-packagemanager-3b04771dc73
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAppsScreen(
    context: Context,
    viewModel: SelectAppsViewModel = viewModel {
        SelectAppsViewModel(
            packageManager = context.packageManager,
            packageName = context.packageName
        )
    },
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
        R.string.select_apps_title
    }
    // Top App Bar State
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    // Navigation state
    val actualNavigateUp = if (selectedAppsCount > 0) {
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

//    val lastSelectedApp = when (selectedAppsCount) {
//        0 -> "None"
//        else ->  selectAppsUiState.selectedApps.last().label
//    }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppWipeTopAppBar(
                collapsedTitle = screenTitle,
                scrollBehavior = scrollBehavior,
                canNavigateBack = true,
                navigateUp = actualNavigateUp
            )
        },
        floatingActionButton = {
            if (selectedAppsCount > 0) {
                ExtendedFloatingActionButton(
                    onClick = navigateForward,
                    icon = { Icon(Icons.Filled.Add, "Add") },
                    text = { Text(text = "Done") },
                )
            }
        }
    )
    { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(paddingGap),
            modifier = Modifier
                .padding(innerPadding)
                .padding(paddingGap)
        ) {
//        Spacer(modifier = Modifier.height(paddingGap))
//        Text(text = "Last Selected App: $lastSelectedApp")
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = "Show System Apps")
//                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = uiState.showSystemApps,
                    onCheckedChange = {
                        viewModel.showSystemApps()
                        coroutineScope.launch {
                            listState.scrollToItem(index = 0)
                        }
                    }
                )
            }
            if (uiState.isLoading) {
                Text(text = "Please Wait...")
            } else {
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
            // Aim for this to make the last app selectable, not busied by the FAB
            Spacer(modifier = Modifier.height(paddingGap))
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