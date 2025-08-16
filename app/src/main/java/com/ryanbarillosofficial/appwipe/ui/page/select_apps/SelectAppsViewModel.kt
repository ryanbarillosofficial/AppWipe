package com.ryanbarillosofficial.appwipe.ui.page.select_apps

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanbarillosofficial.appwipe.data.application.ApplicationInfoWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectAppsViewModel(val packageManager: PackageManager): ViewModel() {


    // StateFlow for UI state
    private val _uiState = MutableStateFlow(SelectAppsUiState())
    private var _installedApps: List<ApplicationInfoWrapper> = emptyList()
    val uiState: StateFlow<SelectAppsUiState> = _uiState.asStateFlow()

    // Fetch installed apps
    init {
        viewModelScope.launch {
            fetchInstalledApps()
        }
    }

    // Idea is to do get the list of installed apps in the background
    // Using coroutines is recommended, but currently this is my implementation
    private suspend fun fetchInstalledApps() = withContext(Dispatchers.IO) {
        _uiState.update { currentState -> currentState.copy(isLoading = true) }
        val installedApps: List<ApplicationInfoWrapper> = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                .mapNotNull { application -> ApplicationInfoWrapper(
                    packageName = application.packageName,
                    label = packageManager.getApplicationLabel(application).toString(),
                    isSystemApp = (application.flags and ApplicationInfo.FLAG_SYSTEM) != 0,
                    icon = packageManager.getApplicationIcon(application).toBitmap()
                        .asImageBitmap()
                )
                }.sortedBy { appInfo -> appInfo.label.lowercase() }

        _installedApps = installedApps
        _uiState.update { currentState ->
            currentState.copy(
                installedApps = when(currentState.showSystemApps) {
                    true -> installedApps
                    false -> installedApps.filter { appInfo -> !appInfo.isSystemApp }
                },
                isLoading = false)
        }
    }

    // Add the selected app from screen to the view model list
    fun selectApp(applicationInfo: ApplicationInfoWrapper) {
        val app = applicationInfo.toAppInfo()
        val newIsSelectedValue = !applicationInfo.isSelected


        _uiState.update { currentState ->
            // Add or remove the app from the list
            val updatedSelectedApps = when (currentState.selectedApps.contains(app)) {
                true -> currentState.selectedApps - app
                false -> currentState.selectedApps + app
            }
            // Update the installed apps list to reflect that the current app has been selected (for use by the UI)
            val updatedInstalledApps = currentState.installedApps.map { application ->
                if (application.packageName == applicationInfo.packageName) {
                    application.toggleIsSelected()
                } else {
                    application
                }
            }
            currentState.copy(selectedApps = updatedSelectedApps, installedApps = updatedInstalledApps)
        }
    }

    fun showSystemApps() {
        _uiState.update { currentState ->
            currentState.copy(showSystemApps = !currentState.showSystemApps, selectedApps = emptySet())
        }
        viewModelScope.launch {
            fetchInstalledApps()
        }
    }
}