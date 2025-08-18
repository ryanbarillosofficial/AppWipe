package com.ryanbarillosofficial.appwipe.ui.page.select_apps

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanbarillosofficial.appwipe.data.local.repository.SelectAppsRepository
import com.ryanbarillosofficial.appwipe.data.local.model.application.ApplicationInfoWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SelectAppsViewModel @Inject constructor(
    val selectedAppsRepository: SelectAppsRepository,
    app: Application,
    ): ViewModel() {

    // Some important stuffs
    private val packageManager = app.packageManager
    private val packageName = app.packageName


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
        if (_installedApps.isEmpty()) {
            _uiState.update { currentState -> currentState.copy(isLoading = true) }
            /**
             * The function in a nutshell:
             * - Get all installed apps
             * - Filter the current app out
             * - Cast each instance (from ApplicationInfo) to ApplicationInfoWrapper
             * - Sort them alphabetically
             */
            val installedApps: List<ApplicationInfoWrapper> =
                packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                    .filterNot { applicationInfo -> applicationInfo.packageName == packageName }
                    .mapNotNull { application ->
                        ApplicationInfoWrapper(
                            packageName = application.packageName,
                            label = packageManager.getApplicationLabel(application).toString(),
                            isSystemApp = (application.flags and ApplicationInfo.FLAG_SYSTEM) != 0,
                            icon = packageManager.getApplicationIcon(application)
//                            icon = packageManager.getApplicationIcon(application).toBitmap().asImageBitmap()
                        )
                    }
                    .sortedBy { appInfo -> appInfo.label.lowercase() }
            // Cache the entire app list internally
            // For reasons such as doing filters
            _installedApps = installedApps
        }
        // Initially, provide the app list with all system apps filtered
        // Let the user decide that option
        _uiState.update { currentState ->
            currentState.copy(
                installedApps = when(currentState.showSystemApps) {
                    true -> _installedApps
                    false -> _installedApps.filter { appInfo -> !appInfo.isSystemApp }
                },
                isLoading = false)
        }
    }

    // Add the selected app from screen to the view model list
    fun selectApp(application: ApplicationInfoWrapper) {

        _uiState.update { currentState ->
            // Add or remove the app from the list
            val updatedSelectedApps = if (currentState.selectedApps.any({ it.packageName == application.packageName })) {
                (currentState.selectedApps.filterNot { it.packageName == application.packageName }).toSet()
            } else {
                currentState.selectedApps + application
            }
            // Update the installed apps list to reflect that the current app has been selected (for use by the UI)
            val updatedInstalledApps = currentState.installedApps.map { installedApp ->
                if (installedApp.packageName == application.packageName) {
                    installedApp.toggleIsSelected()
                } else {
                    installedApp
                }
            }
            Log.d("SelectAppsViewModel", "Selected Apps: $updatedSelectedApps")
            currentState.copy(selectedApps = updatedSelectedApps, installedApps = updatedInstalledApps)
        }
    }

    fun showSystemApps() {
        _uiState.update { currentState ->
            currentState.copy(showSystemApps = !currentState.showSystemApps)
        }
        clearSelectedApps()
        viewModelScope.launch {
            fetchInstalledApps()
        }
    }
    fun clearSelectedApps() {

        _uiState.update { currentState ->
            // Update the installed apps list to reflect that the current app has been selected (for use by the UI)
            val updatedInstalledApps = currentState.installedApps.map { application ->
                if (application.isSelected) {
                    application.toggleIsSelected()
                } else {
                    application
                }
            }
            currentState.copy(selectedApps = emptySet(), installedApps = updatedInstalledApps)
        }
    }

    /**
     * HILT REQUIRED:
     *
     * Transfer the selectedApps to the repository
     * for the next screen to use
     */
    fun navigateForward(navigateToNextScreen: () -> Unit) {
        selectedAppsRepository.updateSelectedApps(_uiState.value.selectedApps)
        navigateToNextScreen()
    }
}