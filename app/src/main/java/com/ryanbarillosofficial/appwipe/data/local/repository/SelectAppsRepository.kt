package com.ryanbarillosofficial.appwipe.data.local.repository

import com.ryanbarillosofficial.appwipe.data.local.model.application.ApplicationInfoWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SelectAppsRepository @Inject constructor() {
    private val _selectedAppsFlow: MutableStateFlow<Set<ApplicationInfoWrapper>> = MutableStateFlow(emptySet())
    val selectedAppsFlow: StateFlow<Set<ApplicationInfoWrapper>> = _selectedAppsFlow.asStateFlow()

    fun updateSelectedApps(selectedApps: Set<ApplicationInfoWrapper>) {
        _selectedAppsFlow.value = selectedApps
    }

    fun getSelectedApps(): Set<ApplicationInfoWrapper> {
        return _selectedAppsFlow.value
    }

    fun clear() {
        _selectedAppsFlow.value = emptySet()

    }
}