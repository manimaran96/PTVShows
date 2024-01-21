package com.manimarank.ptvshows.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.manimarank.ptvshows.domain.model.TvSeries
import com.manimarank.ptvshows.util.Screen

/**
 * Component for TV Series item
 */
@Composable
fun TvSeriesItem(
    tvSeries: TvSeries,
    navHostController: NavHostController
) {

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(4.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                navHostController.navigate(Screen.TvSeriesDetails.route + "/${tvSeries.id}")
            }
    ) {

        TvSeriesImage(imagePath = tvSeries.poster_path, clipShape = RoundedCornerShape(4.dp),  contentDescription = tvSeries.name)

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