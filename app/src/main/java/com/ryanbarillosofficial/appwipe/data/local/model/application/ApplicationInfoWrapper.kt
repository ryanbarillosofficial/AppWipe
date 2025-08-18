package com.ryanbarillosofficial.appwipe.data.local.model.application

import android.graphics.drawable.Drawable

data class ApplicationInfoWrapper(
    val label: String,
    val icon: Drawable,
    val packageName: String,
    val isSystemApp: Boolean,
    val isSelected: Boolean = false,

    ) {
    fun toAppInfo(): AppInfo = AppInfo(
        label = label,
        packageName = packageName,
        isSystemApp = isSystemApp
    )
    fun toggleIsSelected(): ApplicationInfoWrapper = this.copy(isSelected = !this.isSelected)
}
