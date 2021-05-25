package com.naughtyenigma.intracranialmajor.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.naughtyenigma.intracranialmajor.R

@Composable
fun AvatarImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    elevation: Dp = 0.dp
) {
    Surface(
        color = Color.LightGray,
        elevation = elevation,
        shape = shape,
        modifier = modifier
    ) {
        Image(
            painter = rememberCoilPainter(
                request = imageUrl,
                previewPlaceholder = R.drawable.placeholder
            ),
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
