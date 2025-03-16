package chaehyun.chlide.cache

import android.content.Context
import android.graphics.Bitmap

class CacheManager(
    private val memoryCache: Cache,
    private val diskCache: Cache,
) {
    fun getBitmapFromCache(url: String): Bitmap? {
        return memoryCache.get(url) ?: diskCache.get(url)?.also { memoryCache.put(url, it) }
    }

    fun saveBitmapToCache(url: String, bitmap: Bitmap) {
        memoryCache.put(url, bitmap)
        diskCache.put(url, bitmap)
    }

    companion object {
        private const val DEFAULT_MEMORY_CACHE_RATIO = 8
        private const val DEFAULT_DISK_CACHE_RATIO = 10

        fun create(
            applicationContext: Context,
            memoryRatio: Int = DEFAULT_MEMORY_CACHE_RATIO,
            diskRatio: Int = DEFAULT_DISK_CACHE_RATIO
        ): CacheManager {
            val memoryCacheSize = calculateMemoryCacheSize(memoryRatio)
            val diskCacheSize = calculateDiskCacheSize(applicationContext, diskRatio)
            return CacheManager(
                memoryCache = MemoryCache(maxSize = memoryCacheSize),
                diskCache = DiskCache(applicationContext, maxSize = diskCacheSize)
            )
        }

        private fun calculateMemoryCacheSize(memoryRatio: Int): Long {
            val maxMemory = Runtime.getRuntime().maxMemory() / 1024
            return maxMemory / memoryRatio
        }

        private fun calculateDiskCacheSize(context: Context, diskRatio: Int): Long {
            val cacheDir = context.cacheDir
            return cacheDir.usableSpace / diskRatio
        }
    }
}
