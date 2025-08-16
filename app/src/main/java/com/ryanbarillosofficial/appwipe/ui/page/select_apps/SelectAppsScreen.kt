package com.ryanbarillosofficial.appwipe.ui.page.select_apps

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Reference(s) needed to complete this page:
 * https://developer.android.com/reference/android/content/pm/PackageManager
 * https://tomas-repcik.medium.com/listing-all-installed-apps-in-android-13-via-packagemanager-3b04771dc73
 */

@Composable
fun SelectAppsScreen(
    modifier: Modifier = Modifier,
    context: Context,
    selectAppsViewModel: SelectAppsViewModel = viewModel {
        SelectAppsViewModel(context.packageManager)
    }
) {
    // View Model
    val selectAppsUiState by selectAppsViewModel.uiState.collectAsState()
    val selectedAppsCount = selectAppsUiState.selectedApps.size
    val selectedAppsCountText: String = when (selectedAppsCount) {
        0 -> "No app has been selected"
        1 -> "1 app has been selected"
        else -> "$selectedAppsCount apps have been selected"
    }
//    val lastSelectedApp = when (selectedAppsCount) {
//        0 -> "None"
//        else ->  selectAppsUiState.selectedApps.last().label
//    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Select Apps",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = selectedAppsCountText
//            text = "Selected Apps: $selectedAppsCount"
        )
//        Text(text = "Last Selected App: $lastSelectedApp")
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Text(text = "Include System Apps?")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(checked = selectAppsUiState.showSystemApps, onCheckedChange = {selectAppsViewModel.showSystemApps()})
        }
        Spacer(modifier = Modifier.height(32.dp))

        if (selectAppsUiState.isLoading) {
            Text(text = "Please Wait...")
        } else {
            LazyColumn(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .padding(8.dp)
                    .weight(1f)
            ) {
                items(items = selectAppsUiState.installedApps) { applicationInfo ->
                    ApplicationInfoCard(
                        icon = applicationInfo.icon,
                        label = applicationInfo.label,
                        isSelected = applicationInfo.isSelected,
                        isSystemApp = applicationInfo.isSystemApp,
                        onClick = {
                            selectAppsViewModel.selectApp(applicationInfo)
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ApplicationInfoCard(
    icon: ImageBitmap,
    label: String,
    isSelected: Boolean,
    isSystemApp: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    ) {
    val cardColor = when (isSelected) {
        false -> CardDefaults.cardColors()
        true -> CardDefaults.cardColors(
            containerColor = Color.hsl(
                hue = 220F,
                saturation = 1F,
                lightness = 0.6F
            ),
            contentColor = Color.White
        )

    }
    Card(
        onClick = onClick,
        modifier = modifier,
        colors = cardColor
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Image(
                bitmap = icon,
                contentDescription = "Icon of $label",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = when (isSelected) {
                    false -> FontWeight.Normal
                    true -> FontWeight.Bold
                }
            )
        }
    }
}



//@Preview(showBackground = true)
//@Composable()
//fun AppSelectionPreview() {
//    AppWipeTheme {
//        SelectAppsScreen(context = LocalContext.current)
//    }
//}