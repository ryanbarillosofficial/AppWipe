package com.ryanbarillosofficial.appwipe.ui.page.home

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.MaterialTheme
import com.ryanbarillosofficial.appwipe.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ryanbarillosofficial.appwipe.ui.component.NavigationCardButton
import com.ryanbarillosofficial.appwipe.ui.navigation.ScreenRoute
import com.ryanbarillosofficial.appwipe.ui.paddingGap
import com.ryanbarillosofficial.appwipe.ui.theme.AppWipeTheme

const val placeholderInt: Int = 0

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(),
    navController: NavHostController,
    onTitlesChange: (Any, Any) -> Unit = { _, _ -> }
) {
    val homeUiState by homeViewModel.uiState.collectAsState()

    val screenTitle = R.string.home_title // Title of this app for the top bar
    LaunchedEffect( screenTitle) {
        onTitlesChange(screenTitle, screenTitle)
    }

    BoxWithConstraints(modifier = modifier) {
        val scope = this
        val screenHeight = scope.maxHeight
        var contentHeight by remember { mutableStateOf(0.dp) }
        val localDensity = LocalDensity.current

        val wouldOverflow = contentHeight > screenHeight // Determine if the content would overflow, if not scrollable

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(
                    state = rememberScrollState(),
                    enabled = wouldOverflow
                )
                .onSizeChanged {
                    contentHeight = with(localDensity) { it.height.toDp() }
                }
        ) {
            HomeText(placeholderInt)
            // Pick Apps Button
            Spacer(modifier = Modifier.padding(vertical = paddingGap))
            NavigationCardButton(
                titleText = stringResource(R.string.select_apps_title),
                descriptionText = stringResource(R.string.select_apps_description),
                iconImageVector = Icons.Default.Add,
                onClick = { navController.navigate(ScreenRoute.SelectApps.route) },
                modifier = Modifier.fillMaxWidth(),
            )
            // View List Button
            Spacer(modifier = Modifier.padding(vertical = paddingGap))
            NavigationCardButton(
                titleText = stringResource(R.string.view_list_title),
                descriptionText = stringResource(R.string.view_list_description),
                iconImageVector = Icons.AutoMirrored.Filled.ViewList,
//                onClick = { navController.navigate(ScreenRoute.ViewList.route) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.padding(vertical = paddingGap))
            NavigationCardButton(modifier = Modifier.fillMaxWidth())
        }
    }
}

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