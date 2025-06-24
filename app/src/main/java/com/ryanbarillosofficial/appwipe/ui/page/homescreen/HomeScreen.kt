package com.ryanbarillosofficial.appwipe.ui.page.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import com.ryanbarillosofficial.appwipe.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ryanbarillosofficial.appwipe.ui.component.NavigationCardButton
import com.ryanbarillosofficial.appwipe.ui.theme.AppWipeTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HomeText(0)
        NavigationCardButton(modifier = Modifier.padding(16.dp))
    }

}

@Composable
fun HomeText(countOfAppsBlocked: Int) {
    if(countOfAppsBlocked > 0) {
        Text(
            text = "Hooray!\nThere are $countOfAppsBlocked apps blocked!",
            fontSize = 30.sp,
            lineHeight = 50.sp,
            textAlign = TextAlign.Center
        )
    } else {
        Text(
            text = stringResource(R.string.no_apps_blocked),
            fontSize = 30.sp,
            lineHeight = 50.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    )
@Composable()
fun HomeScreenPreview() {
    AppWipeTheme {
        HomeScreen()
    }
}