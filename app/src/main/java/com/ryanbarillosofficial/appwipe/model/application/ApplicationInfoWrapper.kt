package com.ryanbarillosofficial.appwipe.model.application

import androidx.compose.ui.graphics.ImageBitmap

data class ApplicationInfoWrapper(
    val label: String,
    val icon: ImageBitmap,
    val packageName: String,
    val isSystemApp: Boolean,
    val isSelected: Boolean = false,

) {
    fun toAppInfo(): AppInfo = AppInfo(label = label, packageName = packageName)
    fun toggleIsSelected(): ApplicationInfoWrapper = this.copy(isSelected = !this.isSelected)
}
