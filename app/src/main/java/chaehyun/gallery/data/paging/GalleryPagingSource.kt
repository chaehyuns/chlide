package chaehyun.gallery.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import chaehyun.gallery.data.mapper.toDomainList
import chaehyun.gallery.data.network.PicsumApiService
import chaehyun.gallery.domain.model.GalleryImage

class GalleryPagingSource(
    private val apiService: PicsumApiService
) : PagingSource<Int, GalleryImage>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryImage> {
        val page = params.key ?: 1
        val limit = params.loadSize

        return try {
            val response = apiService.getImages(page = page, limit = limit)
            val images = response.toDomainList()
            LoadResult.Page(
                data = images,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (images.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryImage>): Int? {
        return state.anchorPosition
    }
}
