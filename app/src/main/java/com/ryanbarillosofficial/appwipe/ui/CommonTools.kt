package com.ryanbarillosofficial.appwipe.ui

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier

/**
 * These are common classes, objects & styling to be quickly used across all pages
 * of the application, with the objective of minimizing reduncancy & increasing
 * efficiency of development
 */

val modifierWithPaddingOnStatusAndNavigationBars: Modifier = Modifier.navigationBarsPadding()
    .statusBarsPadding()
//    .safeGesturesPadding()


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