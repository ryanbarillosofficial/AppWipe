package com.ryanbarillosofficial.appwipe.ui.page.select_apps

import com.ryanbarillosofficial.appwipe.data.application.AppInfo
import com.ryanbarillosofficial.appwipe.data.application.ApplicationInfoWrapper

data class SelectAppsUiState(
    val selectedApps: Set<AppInfo> = emptySet(),
    val installedApps: List<ApplicationInfoWrapper> = emptyList(),
    val showSystemApps: Boolean = false,
    val isLoading: Boolean = false
    )
