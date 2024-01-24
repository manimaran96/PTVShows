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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manimarank.ptvshows.R
import com.manimarank.ptvshows.domain.model.TvSeries
import com.manimarank.ptvshows.presentation.components.AppLoader
import com.manimarank.ptvshows.presentation.components.CastItem
import com.manimarank.ptvshows.presentation.components.EmptyWidget
import com.manimarank.ptvshows.presentation.components.ErrorWidget
import com.manimarank.ptvshows.presentation.components.RattingStats
import com.manimarank.ptvshows.presentation.components.SeasonItem
import com.manimarank.ptvshows.presentation.components.TvSeriesImage
import com.manimarank.ptvshows.util.DateUtils
import com.manimarank.ptvshows.util.ImageAspectRatio
import com.manimarank.ptvshows.util.MovieContentRatting
import com.manimarank.ptvshows.util.isLandscape

/**
 * TV Series Details Screen
 */
@Composable
fun TvSeriesDetailsScreen() {

    val viewModel = hiltViewModel<TvSeriesDetailsViewModel>()
    val state = viewModel.state.collectAsState().value

    val tabs = listOf(stringResource(R.string.details), stringResource(R.string.seasons), stringResource(R.string.cast))
    var tabIndex by remember { mutableIntStateOf(0) }

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
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        if (!LocalContext.current.isLandscape())
                            TvSeriesImage(imagePath = state.tvSeries.backdrop_path, aspectRatio = ImageAspectRatio.cover, contentDescription = state.tvSeries.name)

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Box(modifier = Modifier.width(100.dp)) {
                                TvSeriesImage(imagePath = state.tvSeries.poster_path, clipShape = RoundedCornerShape(6.dp), contentDescription = state.tvSeries.name, defaultIconSize = 35)
                            }

                            state.tvSeries.let { tvSeries ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(if (LocalContext.current.isLandscape()) 0.6f else 1f)
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

                                    Row {
                                        val movieContentRate = if(tvSeries.adult == false) MovieContentRatting.U else MovieContentRatting.UA
                                        Text(
                                            text =  movieContentRate.name,
                                        )

                                        val seasonInfo: String = if ((tvSeries.number_of_seasons ?: 0) > 0)
                                            "${tvSeries.number_of_seasons ?: 0} ${stringResource(id = if ((tvSeries.number_of_seasons ?: 0) > 1) R.string.seasons else R.string.season)}"
                                        else ""
                                        if (seasonInfo.isNotEmpty())
                                            Text(text = " | $seasonInfo")
                                    }


                                }
                            }

                            if (LocalContext.current.isLandscape())
                                Box (modifier = Modifier.padding(horizontal = 16.dp)) {
                                    TvSeriesImage(
                                        imagePath = state.tvSeries.backdrop_path,
                                        aspectRatio = ImageAspectRatio.cover,
                                        contentDescription = state.tvSeries.name,
                                        clipShape = RoundedCornerShape(4.dp)
                                    )
                                }

                        }

                        val genres = if (state.tvSeries.genres?.filterNotNull()?.isNotEmpty() == true) state.tvSeries.genres.joinToString(" | ") else null
                        if (!genres.isNullOrBlank()) {
                            Text(
                                text = genres,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(16.dp),
                                fontWeight = FontWeight.Light
                            )
                        }

                        TabRow(
                            selectedTabIndex = tabIndex,
                        ) {
                            tabs.forEachIndexed { index, title ->
                                Tab(text = {
                                    Text(
                                        text = tabs[index]
                                    )
                                },
                                    selected = tabIndex == index,
                                    onClick = { tabIndex = index }
                                )
                            }
                        }

                        when (tabs[tabIndex]) {
                            stringResource(id = R.string.seasons) -> TvSeriesSeasons(state.tvSeries)
                            stringResource(id = R.string.cast) -> TvSeriesCast(state.tvSeries)
                            else -> TvSeriesMoreDetails(state.tvSeries)
                        }
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
        modifier = Modifier.padding(16.dp),
    ) {

        if (!tvSeries.overview.isNullOrEmpty())
            Text(
                text = tvSeries.overview,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight.Light
            )

        val detailsMap = mutableMapOf<String, String>().apply {
            put(stringResource(R.string.type), tvSeries.type ?: "")
            put(stringResource(R.string.original_name), tvSeries.original_name ?: "")
            put(stringResource(R.string.status), tvSeries.status ?: "")
            put(stringResource(R.string.languages), tvSeries.spoken_languages?.joinToString(", ") ?: "")
            if ((tvSeries.number_of_episodes ?: 0) > 0)
                put(stringResource(R.string.total_episodes), tvSeries.number_of_episodes?.toString() ?: "")
            put(stringResource(R.string.popularity), tvSeries.popularity?.toString() ?: "")
            put(stringResource(R.string.production_companies), tvSeries.production_companies?.joinToString(", ") ?: "")
            put(stringResource(R.string.networks), tvSeries.networks?.joinToString(", ") ?: "")
        }.filter { it.value.isNotEmpty() }

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

/**
 * Component for TV Series Seasons full details
 */
@Composable
fun TvSeriesSeasons(tvSeries: TvSeries) {
    val seasons = tvSeries.seasons?.filterNotNull() ?: emptyList()
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        if (seasons.isEmpty()) {
            EmptyWidget()
        } else {
            seasons.forEach {season ->
                SeasonItem(
                    season = season,
                )
                Divider(thickness = 0.2.dp, modifier = Modifier.padding(vertical = 12.dp))
            }
        }
    }
}


/**
 * Component for TV Series Cast
 */
@Composable
fun TvSeriesCast(tvSeries: TvSeries) {
    val castList = tvSeries.cast_list?.filterNotNull()?.sortedBy { it.order } ?: emptyList()
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        if (castList.isEmpty()) {
            EmptyWidget()
        } else {
            castList.forEach {cast ->
                CastItem(
                    cast = cast,
                )
                Divider(thickness = 0.2.dp, modifier = Modifier.padding(vertical = 12.dp))
            }
        }
    }
}
