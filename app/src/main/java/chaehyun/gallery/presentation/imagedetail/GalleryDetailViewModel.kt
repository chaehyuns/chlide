package chaehyun.gallery.presentation.imagedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import chaehyun.gallery.domain.model.GalleryImage
import chaehyun.gallery.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GalleryDetailViewModel @Inject constructor(
    repository: ImageRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val galleryImages: Flow<PagingData<GalleryImage>> = repository.getPagedImages()
        .cachedIn(viewModelScope)

    private val _currentPage = MutableStateFlow(0)

    private val _imageId = MutableStateFlow(savedStateHandle[IMAGE_ID_KEY] ?: -1L)
    val imageId: StateFlow<Long> = _imageId

    fun setCurrentPage(page: Int) {
        _currentPage.value = page
    }

    fun setImageId(id: Long) {
        _imageId.value = id
        savedStateHandle[IMAGE_ID_KEY] = id
    }

    companion object {
        private const val IMAGE_ID_KEY = "imageId"
    }
}
