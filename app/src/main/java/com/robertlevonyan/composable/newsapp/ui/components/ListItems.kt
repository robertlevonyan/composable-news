package com.robertlevonyan.composable.newsapp.ui.components

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.robertlevonyan.composable.newsapp.R
import com.robertlevonyan.composable.newsapp.data.entity.NewsItem
import com.robertlevonyan.composable.newsapp.data.entity.NewsSource
import com.robertlevonyan.composable.newsapp.ui.theme.*

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BreakingNewsItem(newsItem: NewsItem) {
    ConstraintLayout {
        val (background, title, description) = createRefs()

        val painter = if (newsItem.image == null) {
            painterResource(id = R.drawable.bg_placeholder)
        } else {
            rememberImagePainter(data = newsItem.image) {
                crossfade(true)
                placeholder(R.drawable.bg_placeholder)
            }
        }

        Image(
            painter = painter,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(background) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(BlackTransparent, BlendMode.Overlay)
        )
        ItemHeadingText(
            text = newsItem.title ?: "",
            modifier = Modifier
                .constrainAs(title) {
                    bottom.linkTo(description.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
        )
        GeneralText(
            text = newsItem.description ?: "",
            modifier = Modifier
                .constrainAs(description) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            maxLines = 3,
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PopularNewsItem(newsItem: NewsItem) {
    val elementWidth = Resources.getSystem().displayMetrics.widthPixels * 2 / 3
    val elementWidthDp = LocalDensity.current.run { elementWidth.toDp() }

    val painter = if (newsItem.image == null) {
        painterResource(id = R.drawable.bg_placeholder)
    } else {
        rememberImagePainter(data = newsItem.image) {
            crossfade(true)
            placeholder(R.drawable.bg_placeholder)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .padding(all = 4.dp)
            .width(width = elementWidthDp)
            .height(height = 250.dp),
    ) {
        val (image, title, description) = createRefs()

        Image(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(image) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    bottom.linkTo(title.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            painter = painter,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        ItemHeadingText(
            text = newsItem.title ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    bottom.linkTo(description.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(image.bottom)
                },
            textColor = if (isSystemInDarkTheme()) WhitePure else BlackPure,
        )
        GeneralText(
            text = newsItem.description ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(description) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(title.bottom)
                },
            maxLines = 3,
            textColor = if (isSystemInDarkTheme()) White else Black,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListItems() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        PopularNewsItem(
            newsItem = NewsItem(
                "author",
                "title",
                "description",
                "url",
                "https://images.unsplash.com/photo-1632699890381-4bfca8b6488a?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=2340&q=80",
                "content",
                NewsSource("id", "name"),
                "published_at",
            )
        )
    }
}