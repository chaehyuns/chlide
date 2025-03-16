package chaehyun.chlide.imageload

import android.content.Context
import android.graphics.Bitmap
import chaehyun.chlide.cache.CacheManager
import chaehyun.chlide.compose.option.ImageTransform
import chaehyun.chlide.dispatcher.DefaultDispatcherProvider
import chaehyun.chlide.dispatcher.DispatcherProvider
import chaehyun.chlide.imageload.transform.applyTransform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefaultImageLoader(
    private val cacheManager: CacheManager,
    private val imageDownloader: ImageDownloader,
    private val dispatcherProvider: DispatcherProvider
) : ImageLoader {
    private val coroutineScope = CoroutineScope(dispatcherProvider.io)

    override fun loadImage(
        url: String,
        transform: ImageTransform,
        onSuccess: (Bitmap) -> Unit,
        onError: () -> Unit
    ) {
        coroutineScope.launch {
            try {
                val bitmap = loadOrDownloadImage(url, transform)
                withContext(dispatcherProvider.main) { onSuccess(bitmap) }
            } catch (e: Exception) {
                withContext(dispatcherProvider.main) { onError() }
            }
        }
    }

    private fun loadOrDownloadImage(url: String, transform: ImageTransform): Bitmap {
        val cachedBitmap = cacheManager.getBitmapFromCache(url)
        val originalBitmap = cachedBitmap ?: imageDownloader.downloadImage(url)

        val finalBitmap = originalBitmap.applyTransform(transform)

        if (cachedBitmap == null) {
            cacheManager.saveBitmapToCache(url, originalBitmap)
        }

        return finalBitmap
    }

    companion object {
        @Volatile
        private var instance: DefaultImageLoader? = null

        fun getInstance(context: Context): DefaultImageLoader {
            val appContext = context.applicationContext
            return instance ?: synchronized(this) {
                instance ?: DefaultImageLoader(
                    cacheManager = CacheManager.create(applicationContext = appContext),
                    imageDownloader = ImageDownloader(),
                    dispatcherProvider = DefaultDispatcherProvider
                ).also { instance = it }
            }
        }
    }
}
