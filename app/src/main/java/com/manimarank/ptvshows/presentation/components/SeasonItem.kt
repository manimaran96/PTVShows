package com.manimarank.ptvshows.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manimarank.ptvshows.R
import com.manimarank.ptvshows.domain.model.Season
import com.manimarank.ptvshows.util.DateUtils

/**
 * Component for Season item
 */
@Composable
fun SeasonItem(
    season: Season
) {

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .padding(4.dp)
            .clip(RoundedCornerShape(4.dp))
    ) {
        Box(modifier = Modifier.width(80.dp)) {
            TvSeriesImage(imagePath = season.poster_path, clipShape = RoundedCornerShape(6.dp), contentDescription = season.name, defaultIconSize = 30)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        ) {
            Text(
                text = season.name ?: "",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                if (season.episode_count != null) {
                    Text(
                        text = "${season.episode_count} ${stringResource(id = R.string.episodes)} | ",
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (!season.air_date.isNullOrEmpty())
                    Text(
                        text = DateUtils.displayDate(season.air_date),
                        fontSize = 14.sp,
                    )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (!season.overview.isNullOrEmpty()) {
                Text(
                    text =  season.overview,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}