package com.ryanbarillosofficial.appwipe.ui.page.select_apps.review

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ryanbarillosofficial.appwipe.R
import com.ryanbarillosofficial.appwipe.data.local.model.application.ApplicationInfoWrapper
import com.ryanbarillosofficial.appwipe.ui.components.AppWipeTopAppBar
import com.ryanbarillosofficial.appwipe.ui.components.ConstrainedWidthLayout
import com.ryanbarillosofficial.appwipe.ui.components.NAVIGATION_ANIMATION_DURATION_MILLIS
import com.ryanbarillosofficial.appwipe.ui.components.paddingGap
import com.ryanbarillosofficial.appwipe.ui.page.select_apps.review.components.ApplicationInfoList
import com.ryanbarillosofficial.appwipe.ui.page.select_apps.review.components.ReviewAppsText
import com.ryanbarillosofficial.appwipe.ui.page.select_apps.review.components.ReviewComposableNew
import com.ryanbarillosofficial.appwipe.ui.page.select_apps.review.components.ReviewScreenType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    @StringRes routeTitle: Int,
    viewModel: ReviewViewModel = hiltViewModel(),
    navigateForward: () -> Unit = { },
    navigateUp: () -> Unit = { },
) {
    // UI state
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Top app bar states
    val screenTitle: Any = routeTitle
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    // navigation states
    val reviewNavController = rememberNavController()
    val reviewNavBackStackEntry by reviewNavController.currentBackStackEntryAsState()
    val reviewCurrentDestination = reviewNavBackStackEntry?.destination?.route

    val actualNavigateUp: () -> Unit = {
        if (reviewNavController.previousBackStackEntry != null) {
            reviewNavController.popBackStack()
            coroutineScope.launch {
                listState.scrollToItem(index = 0)
            }
        } else {
            navigateUp()
        }
    }
    BackHandler(enabled = true) {
        actualNavigateUp()
    }

    // Floating action button states
    val isSystemAppsListEmpty = uiState.listOfSystemApps.isEmpty()

    val (fabIcon, fabText) = if (isSystemAppsListEmpty) {
        Icons.Filled.Check to stringResource(R.string.done)
    } else {
        if (reviewCurrentDestination == ReviewScreenType.System.name) {
            Icons.Filled.Check to stringResource(R.string.done)
        } else {
            Pair(Icons.AutoMirrored.Filled.NavigateNext, stringResource(R.string.next))
        }
    }


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppWipeTopAppBar(
                collapsedTitle = screenTitle,
                scrollBehavior = scrollBehavior,
                navigateUp = actualNavigateUp
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    if (isSystemAppsListEmpty) {
                        navigateForward()
                    } else {
                        if (reviewCurrentDestination == ReviewScreenType.User.name) {
                            reviewNavController.navigate(ReviewScreenType.System.name)
                            coroutineScope.launch {
                                listState.scrollToItem(index = 0)
                            }
                        } else {
                            navigateForward()
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = fabIcon,
                        contentDescription = "$fabText Button"
                    )
                },
                text = { Text(fabText) },
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = reviewNavController,
            startDestination = ReviewScreenType.User.name,
        ) {
            composable(
                route = ReviewScreenType.User.name,
                exitTransition = {
                    slideOutVertically(
                    targetOffsetY = { -it },
                        animationSpec = tween(durationMillis = NAVIGATION_ANIMATION_DURATION_MILLIS)
                    ) },
                popEnterTransition = {
                    slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(durationMillis = NAVIGATION_ANIMATION_DURATION_MILLIS)
                    ) }
            ) {
                ReviewComposable(
                    title = R.string.review_user_apps_title,
                    description = R.string.review_user_apps_description,
                    applicationList = uiState.listOfUserApps.toList(),
                    innerPadding = innerPadding,
                    listState = listState
                )
            }
            composable(
                route = ReviewScreenType.System.name,
                enterTransition = { slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = NAVIGATION_ANIMATION_DURATION_MILLIS)
                )  },
                popExitTransition = { slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = NAVIGATION_ANIMATION_DURATION_MILLIS)
                )  }
            ) {
                ReviewComposable(
                    title = R.string.review_system_apps_title,
                    description = R.string.review_system_apps_description,
                    applicationList = uiState.listOfSystemApps.toList(),
                    innerPadding = innerPadding,
                    listState = listState
                )
            }
        }

    }
}

@Composable
fun ReviewComposable(
    @StringRes title: Int,
    @StringRes description: Int,
    applicationList: List<ApplicationInfoWrapper>,
    innerPadding: PaddingValues,
    listState: LazyListState = rememberLazyListState()
) {
    Log.d("ReviewComposableNew", "Icon: ${applicationList.first().icon}")
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .padding(paddingGap)
//            .verticalScroll(rememberScrollState())
    ) {
//        ReviewComposableNew(
//            title = title,
//            description = description,
//            applicationList = applicationList,
//            listState = listState
//        )
        ConstrainedWidthLayout {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(paddingGap)
            ) {
                ReviewAppsText(
                    title = title,
                    description = description
                )
                ApplicationInfoList(
                    applicationList = applicationList,
                    listState = listState
                )
            }
        }
    }
}