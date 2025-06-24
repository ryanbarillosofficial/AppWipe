package com.ryanbarillosofficial.appwipe.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ryanbarillosofficial.appwipe.R
import com.ryanbarillosofficial.appwipe.ui.theme.AppWipeTheme

@Composable
fun NavigationCardButton(
    modifier: Modifier = Modifier,
    titleText: String = stringResource(R.string.default_title_text),
    descriptionText: String = stringResource(R.string.default_description_text),
    onClick: () -> Unit = { },
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Text(
                    text = titleText,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
            }
            Text(text = descriptionText)
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