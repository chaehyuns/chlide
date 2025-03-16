package chaehyun.chlide.cache

import android.graphics.Bitmap
import chaehyun.chlide.URL
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.clearInvocations
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class CacheManagerTest {
    private lateinit var memoryCache: Cache
    private lateinit var diskCache: Cache
    private lateinit var cacheManager: CacheManager
    private lateinit var mockBitmap: Bitmap

    @Before
    fun setup() {
        memoryCache = mock(Cache::class.java)
        diskCache = mock(Cache::class.java)
        mockBitmap = mock(Bitmap::class.java)

        cacheManager = CacheManager(
            memoryCache = memoryCache,
            diskCache = diskCache
        )

        clearInvocations(memoryCache, diskCache)
    }

    @Test
    fun `비트맵을 메모리 및 디스크 캐시에 저장한다`() {
        // Given
        val url = URL

        // When
        cacheManager.saveBitmapToCache(url, mockBitmap)

        val result = cacheManager.getBitmapFromCache(url)

        // Then
        assertNull(result)
    }

    @Test
    fun `메모리 캐시에서 비트맵을 성공적으로 가져온다`() {
        // Given
        val url = URL
        `when`(memoryCache.get(url)).thenReturn(mockBitmap)

        // When
        val result = cacheManager.getBitmapFromCache(url)

        // Then
        assertNotNull(result)
        assertEquals(mockBitmap, result)
    }

    fun `디스크 캐시에서 비트맵을 성공적으로 가져온다`() {
        // Given
        val url = URL
        `when`(memoryCache.get(url)).thenReturn(null)
        `when`(diskCache.get(url)).thenReturn(mockBitmap)

        // When
        val result = cacheManager.getBitmapFromCache(url)

        // Then
        assertNotNull(result)
        assertEquals(mockBitmap, result)
    }

    @Test
    fun `메모리 및 디스크 캐시에 없는 URL 요청 시 null을 반환한다`() {
        // Given
        val url = URL
        `when`(memoryCache.get(url)).thenReturn(null)
        `when`(diskCache.get(url)).thenReturn(null)

        // When
        val result = cacheManager.getBitmapFromCache(url)

        // Then
        assertNull(result)
    }
}
