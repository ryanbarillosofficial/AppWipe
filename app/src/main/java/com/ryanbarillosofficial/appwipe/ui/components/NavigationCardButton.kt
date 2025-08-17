package com.ryanbarillosofficial.appwipe.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ryanbarillosofficial.appwipe.R
import com.ryanbarillosofficial.appwipe.ui.theme.AppWipeTheme

@Composable
fun NavigationCardButton(
    modifier: Modifier = Modifier,
    iconImageVector: ImageVector = Icons.Default.Add,
    titleText: String = stringResource(R.string.default_title_text),
    descriptionText: String = stringResource(R.string.default_description_text),
    onClick: () -> Unit = {},
) {
    // Card can be clickable
    // See here: https://developer.android.com/develop/ui/compose/components/card#advanced
    Card(
        modifier = modifier,
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = iconImageVector,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = titleText,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = descriptionText,
                    style = MaterialTheme.typography.bodyLarge,
//                    minLines = 2,
//                    overflow = TextOverflow.Ellipsis
//                    modifier = Modifier.height(40.dp) // Implied space for two lines
                )
            }
        }
    }
}

@Preview
@Composable
fun NavigationCardButtonPreview() {
    AppWipeTheme {
        NavigationCardButton()
    }
}