package com.ryanbarillosofficial.appwipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ryanbarillosofficial.appwipe.ui.component.AppWipeTopAppBar
import com.ryanbarillosofficial.appwipe.ui.modifierWithPaddingOnStatusAndNavigationBars
import com.ryanbarillosofficial.appwipe.ui.page.home.HomeScreen
import com.ryanbarillosofficial.appwipe.ui.theme.AppWipeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppWipeTheme {
                Scaffold(
                    topBar = {
                        AppWipeTopAppBar()
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppWipeApp(
                        modifier = modifierWithPaddingOnStatusAndNavigationBars.padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun AppWipeApp(modifier: Modifier = Modifier) {
    HomeScreen(
        modifier = modifier
    )
}