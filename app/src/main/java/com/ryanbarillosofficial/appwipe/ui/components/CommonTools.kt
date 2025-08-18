package com.ryanbarillosofficial.appwipe.ui.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * These are common classes, objects & styling to be quickly used across all pages
 * of the application, with the objective of minimizing reduncancy & increasing
 * efficiency of development
 */

val modifierWithPaddingOnSidesAndBottom: Modifier = Modifier
    .navigationBarsPadding()
//    .statusBarsPadding()
    .safeGesturesPadding()

val paddingGap: Dp = 8.dp
const val NAVIGATION_ANIMATION_DURATION_MILLIS: Int = 700

// OneUI padding for all screens
//val paddingForAllSides: Dp = 24.dp



/**
 * To be implemented at a future date.
 * Intent is to be a scalable function that can preview any page at ease without the need
 * to tediously and manually implement it at each page
 */
//
//@Preview(
//    showBackground = true,
//    showSystemUi = true,
//)
//@Composable
//fun PreviewWithSystemUi(composablePage: @Composable () -> Unit) {
//    AppWipeTheme {
//        composablePage()
//    }
//}