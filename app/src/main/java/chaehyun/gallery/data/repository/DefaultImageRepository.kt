package chaehyun.gallery.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import chaehyun.gallery.data.mapper.toDomain
import chaehyun.gallery.data.network.PicsumApiService
import chaehyun.gallery.data.paging.GalleryPagingSource
import chaehyun.gallery.domain.model.GalleryImage
import chaehyun.gallery.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultImageRepository @Inject constructor(
    private val apiService: PicsumApiService
) : ImageRepository {

    override fun getPagedImages(): Flow<PagingData<GalleryImage>> {
        return createPager().flow
    }

    private fun createPager(): Pager<Int, GalleryImage> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GalleryPagingSource(apiService) }
        )
    }

    override suspend fun getImageDetail(imageId: Long): GalleryImage {
        val response = apiService.getImageDetail(imageId)
        return response.toDomain()
    }
}
