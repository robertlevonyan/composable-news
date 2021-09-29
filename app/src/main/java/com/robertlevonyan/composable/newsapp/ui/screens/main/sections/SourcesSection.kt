package com.robertlevonyan.composable.newsapp.ui.screens.main.sections

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.flowlayout.FlowRow
import com.robertlevonyan.chip.compose.MaterialChip
import com.robertlevonyan.composable.newsapp.R
import com.robertlevonyan.composable.newsapp.data.entity.SourceItem
import com.robertlevonyan.composable.newsapp.ui.components.SectionHeadingText
import com.robertlevonyan.composable.newsapp.ui.screens.main.MainViewModel
import com.robertlevonyan.composable.newsapp.ui.theme.*

object Sources {
    @Composable
    fun SourcesSection(mainViewModel: MainViewModel) {
        val sources by mainViewModel.sources.collectAsState()
        val areSourcesLoading by mainViewModel.areSourcesLoading.collectAsState()

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = colorResource(id = R.color.surface))
        ) {
            ConstraintLayout {
                val (title, more, tags) = createRefs()

                SectionHeadingText(
                    text = stringResource(id = R.string.label_sources),
                    modifier = Modifier.constrainAs(title) {
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                )
                TagsToggle(mainViewModel, areSourcesLoading, title, more)
                TagsFlexbox(sources, title, tags)
            }
        }
    }

    @Composable
    private fun ConstraintLayoutScope.TagsToggle(
        mainViewModel: MainViewModel,
        areSourcesLoading: Boolean,
        title: ConstrainedLayoutReference,
        more: ConstrainedLayoutReference
    ) {
        val areAllSources by mainViewModel.areAllSources.collectAsState()

        Row(
            modifier = Modifier
                .padding(end = FabPadding)
                .constrainAs(more) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    end.linkTo(parent.end)
                    top.linkTo(title.top)
                    bottom.linkTo(title.bottom)
                }
        ) {
            if (areSourcesLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(end = HalfPadding)
                        .size(IconSize)
                        .align(Alignment.CenterVertically),
                    color = Accent,
                )
            }

            Button(
                shape = RoundedCornerShape(size = CornerRadius),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.surface),
                    contentColor = colorResource(id = R.color.onPrimary),
                ),
                onClick = {
                    if (areAllSources) mainViewModel.getInitialSources() else mainViewModel.getSources()
                },
                elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
            ) {
                Text(
                    text = stringResource(id = if (areAllSources) R.string.label_less else R.string.label_more),
                    fontFamily = FontFamily(Font(resId = R.font.newsreader_bold)),
                )
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun ConstraintLayoutScope.TagsFlexbox(
        sources: List<SourceItem>,
        title: ConstrainedLayoutReference,
        tags: ConstrainedLayoutReference
    ) {
        AnimatedContent(
            targetState = sources,
            modifier = Modifier
                .padding(all = SmallPadding)
                .fillMaxSize()
                .constrainAs(tags) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                    top.linkTo(title.bottom)
                },
        ) { updatedSource ->
            FlowRow(modifier = Modifier
                .fillMaxSize()
                .padding(bottom = HalfPadding)) {
                updatedSource.forEach { source ->
                    MaterialChip(
                        text = source.name ?: "",
                        modifier = Modifier.padding(all = SmallPadding),
                        strokeSize = StrokeSize,
                        strokeColor = Accent,
                        backgroundColor = Color.Transparent,
                        textColor = colorResource(id = R.color.onPrimary),
                        fontFamily = FontFamily(Font(resId = R.font.newsreader_regular)),
                        onChipClick = {

                        }
                    )
                }
            }
        }
    }
}
