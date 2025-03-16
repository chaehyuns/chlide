package chaehyun.chlide.cache

import android.graphics.Bitmap

interface Cache {
    fun get(key: String): Bitmap?
    fun put(key: String, bitmap: Bitmap)
}
