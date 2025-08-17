package com.ryanbarillosofficial.appwipe.ui.page.select_apps.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ApplicationInfoCard(
    icon: ImageBitmap,
    label: String,
    isSelected: Boolean,
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