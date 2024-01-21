package com.manimarank.ptvshows.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.manimarank.ptvshows.R
import com.manimarank.ptvshows.data.remote.TvSeriesApi
import com.manimarank.ptvshows.util.AppColors
import com.manimarank.ptvshows.util.ImageAspectRatio

@Composable
fun TvSeriesImage(
    imagePath: String?,
    contentDescription: String? = null,
    aspectRatio: Float = ImageAspectRatio.poster,
    clipShape: Shape = RoundedCornerShape(0.dp)
) {

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(TvSeriesApi.imageBaseUrl + imagePath)
            .size(Size.ORIGINAL)
            .build()
    ).state


    when (imageState) {
        is AsyncImagePainter.State.Success -> {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRatio)
                    .clip(clipShape),
                painter = imageState.painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
        }
        else -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRatio)
                    .clip(clipShape)
                    .background(AppColors.defaultImageBg),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(R.drawable.default_image),
                    modifier = Modifier.size(50.dp),
                    contentDescription = contentDescription
                )
            }
        }
    }

}