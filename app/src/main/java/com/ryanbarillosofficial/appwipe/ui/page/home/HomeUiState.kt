package com.ryanbarillosofficial.appwipe.ui.page.home

import androidx.annotation.StringRes
import com.ryanbarillosofficial.appwipe.R

data class HomeUiState(
    val countOfAppsBlocked: Int = 0,
    @param:StringRes val splashMessage: Int = R.string.no_apps_blocked_message
)