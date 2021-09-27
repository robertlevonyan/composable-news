package com.robertlevonyan.composable.newsapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.robertlevonyan.composable.newsapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.google_sans_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily(Font(R.font.google_sans_regular)),
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        ),
    caption = TextStyle(
        fontFamily = FontFamily(Font(R.font.google_sans_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
)