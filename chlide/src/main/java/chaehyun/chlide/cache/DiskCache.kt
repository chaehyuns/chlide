package chaehyun.chlide.cache

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream

class DiskCache(context: Context, private val maxSize: Long) : Cache {
    private val cacheDir: File = File(context.cacheDir, "cache_image").apply { mkdirs() }

    override fun get(key: String): Bitmap? {
        val file = getCacheFile(key)
        return if (file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
    }

    override fun put(key: String, bitmap: Bitmap) {
        val cacheFile = getCacheFile(key)
        if (!cacheFile.exists()) {
            saveBitmapToFile(cacheFile, bitmap)
        }
        manageCacheSize()
    }

    private fun getCacheFile(key: String): File {
        return File(cacheDir, key.hashCode().toString())
    }

    private fun saveBitmapToFile(file: File, bitmap: Bitmap) {
        FileOutputStream(file).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        }
    }

    private fun manageCacheSize() {
        val allCacheFiles = cacheDir.listFiles() ?: return
        val totalSize = allCacheFiles.sumOf { it.length() }
        if (isCacheSizeExceeded(totalSize)) {
            reduceCacheSize(allCacheFiles, totalSize)
        }
    }

    private fun isCacheSizeExceeded(totalSize: Long): Boolean = totalSize > maxSize

    private fun reduceCacheSize(files: Array<File>, totalSize: Long) {
        var currentSize = totalSize
        files.sortedBy { it.lastModified() }
            .forEach { file ->
                if (currentSize <= maxSize) return
                val fileSize = file.length()
                if (file.delete()) {
                    currentSize -= fileSize
                } else {
                    println("Failed to delete file: ${file.absolutePath}")
                }
            }
    }
}
