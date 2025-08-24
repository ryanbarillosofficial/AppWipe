package com.ryanbarillosofficial.appwipe.data.local.model.application

import android.graphics.drawable.Drawable
import com.ryanbarillosofficial.appwipe.data.local.database.AppInfoEntity

data class ApplicationInfoWrapper(
    val label: String,
    val icon: Drawable,
    val packageName: String,
    val isSystemApp: Boolean,
    val isSelected: Boolean = false,
    val isArchived: Boolean = false,

    ) {
    fun toAppInfoEntity(): AppInfoEntity = AppInfoEntity(
        label = label,
        packageName = packageName,
        isSystemApp = isSystemApp
    )
    fun toggleIsSelected(): ApplicationInfoWrapper = this.copy(isSelected = !this.isSelected)
}
