package com.manimarank.ptvshows.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.manimarank.ptvshows.R
import com.manimarank.ptvshows.data.remote.TvSeriesApi
import com.manimarank.ptvshows.domain.model.TvSeries
import com.manimarank.ptvshows.util.AppColors

/**
 * Component for TV Series item
 */
@Composable
fun TvSeriesItem(
    tvSeries: TvSeries,
    navHostController: NavHostController
) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(TvSeriesApi.imageBaseUrl + tvSeries.poster_path)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(4.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                // Details page open
            }
    ) {


        // Poster image
        when (imageState) {
            is AsyncImagePainter.State.Success -> {
                Image(
                    modifier = Modifier
                        .aspectRatio(2f/3f)
                        .clip(RoundedCornerShape(4.dp)),
                    painter = imageState.painter,
                    contentDescription = tvSeries.name,
                    contentScale = ContentScale.Crop
                )
            }
            else -> {
                Box(
                    modifier = Modifier
                        .aspectRatio(2f/3f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(AppColors.defaultImageBg),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painterResource(R.drawable.default_image),
                        modifier = Modifier.size(70.dp),
                        contentDescription = tvSeries.name
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        RattingStats(
            rating = tvSeries.vote_average ?: 0.0
        )

        // Title
        Text(
            text = tvSeries.name ?: "",
            fontSize = 14.sp,
            maxLines = 1,
            )

        Spacer(modifier = Modifier.height(4.dp))

    }
}