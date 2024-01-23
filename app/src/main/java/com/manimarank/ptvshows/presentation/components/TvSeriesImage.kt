package com.manimarank.ptvshows.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.manimarank.ptvshows.ui.theme.defaultImageBg
import com.manimarank.ptvshows.util.ImageAspectRatio

/**
 * Component for TV Series Image
 */
@Composable
fun TvSeriesImage(
    imagePath: String?,
    contentDescription: String? = null,
    aspectRatio: Float = ImageAspectRatio.poster,
    clipShape: Shape = RoundedCornerShape(0.dp),
    defaultIconSize: Int = 50
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
                    .background(defaultImageBg),
                contentAlignment = Alignment.Center
            ) {
                if (imageState is AsyncImagePainter.State.Error)
                    Image(
                        painterResource(R.drawable.default_image),
                        modifier = Modifier.size(defaultIconSize.dp).alpha(0.5f),
                        contentDescription = contentDescription,
                    )
                else
                    Spacer(modifier = Modifier.size(0.dp))
            }
        }
    }

}