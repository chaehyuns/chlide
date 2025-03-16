package chaehyun.chlide.cache

import android.graphics.Bitmap
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class MemoryCacheTest {
    private lateinit var memoryCache: MemoryCache
    private lateinit var mockBitmap100kb: Bitmap
    private lateinit var mockBitmap800kb: Bitmap

    @Before
    fun setup() {
        memoryCache = MemoryCache(maxSize = 1024 * 1024) // 1MB

        mockBitmap100kb = Mockito.mock(Bitmap::class.java).apply {
            Mockito.`when`(byteCount).thenReturn(100 * 1024) 
        }

        mockBitmap800kb= Mockito.mock(Bitmap::class.java).apply {
            Mockito.`when`(byteCount).thenReturn(800 * 1024)
        }
    }

    @Test
    fun `캐시가 정상적으로 저장되고 반환된다`() {
        // Given
        val key = "test_key"

        // When
        memoryCache.put(key, mockBitmap100kb)
        val resultBitmap = memoryCache.get(key)

        // Then
        assertNotNull(resultBitmap)
        assertEquals(mockBitmap100kb, resultBitmap)
    }

    @Test
    fun `같은 key로 캐시를 저장하면 첫번째로 넣은 비트맵만 저장된다`() {
        // Given
        val key = "duplicate_key"
        val firstBitmap = mockBitmap100kb
        val secondBitmap = mockBitmap800kb

        // When
        memoryCache.put(key, firstBitmap)
        memoryCache.put(key, secondBitmap)
        val resultBitmap = memoryCache.get(key)

        // Then
        assertNotNull(resultBitmap)
        assertEquals(firstBitmap, resultBitmap)
    }

    @Test
    fun `maxSize를 넘지 않는 큰 비트맵도 저장된다`() {
        // Given
        val key = "large_bitmap"

        // When
        memoryCache.put(key, mockBitmap800kb)
        val resultBitmap = memoryCache.get(key)

        // Then
        assertNotNull(resultBitmap)
        assertEquals(mockBitmap800kb, resultBitmap)
    }

    @Test
    fun `maxSize를 초과하지 않을 떄 까지 저장된다`() {
        // Given
        val key1 = "bitmap1"
        val key2 = "bitmap2"

        // When
        memoryCache.put(key1, mockBitmap800kb)
        memoryCache.put(key2, mockBitmap100kb)

        // Then
        assertNotNull(memoryCache.get(key1))
        assertNotNull(memoryCache.get(key2))
    }

    @Test
    fun `maxSize를 초과하면 가장 오래된 아이템을 삭제한다`() {
        // Given
        val key1 = "bitmap1"
        val key2 = "bitmap2"

        // When
        memoryCache.put(key1, mockBitmap800kb)
        memoryCache.put(key2, mockBitmap800kb)

        // Then
        assertNull(memoryCache.get(key1))
    }
}
