package com.ryanbarillosofficial.appwipe.ui.page.select_apps.review.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.ryanbarillosofficial.appwipe.data.local.model.application.ApplicationInfoWrapper

@Composable
fun ApplicationInfoList(
    applicationList: List<ApplicationInfoWrapper>,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
        ) {
        itemsIndexed(items = applicationList) { index, applicationInfo ->
            if (index > 0) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider(
                        thickness = 2.dp,
                        modifier = Modifier.fillMaxWidth(0.9f)

                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = applicationInfo.icon),
                    contentDescription = "Icon of ${applicationInfo.label}",
                    modifier = Modifier.size(48.dp),
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = applicationInfo.label,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}