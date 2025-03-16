package chaehyun.chlide.compose

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import chaehyun.chlide.compose.component.ErrorScreen
import chaehyun.chlide.compose.component.LoadingScreen
import chaehyun.chlide.compose.option.ImageContentScale
import chaehyun.chlide.compose.option.ImageTransform
import chaehyun.chlide.compose.option.toContentScale
import chaehyun.chlide.imageload.DefaultImageLoader
import chaehyun.chlide.imageload.ImageLoader

@Composable
fun URLImage(
    url: String,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader = DefaultImageLoader.getInstance(LocalContext.current),
    loadingView: @Composable (() -> Unit)? = { LoadingScreen() },
    errorView: @Composable (() -> Unit)? = { ErrorScreen() },
    transform: ImageTransform = ImageTransform.Original,
    contentScale: ImageContentScale = ImageContentScale.Crop
) {
    var state by remember { mutableStateOf<URLImageState>(URLImageState.Loading) }

    LaunchedEffect(url) {
        state = URLImageState.Loading
        imageLoader.loadImage(
            url = url,
            transform = transform,
            onSuccess = { bitmap ->
                state = URLImageState.Success(bitmap)
            },
            onError = {
                state = URLImageState.Error
            }
        )
    }

    when (val currentState = state) {
        is URLImageState.Loading -> loadingView?.invoke()
        is URLImageState.Error -> errorView?.invoke()
        is URLImageState.Success -> Image(
            modifier = modifier,
            bitmap = currentState.bitmap.asImageBitmap(),
            contentDescription = null,
            contentScale = contentScale.toContentScale()
        )
    }
}
