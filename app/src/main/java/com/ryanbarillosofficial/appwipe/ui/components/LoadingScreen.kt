package com.ryanbarillosofficial.appwipe.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ryanbarillosofficial.appwipe.R
import com.ryanbarillosofficial.appwipe.ui.theme.AppWipeTheme

/**
 * Reference(s):
 * https://developer.android.com/develop/ui/compose/quick-guides/content/create-progress-indicator
 */
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    loadingText: String = stringResource(R.string.loading),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = loadingText,
                style = MaterialTheme.typography.headlineLarge
            )
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    AppWipeTheme {
        LoadingScreen(modifier = Modifier.fillMaxSize())
    }
}