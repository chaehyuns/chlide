package chaehyun.gallery.presentation.gallery.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import chaehyun.gallery.domain.model.GalleryImage

class GalleryPreviewParameterProvider : PreviewParameterProvider<List<GalleryImage>> {
    override val values: Sequence<List<GalleryImage>>
        get() = sequenceOf(
            List(12) { index ->
                GalleryImage(
                    id = index.toLong(),
                    url = "https://unsplash.com/photos/LNRyGwIJr5c0",
                    author = "Author $index"
                )
            },
            emptyList()
        )
}
