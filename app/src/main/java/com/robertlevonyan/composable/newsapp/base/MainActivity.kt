package com.robertlevonyan.composable.newsapp.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.android.material.color.DynamicColors
import com.robertlevonyan.composable.newsapp.ui.navigation.Navigation
import com.robertlevonyan.composable.newsapp.ui.theme.ComposableNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (DynamicColors.isDynamicColorAvailable()) {
            DynamicColors.applyIfAvailable(this)
        }
        setContent {
            ComposableNewsTheme {
                ProvideWindowInsets {
                    Surface {
                        Navigation()
                    }
                }
            }
        }
    }
}
