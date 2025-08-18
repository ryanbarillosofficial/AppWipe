package com.ryanbarillosofficial.appwipe.ui.page.select_apps

import com.ryanbarillosofficial.appwipe.data.local.model.application.ApplicationInfoWrapper

data class SelectAppsUiState(
    val selectedApps: Set<ApplicationInfoWrapper> = emptySet(),
    val installedApps: List<ApplicationInfoWrapper> = emptyList(),
    val showSystemApps: Boolean = false,
    val isLoading: Boolean = false
    )
