package chaehyun.chlide.imageload.transform

import android.graphics.Bitmap
import chaehyun.chlide.compose.option.ImageTransform

fun Bitmap.applyTransform(transform: ImageTransform): Bitmap {
    return when (transform) {
        is ImageTransform.Original -> this
        is ImageTransform.Grayscale -> toGrayscale()
    }
}
