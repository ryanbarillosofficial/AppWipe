package com.ryanbarillosofficial.appwipe.ui.page.select_apps.review

import androidx.lifecycle.ViewModel
import com.ryanbarillosofficial.appwipe.data.local.repository.SelectAppsRepository
import com.ryanbarillosofficial.appwipe.data.local.model.application.ApplicationInfoWrapper
import com.ryanbarillosofficial.appwipe.ui.page.select_apps.review.components.ReviewScreenType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val selectAppsRepository: SelectAppsRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState: StateFlow<ReviewUiState> = _uiState.asStateFlow()

    init {
        setAllAppsSet()
    }

    private fun setAllAppsSet() {
        val listOfUserApps: MutableSet<ApplicationInfoWrapper> = mutableSetOf()
        val listOfSystemApps: MutableSet<ApplicationInfoWrapper> = mutableSetOf()

        selectAppsRepository.getSelectedApps().forEach { applicationInfo ->
            if (applicationInfo.isSystemApp) {
                listOfSystemApps.add(applicationInfo)
            } else {
                listOfUserApps += applicationInfo
            }

        _uiState.update { currentState -> currentState.copy(
            listOfUserApps = listOfUserApps,
            listOfSystemApps = listOfSystemApps
        ) }
        }
    }

    fun setCurrentScreenToShow() {
        _uiState.update { currentState -> currentState.copy(
            currentScreenToShow = if (currentState.currentScreenToShow == ReviewScreenType.User.name) {
                ReviewScreenType.System.name
            } else {
                ReviewScreenType.User.name
            }
        ) }
    }

    fun getCurrentScreenToShow(): String {
        return uiState.value.currentScreenToShow
    }
}