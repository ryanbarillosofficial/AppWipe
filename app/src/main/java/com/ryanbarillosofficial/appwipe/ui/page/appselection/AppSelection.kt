package com.ryanbarillosofficial.appwipe.ui.page.appselection

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.ryanbarillosofficial.appwipe.ui.theme.AppWipeTheme

/**
 * Reference(s) needed to complete this page:
 * https://developer.android.com/reference/android/content/pm/PackageManager
 * https://tomas-repcik.medium.com/listing-all-installed-apps-in-android-13-via-packagemanager-3b04771dc73
 */


@Composable
fun AppSelection(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val packageManager = context.packageManager
    val installedAppsList: List<ApplicationInfo> = (remember {
        packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
    }).sortedBy { application -> packageManager.getApplicationLabel(application).toString().lowercase() }

    LazyColumn(modifier = modifier) {
        items(items = installedAppsList) { applicationInfo ->
            AppInfoCard(
                applicationIcon = packageManager.getApplicationIcon(applicationInfo),
                applicationLabel = packageManager.getApplicationLabel(applicationInfo)
                    .toString(),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun AppInfoCard(
    applicationIcon: Drawable,
    applicationLabel: String,
    modifier: Modifier = Modifier
    ) {
    Card(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Image(
                bitmap = applicationIcon.toBitmap().asImageBitmap(),
                contentDescription = "Icon of $applicationLabel",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = applicationLabel,
                style = MaterialTheme.typography.bodyLarge
            )
            // Add more details as needed

        }
    }
}



@Preview(showBackground = true)
@Composable()
fun AppSelectionPreview() {
    AppWipeTheme {
        AppSelection()
    }
}