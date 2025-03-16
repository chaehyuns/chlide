package chaehyun.chlide.imageload

import android.graphics.Bitmap
import chaehyun.chlide.compose.option.ImageTransform

interface ImageLoader {
    fun loadImage(
        url: String,
        transform: ImageTransform = ImageTransform.Original,
        onSuccess: (Bitmap) -> Unit,
        onError: () -> Unit
    )
}
