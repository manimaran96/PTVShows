package com.manimarank.ptvshows.presentation.tv_series_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manimarank.ptvshows.R
import com.manimarank.ptvshows.domain.model.TvSeries
import com.manimarank.ptvshows.presentation.components.AppLoader
import com.manimarank.ptvshows.presentation.components.EmptyWidget
import com.manimarank.ptvshows.presentation.components.ErrorWidget
import com.manimarank.ptvshows.presentation.components.RattingStats
import com.manimarank.ptvshows.presentation.components.TvSeriesImage
import com.manimarank.ptvshows.util.DateUtils
import com.manimarank.ptvshows.util.ImageAspectRatio
import com.manimarank.ptvshows.util.MovieContentRatting

/**
 * TV Series Details Screen
 */
@Composable
fun TvSeriesDetailsScreen() {

    val viewModel = hiltViewModel<TvSeriesDetailsViewModel>()
    val state = viewModel.state.collectAsState().value

    Scaffold { innerPadding ->
        Box (
            modifier = Modifier.padding(innerPadding)
        ) {
            when {
                state.isLoading -> AppLoader()
                state.error != null -> ErrorWidget(state.error)
                state.tvSeries == null -> EmptyWidget()
                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        TvSeriesImage(imagePath = state.tvSeries.backdrop_path, aspectRatio = ImageAspectRatio.cover, contentDescription = state.tvSeries.name)

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Box(modifier = Modifier.width(100.dp)) {
                                TvSeriesImage(imagePath = state.tvSeries.poster_path, clipShape = RoundedCornerShape(6.dp), contentDescription = state.tvSeries.name)
                            }

                            state.tvSeries.let { tvSeries ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 12.dp)
                                ) {
                                    Text(
                                        text = tvSeries.name ?: "",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    RattingStats(rating = tvSeries.vote_average ?: 0.0, rattersCount = tvSeries.vote_count ?: 0)

                                    Spacer(modifier = Modifier.height(8.dp))

                                    if (!tvSeries.tagline.isNullOrBlank()) {
                                        Text(
                                            text = tvSeries.tagline,
                                            fontSize = 14.sp,
                                            fontStyle = FontStyle.Italic
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    if (!tvSeries.first_air_date.isNullOrEmpty())
                                        Text(text = DateUtils.displayDate(tvSeries.first_air_date))

                                    Spacer(modifier = Modifier.height(8.dp))

                                    val movieContentRate = if(tvSeries.adult == false) MovieContentRatting.U else MovieContentRatting.UA
                                    val seasonInfo = "${tvSeries.number_of_seasons ?: 0} ${stringResource(id = if ((tvSeries.number_of_seasons ?: 0) > 1) R.string.seasons else R.string.season)}"

                                    Text(
                                        text =  "$movieContentRate | $seasonInfo",
                                    )
                                }
                            }
                        }

                        if (state.tvSeries.genres?.isNotEmpty() == true) {
                            Text(
                                text = state.tvSeries.genres.joinToString(" | "),
                                modifier = Modifier.padding(16.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light
                            )
                        }

                        TvSeriesMoreDetails(state.tvSeries)
                    }
                }
            }
        }
    }

}

/**
 * Component for TV Series More details
 */
@Composable
fun TvSeriesMoreDetails(tvSeries: TvSeries) {
    Column (
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {

        if (!tvSeries.overview.isNullOrEmpty())
            Text(
                text = tvSeries.overview,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )

        val detailsMap: Map<String, String> = mapOf(
            stringResource(R.string.type) to (tvSeries.type ?: ""),
            stringResource(R.string.original_name) to (tvSeries.original_name ?: ""),
            stringResource(R.string.status) to (tvSeries.status ?: ""),
            stringResource(R.string.languages) to (tvSeries.spoken_languages?.joinToString(", ") ?: ""),
            stringResource(R.string.total_episodes) to (tvSeries.number_of_episodes?.toString() ?: ""),
            stringResource(R.string.popularity) to (tvSeries.popularity?.toString() ?: ""),
            stringResource(R.string.production_companies) to (tvSeries.production_companies?.joinToString(", ") ?: ""),
            stringResource(R.string.networks) to (tvSeries.networks?.joinToString(", ") ?: ""),
        ).filter { it.value.isNotEmpty() }

        if (detailsMap.isNotEmpty()) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp))
            {
                detailsMap.forEach {
                    Divider(thickness = 0.2.dp, modifier = Modifier.padding(vertical = 12.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Text(text = it.key, modifier = Modifier.weight(0.4f))
                        Text(text = it.value, modifier = Modifier.weight(0.6f), fontWeight = FontWeight.SemiBold)
                    }
                }
                Divider(thickness = 0.2.dp, modifier = Modifier.padding(vertical = 12.dp))
            }
        }
    }
}