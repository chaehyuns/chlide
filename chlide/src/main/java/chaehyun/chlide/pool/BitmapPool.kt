package chaehyun.chlide.pool

import android.graphics.Bitmap
import android.graphics.BitmapFactory

object BitmapPool {
    private val pool = mutableListOf<Bitmap>()

    fun getReusableBitmap(options: BitmapFactory.Options): Bitmap? {
        synchronized(pool) {
            val iterator = pool.iterator()
            while (iterator.hasNext()) {
                val bitmap = iterator.next()
                if (bitmap.isMutable &&
                    bitmap.width >= options.outWidth &&
                    bitmap.height >= options.outHeight
                ) {
                    iterator.remove()
                    return bitmap
                }
            }
        }
        return null
    }
}
