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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ryanbarillosofficial.appwipe.R
import com.ryanbarillosofficial.appwipe.ui.paddingGap
import kotlinx.coroutines.launch

/**
 * Reference(s) needed to complete this page:
 * https://developer.android.com/reference/android/content/pm/PackageManager
 * https://tomas-repcik.medium.com/listing-all-installed-apps-in-android-13-via-packagemanager-3b04771dc73
 */

@Composable
fun SelectAppsScreen(
    modifier: Modifier = Modifier,
    context: Context,
    onTitlesChange: (Any, Any) -> Unit = { _, _ -> },
    viewModel: SelectAppsViewModel = viewModel {
        SelectAppsViewModel(
            packageManager = context.packageManager,
            packageName = context.packageName
        )
    }
) {
    // View Model
    val uiState by viewModel.uiState.collectAsState()
    val selectedAppsCount = uiState.selectedApps.size
    val screenTitle: Any = if (selectedAppsCount > 0) {
        "$selectedAppsCount selected"
    } else {
        R.string.select_apps_title
    }

    LaunchedEffect(screenTitle) {
        onTitlesChange(screenTitle, screenTitle)
    }

//    val lastSelectedApp = when (selectedAppsCount) {
//        0 -> "None"
//        else ->  selectAppsUiState.selectedApps.last().label
//    }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
//        Spacer(modifier = Modifier.height(paddingGap))
//        Text(text = "Last Selected App: $lastSelectedApp")
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Show System Apps")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = uiState.showSystemApps,
                onCheckedChange = {
                    viewModel.showSystemApps()
                    coroutineScope.launch {
                        listState.scrollToItem(index = 0)
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        if (uiState.isLoading) {
            Text(text = "Please Wait...")
        } else {
            // Scroll to top when showSystemApps changes

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .weight(1f)
            ) {
                itemsIndexed(items = uiState.installedApps) { index, applicationInfo ->
                    val topPadding = if (index > 0) paddingGap else 0.dp
                    ApplicationInfoCard(
                        icon = applicationInfo.icon,
                        label = applicationInfo.label,
                        isSelected = applicationInfo.isSelected,
                        isSystemApp = applicationInfo.isSystemApp,
                        onClick = {
                            viewModel.selectApp(applicationInfo)
                        },
                        modifier = Modifier.padding(top = topPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SelectAppsMoreOptionsRow(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
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
    val cardColor: CardColors = when (isSelected) {
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
    val textStyle = when(isSelected) {
        false -> MaterialTheme.typography.bodyLarge
        true -> MaterialTheme.typography.bodyLarge.copy(
            shadow = Shadow(
                color = Color.Black.copy(alpha = 0.5f), // Shadow color with some transparency
//                offset = Offset(x = 2f, y = 2f),    // Offset of the shadow
                blurRadius = 4f                     // Blur radius for a softer shadow
            )
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
                style = textStyle,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
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