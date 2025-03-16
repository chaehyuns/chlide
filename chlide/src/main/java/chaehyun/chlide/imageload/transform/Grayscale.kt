package chaehyun.chlide.imageload.transform

import android.graphics.Bitmap

fun Bitmap.toGrayscale(): Bitmap {
    val width = this.width
    val height = this.height
    val grayscaleBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    for (x in 0 until width) {
        for (y in 0 until height) {
            val pixel = this.getPixel(x, y)
            val red = (pixel shr 16) and 0xFF
            val green = (pixel shr 8) and 0xFF
            val blue = pixel and 0xFF

            val gray = (red + green + blue) / 3
            val grayPixel = (0xFF shl 24) or (gray shl 16) or (gray shl 8) or gray
            grayscaleBitmap.setPixel(x, y, grayPixel)
        }
    }

    return grayscaleBitmap
}
