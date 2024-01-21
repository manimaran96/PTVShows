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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.manimarank.ptvshows.R
import com.manimarank.ptvshows.presentation.components.AppLoader
import com.manimarank.ptvshows.presentation.components.EmptyWidget
import com.manimarank.ptvshows.presentation.components.ErrorWidget
import com.manimarank.ptvshows.presentation.components.RattingStats
import com.manimarank.ptvshows.presentation.components.TvSeriesImage
import com.manimarank.ptvshows.util.DateUtils
import com.manimarank.ptvshows.util.ImageAspectRatio

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
                        modifier = Modifier
                            .fillMaxSize()
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
                                    modifier = Modifier.fillMaxWidth().padding(start = 12.dp)
                                ) {
                                    Text(
                                        text = tvSeries.name ?: "",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    RattingStats(rating = tvSeries.vote_average ?: 0.0, rattersCount = tvSeries.vote_count ?: 0)

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = stringResource(R.string.release_date) + DateUtils.displayDate(tvSeries.first_air_date)
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = stringResource(R.string.language) + tvSeries.original_language
                                    )
                                }
                            }
                        }

                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = state.tvSeries.overview ?: "",
                            fontSize = 16.sp,
                        )

                    }
                }
            }
        }
    }

}