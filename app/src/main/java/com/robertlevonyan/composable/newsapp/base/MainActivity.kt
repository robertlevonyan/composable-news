package com.robertlevonyan.composable.newsapp.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.robertlevonyan.composable.newsapp.ui.navigation.Navigation
import com.robertlevonyan.composable.newsapp.ui.theme.ComposableNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ComposableNewsTheme {
                Surface {
                    Navigation()
                }
            }
        }
    }
}
