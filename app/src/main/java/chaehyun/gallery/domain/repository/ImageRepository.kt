package chaehyun.gallery.domain.repository

import androidx.paging.PagingData
import chaehyun.gallery.domain.model.GalleryImage
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getPagedImages(): Flow<PagingData<GalleryImage>>

    suspend fun getImageDetail(imageId: Long): GalleryImage
}
