package com.ryanbarillosofficial.appwipe.data.local.model.application

data class AppInfo(
    val label: String,
    val packageName: String,
    val isSystemApp: Boolean = false,
)
