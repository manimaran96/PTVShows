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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manimarank.ptvshows.domain.model.Cast

/**
 * Component for Cast item
 */
@Composable
fun CastItem(
    cast: Cast
) {

    Row(
        modifier = Modifier
            .wrapContentHeight()
            .padding(4.dp)
            .clip(RoundedCornerShape(4.dp))
    ) {
        Box(modifier = Modifier.width(52.dp)) {
            TvSeriesImage(imagePath = cast.profile_path, clipShape = RoundedCornerShape(6.dp), contentDescription = cast.name, defaultIconSize = 24)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        ) {
            Text(
                text = cast.name ?: "",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (!cast.character.isNullOrEmpty()) {
                Text(
                    text =  cast.character,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    maxLines = 3,
                )
            }
        }
    }
}