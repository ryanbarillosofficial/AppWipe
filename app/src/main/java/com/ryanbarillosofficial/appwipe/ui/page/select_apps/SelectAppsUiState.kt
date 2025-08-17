package com.ryanbarillosofficial.appwipe.ui.page.select_apps

import com.ryanbarillosofficial.appwipe.model.application.AppInfo
import com.ryanbarillosofficial.appwipe.model.application.ApplicationInfoWrapper

data class SelectAppsUiState(
    val selectedApps: Set<AppInfo> = emptySet(),
    val installedApps: List<ApplicationInfoWrapper> = emptyList(),
    val showSystemApps: Boolean = false,
    val isLoading: Boolean = false
    )
