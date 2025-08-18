package com.ryanbarillosofficial.appwipe.ui.page.select_apps.review

import com.ryanbarillosofficial.appwipe.data.local.model.application.ApplicationInfoWrapper
import com.ryanbarillosofficial.appwipe.ui.page.select_apps.review.components.ReviewScreenType

data class ReviewUiState(
    val listOfUserApps: Set<ApplicationInfoWrapper> = emptySet(),
    val listOfSystemApps: Set<ApplicationInfoWrapper> = emptySet(),
    val currentScreenToShow: String = ReviewScreenType.User.name
)