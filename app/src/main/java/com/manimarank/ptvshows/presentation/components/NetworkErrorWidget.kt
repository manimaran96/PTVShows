package com.manimarank.ptvshows.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.manimarank.ptvshows.R

/**
 * Component for Network Error and Refresh action
 */

@Composable
fun NetworkErrorWidget(message: String? = null, refresh: () -> Unit) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                R.raw.error
            )
        )

        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.height(200.dp),
            speed = 2.0f
        )

        Text(
            text = message ?: stringResource(R.string.check_network_connection),
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = refresh,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(R.string.refresh))
        }
    }
}
