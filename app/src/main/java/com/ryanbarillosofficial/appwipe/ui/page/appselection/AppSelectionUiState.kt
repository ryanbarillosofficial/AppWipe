package com.ryanbarillosofficial.appwipe.ui.page.appselection

import com.ryanbarillosofficial.appwipe.data.application.ApplicationInfoWrapper

data class AppSelectionUiState(
    val installedApps: List<ApplicationInfoWrapper> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
