package chaehyun.chlide.imageload

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import chaehyun.chlide.pool.BitmapPool
import chaehyun.chlide.utils.createHttpConnection
import java.net.HttpURLConnection

class ImageDownloader {
    fun downloadImage(
        url: String,
        reqWidth: Int = DEFAULT_REQ_WIDTH,
        reqHeight: Int = DEFAULT_REQ_HEIGHT
    ): Bitmap {
        val connection = createHttpConnection(url)

        if (connection.responseCode != HttpURLConnection.HTTP_OK) {
            throw Exception("HTTP error code: ${connection.responseCode}")
        }

        val imageData = connection.inputStream.use { it.readBytes() }

        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeByteArray(imageData, 0, imageData.size, options)

        if (options.outWidth == -1 || options.outHeight == -1) {
            throw Exception("Invalid image data")
        }

        options.inBitmap = BitmapPool.getReusableBitmap(options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        options.inMutable = true

        return BitmapFactory.decodeByteArray(imageData, 0, imageData.size, options)
            ?: throw Exception("Failed to decode bitmap")
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height, width) = options.outHeight to options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    companion object {
        private const val DEFAULT_REQ_WIDTH = 500
        private const val DEFAULT_REQ_HEIGHT = 500
    }
}
