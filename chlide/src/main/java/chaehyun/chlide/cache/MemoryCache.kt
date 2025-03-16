package chaehyun.chlide.cache

import android.graphics.Bitmap

class MemoryCache(private val maxSize: Long) : Cache {
    private val cache: LinkedHashMap<String, Bitmap> =
        LinkedHashMap(16, 0.75f, true)

    private var currentSize = 0

    @Synchronized
    override fun get(key: String): Bitmap? = cache[key]

    @Synchronized
    override fun put(key: String, bitmap: Bitmap) {
        if (isKeyAlreadyCached(key)) return
        addBitmapToCache(key, bitmap)
        trimCacheToMaxSize()
    }

    private fun isKeyAlreadyCached(key: String): Boolean = cache.containsKey(key)

    private fun addBitmapToCache(key: String, bitmap: Bitmap) {
        cache[key] = bitmap
        currentSize += bitmap.byteCount
    }

    @Synchronized
    private fun trimCacheToMaxSize() {
        while (isCacheOverLimit()) {
            removeOldestItem()
        }
    }

    private fun isCacheOverLimit(): Boolean {
        return currentSize > maxSize
    }

    private fun removeOldestItem() {
        val oldestItem = cache.entries.iterator().next()
        currentSize -= oldestItem.value.byteCount
        cache.remove(oldestItem.key)
    }
}
