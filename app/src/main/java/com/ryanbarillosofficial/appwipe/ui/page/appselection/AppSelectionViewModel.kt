package com.ryanbarillosofficial.appwipe.ui.page.appselection

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.ryanbarillosofficial.appwipe.data.application.ApplicationInfoWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.mapNotNull
import kotlin.text.lowercase

class AppSelectionViewModel(private val packageManager: PackageManager): ViewModel() {
    private val _uiState = MutableStateFlow(AppSelectionUiState())
    val uiState: StateFlow<AppSelectionUiState> = _uiState.asStateFlow()

    init {
        fetchInstalledApps()
    }

    fun fetchInstalledApps() {
    }
}