package com.ryanbarillosofficial.appwipe.ui.page.home.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.ryanbarillosofficial.appwipe.R
import com.ryanbarillosofficial.appwipe.ui.theme.AppWipeTheme

@Composable
fun HomeText(countOfAppsBlocked: Int) {
    val text: String = when(countOfAppsBlocked){
        0 -> stringResource(R.string.no_apps_blocked_message)
        else -> "Hooray!\nThere are $countOfAppsBlocked apps blocked!"
    }
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable()
fun HomeTextPreview() {
    AppWipeTheme {
        HomeText(5)
    }
}