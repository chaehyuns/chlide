package chaehyun.gallery.presentation.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import chaehyun.gallery.domain.model.GalleryImage
import chaehyun.gallery.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    repository: ImageRepository
) : ViewModel() {
    val galleryImages: Flow<PagingData<GalleryImage>> = repository.getPagedImages()
        .cachedIn(viewModelScope)
}
