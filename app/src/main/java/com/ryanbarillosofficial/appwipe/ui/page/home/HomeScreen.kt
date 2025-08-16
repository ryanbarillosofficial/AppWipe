package com.ryanbarillosofficial.appwipe.ui.page.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.ryanbarillosofficial.appwipe.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ryanbarillosofficial.appwipe.ui.component.NavigationCardButton
import com.ryanbarillosofficial.appwipe.ui.navigation.ScreenRoute
import com.ryanbarillosofficial.appwipe.ui.theme.AppWipeTheme

const val placeholderInt: Int = 0

@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel()
) {
    val homeUiState by homeViewModel.uiState.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier.verticalScroll(rememberScrollState())
        modifier = modifier
    ) {
//        Spacer(modifier = Modifier.height(32.dp))
        HomeText(placeholderInt)
        // Pick Apps Button
        NavigationCardButton(
            titleText = stringResource(R.string.select_apps_title),
            descriptionText = stringResource(R.string.select_apps_description),
            onClick = { navController.navigate(ScreenRoute.SelectApps.route) },
            modifier = Modifier.padding(16.dp)
        )
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
            text = stringResource(R.string.no_apps_blocked_message),
            fontSize = 30.sp,
            lineHeight = 50.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(
    showBackground = true,
    )
@Composable()
fun HomeScreenPreview() {
    AppWipeTheme {
        HomeScreen(navController = rememberNavController())
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=720dp,height=360dp,dpi=240"
)
@Composable()
fun HomeScreenLandscapePreview() {
    AppWipeTheme {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            navController = rememberNavController()
        )
    }
}

@Preview(showBackground = true)
@Composable()
fun HomeTextPreview() {
    AppWipeTheme {
        HomeText(5)
    }
}