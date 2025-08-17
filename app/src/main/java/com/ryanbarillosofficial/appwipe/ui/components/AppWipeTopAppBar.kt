package com.ryanbarillosofficial.appwipe.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ryanbarillosofficial.appwipe.R
import com.ryanbarillosofficial.appwipe.ui.paddingGap
import com.ryanbarillosofficial.appwipe.ui.theme.AppWipeTheme



/**
 * A custom TopAppBar for the AppWipe application that can display different titles
 * when expanded or collapsed.
 * @param collapsedTitle The string for the title when the app bar is collapsed.
 *                           Accepted data types: String or @StringRes Int
 * @param expandedTitle The string for the title when the app bar is expanded.
 *                           Accepted data types: String or @StringRes Int
 *                           If null, expandedTitleResId will be used.
 * @param canNavigateBack Whether to display the back navigation icon.
 * @param modifier Optional [Modifier] for theming and styling.
 * @param scrollBehavior The [TopAppBarScrollBehavior] to coordinate scrolling. Essential for this functionality.
 * @param navigateUp Lambda function to be invoked when the navigation icon is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppWipeTopAppBar(
    collapsedTitle: Any,
    expandedTitle: Any? = null,
    canNavigateBack: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    navigateUp: () -> Unit = { },
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,


    ) {
    // Determine which title to display based on the collapsed fraction
    // The threshold (e.g., 0.5f) can be adjusted based on when you want the title to switch.
    // For LargeTopAppBar, the title often disappears/changes as it collapses, so switching
    // mid-way or based on when the large title area is mostly gone makes sense.
    val showCollapsedTitle by remember(scrollBehavior.state.collapsedFraction) {
        derivedStateOf { scrollBehavior.state.collapsedFraction > 0.5f }
    }
    // Log.e("showCollapsedTitle", "Status: $showCollapsedTitle")

    // Initialization of the collapsed title
    val actualCollapsedTitle = when (collapsedTitle) {
        is String -> collapsedTitle
        is Int -> stringResource(id = collapsedTitle)
        else -> throw IllegalArgumentException("collapsedTitle must either be a String or @StringRes Int")
    }
    // Under normal circumstances, both the collapsed and expanded titles are the same
    // But in certain cases where this is not so, it will have a different value
    val actualExpandedTitle = when (expandedTitle) {
        is String -> expandedTitle
        is Int -> stringResource(id = expandedTitle)
        null -> actualCollapsedTitle
        else -> throw IllegalArgumentException("expandedTitle must be a String or @StringRes Int")
    }
    // Finally, the actual title to display
    val actualTitle = if (showCollapsedTitle) actualCollapsedTitle else actualExpandedTitle
    val style = if (showCollapsedTitle) MaterialTheme.typography.titleLarge else MaterialTheme.typography.displaySmall
    LargeTopAppBar(
        title = { Text(
            text = actualTitle,
            modifier = Modifier.padding(paddingGap),
            style = style,
        )},
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AppWipeTopAppBarPreview() {
    AppWipeTheme {
        AppWipeTopAppBar(
            expandedTitle = "Expanded Title",
            collapsedTitle = "Collapsed Title",
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            canNavigateBack = false

        )
    }
}