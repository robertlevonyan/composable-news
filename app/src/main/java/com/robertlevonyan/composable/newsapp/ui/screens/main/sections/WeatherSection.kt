package com.robertlevonyan.composable.newsapp.ui.screens.main.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.insets.navigationBarsPadding
import com.robertlevonyan.composable.newsapp.R
import com.robertlevonyan.composable.newsapp.data.entity.weather.Weather
import com.robertlevonyan.composable.newsapp.ui.components.SectionHeadingText
import com.robertlevonyan.composable.newsapp.ui.screens.main.MainViewModel
import com.robertlevonyan.composable.newsapp.ui.theme.*
import com.robertlevonyan.composable.newsapp.util.currentDate
import com.robertlevonyan.composable.newsapp.util.round

object Weather {
    @Composable
    fun WeatherSection(mainViewModel: MainViewModel) {
        val weather by mainViewModel.weather.collectAsState()

        if (weather == Weather.EMPTY) return

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .navigationBarsPadding()
        ) {

            val (header, city, date, description, temperature, feels, min, max) = createRefs()
            SectionHeadingText(
                modifier = Modifier
                    .constrainAs(header) {
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .padding(bottom = HalfPadding),
                text = stringResource(id = R.string.label_weather),
            )

            Text(
                text = weather.name,
                modifier = Modifier
                    .constrainAs(city) {
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                        start.linkTo(parent.start)
                        top.linkTo(header.bottom)
                    }
                    .padding(
                        horizontal = FabPadding,
                        vertical = HalfPadding,
                    ),
                fontSize = Text16,
                color = colorResource(id = R.color.onPrimary),
            )

            SecondaryText(
                text = currentDate(),
                modifier = Modifier
                    .constrainAs(date) {
                        width = Dimension.wrapContent
                        height = Dimension.wrapContent
                        start.linkTo(parent.start)
                        top.linkTo(city.bottom)
                    },
            )

            weather.weatherInfo.firstOrNull()?.let { weatherInfo ->
                SecondaryText(
                    text = stringResource(id = weatherInfo.description),
                    modifier = Modifier
                        .constrainAs(description) {
                            width = Dimension.wrapContent
                            height = Dimension.wrapContent
                            start.linkTo(parent.start)
                            top.linkTo(date.bottom)
                            bottom.linkTo(parent.bottom)
                        },
                )

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(temperature) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .padding(all = HalfPadding),
                ) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.Asset(weatherInfo.icon))
                    LottieAnimation(
                        composition = composition,
                        iterations = Int.MAX_VALUE,
                        modifier = Modifier
                            .size(WeatherIconSize)
                            .align(Alignment.CenterVertically),
                    )

                    Text(
                        text = "${weather.temperature.round()} °C",
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterVertically)
                            .padding(all = HalfPadding),
                        fontWeight = FontWeight.Black,
                        color = colorResource(id = R.color.onPrimary),
                        fontSize = Text20,
                    )
                }

                SecondaryText(
                    text = "Feels like ${weather.feelsLike.round()} °C",
                    modifier = Modifier
                        .constrainAs(feels) {
                            width = Dimension.wrapContent
                            height = Dimension.wrapContent
                            top.linkTo(temperature.bottom)
                            end.linkTo(parent.end)
                            bottom.linkTo(min.top)
                        },
                )

                SecondaryText(
                    text = "Minimum ${weather.tempMin.round()} °C",
                    modifier = Modifier
                        .constrainAs(min) {
                            width = Dimension.wrapContent
                            height = Dimension.wrapContent
                            top.linkTo(feels.bottom)
                            end.linkTo(parent.end)
                            bottom.linkTo(max.top)
                        },
                )

                SecondaryText(
                    text = "Maximum ${weather.tempMax.round()} °C",
                    modifier = Modifier
                        .constrainAs(max) {
                            width = Dimension.wrapContent
                            height = Dimension.wrapContent
                            top.linkTo(min.bottom)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                )
            }
        }
    }

    @Composable
    private fun SecondaryText(
        text: String,
        modifier: Modifier,
    ) {
        Text(
            text = text,
            modifier = modifier.padding(horizontal = FabPadding),
            fontSize = Text14,
            color = colorResource(id = R.color.onPrimaryVariant),
        )
    }
}
