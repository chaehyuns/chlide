package chaehyun.chlide.cache

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.FileOutputStream

@RunWith(AndroidJUnit4::class)
class DiskCacheTest {
    private lateinit var context: Context
    private lateinit var diskCache: DiskCache
    private lateinit var tempCacheDir: File

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        tempCacheDir = File(context.cacheDir, "test_cache_${System.nanoTime()}").apply { mkdirs() }

        diskCache = DiskCache(context, maxSize = 1024 * 1024) // 1MB
    }

    @After
    fun tearDown() {
        tempCacheDir.deleteRecursively()
    }

    @Test
    fun `test_put_and_get`() {
        // Given
        val key = "test_image"
        val bitmap = createTestBitmap()

        // When
        diskCache.put(key, bitmap)
        val resultBitmap = diskCache.get(key)

        // Then
        assertTrue(areBitmapsEqual(bitmap, resultBitmap!!))
    }

    @Test
    fun `test_get`() {
        // Given
        val key = "non_existing_image"

        // When
        val result = diskCache.get(key)

        // Then
        assertNull(result)
    }

    private fun createTestBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val tempFile = File(tempCacheDir, "temp_bitmap.png")

        FileOutputStream(tempFile).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        }

        val loadedBitmap = BitmapFactory.decodeFile(tempFile.absolutePath)
            ?: throw IllegalStateException("decode bitmap 실패")

        tempFile.delete()

        return loadedBitmap
    }

    private fun areBitmapsEqual(bitmap1: Bitmap, bitmap2: Bitmap): Boolean {
        return bitmap1.width == bitmap2.width &&
                bitmap1.height == bitmap2.height &&
                bitmap1.byteCount == bitmap2.byteCount
    }
}
