package com.ryanbarillosofficial.appwipe.data.application
import android.content.pm.ApplicationInfo

// Wrapper to make ApplicationInfo more UI-friendly or add properties
data class ApplicationInfoWrapper(
    val appInfo: ApplicationInfo,
    val displayName: String
    // You could add other processed info here, like isSelected, icon Drawable, etc.
)
