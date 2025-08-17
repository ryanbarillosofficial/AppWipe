package com.ryanbarillosofficial.appwipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ryanbarillosofficial.appwipe.ui.theme.AppWipeTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppWipeTheme {
                AppWipeApp()
            }
        }
    }
}