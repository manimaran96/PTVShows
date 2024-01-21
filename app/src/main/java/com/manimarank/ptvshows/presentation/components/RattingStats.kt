package com.manimarank.ptvshows.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Component for Ratting stats
 */
@Composable
fun RattingStats(rating: Double, rattersCount: Int? = null) {
    Box(modifier = Modifier.wrapContentHeight()) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            val balRatting = 10 - rating

            if (rating > 0)
                Spacer(modifier = Modifier
                    .height(2.dp)
                    .weight(rating.toFloat())
                    .clip(if (balRatting > 0) RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp) else RoundedCornerShape(4.dp))
                    .background(Color.Yellow))


            if (balRatting > 0)
                Spacer(modifier = Modifier
                    .height(2.dp)
                    .alpha(0.3f)
                    .weight(balRatting.toFloat())
                    .clip(if (rating > 0) RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp) else RoundedCornerShape(4.dp))
                    .background(Color.LightGray))

            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = "${(rating*10).toString().take(2)}%",
                color = Color.LightGray,
                fontSize = 12.sp,
                maxLines = 1,
            )

            if (rattersCount != null) {
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "($rattersCount)",
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    maxLines = 1,
                )
            }
        }
    }
}