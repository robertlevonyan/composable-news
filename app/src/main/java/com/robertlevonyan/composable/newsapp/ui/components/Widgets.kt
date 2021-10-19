package com.robertlevonyan.composable.newsapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.robertlevonyan.composable.newsapp.R
import com.robertlevonyan.composable.newsapp.ui.theme.*


@Composable
fun SectionHeadingText(
    text: String,
    modifier: Modifier = Modifier,
) {
    val textColor = if (isSystemInDarkTheme()) {
        WhitePure
    } else {
        BlackPure
    }

    Text(
        text = text,
        modifier = modifier.padding(FabPadding),
        fontFamily = FontFamily(Font(resId = R.font.newsreader_extra_bold)),
        color = textColor,
        fontSize = SectionHeadingTextSize,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}

@Composable
fun ItemHeadingText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = WhitePure,
) {
    Text(
        text = text,
        fontFamily = FontFamily(Font(resId = R.font.newsreader_bold)),
        color = textColor,
        fontSize = ItemHeadingTextSize,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
        modifier = modifier.padding(all = HalfPadding),
        style = MaterialTheme.typography.h3.copy(
            shadow = Shadow(
                color = BlackVariant,
                offset = Offset(1f, 1f),
                blurRadius = 5f
            )
        )
    )
}

@Composable
fun GeneralText(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    textColor: Color = White,
) {
    Text(
        text = text,
        fontFamily = FontFamily(Font(resId = R.font.newsreader_light)),
        color = textColor,
        fontSize = GeneralTextSize,
        modifier = modifier.padding(all = HalfPadding),
        lineHeight = GeneralTextLineHeight,
        style = MaterialTheme.typography.h6.copy(
            shadow = Shadow(
                color = BlackVariant,
                offset = Offset(1f, 1f),
                blurRadius = 5f,
            )
        ),
        maxLines = maxLines,
    )
}

@Composable
fun ShowLoading(sectionHeight: Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(sectionHeight)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
            color = Accent,
        )
    }
}

@Composable
fun LoadErrorPlaceholder(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.bg_placeholder),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
        )
        Text(
            modifier = Modifier
                .padding(all = FabPadding)
                .align(Alignment.BottomCenter),
            text = stringResource(id = R.string.label_loading_error),
            color = colorResource(id = R.color.onPrimary),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun <I> PreviewWidgets(items: List<I>) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        GeneralText(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
//    }

    LoadErrorPlaceholder(modifier = Modifier.fillMaxSize())
}
