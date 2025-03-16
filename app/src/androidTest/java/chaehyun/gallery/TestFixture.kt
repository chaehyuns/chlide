package chaehyun.gallery

import chaehyun.gallery.domain.model.GalleryImage

val galleryImage = GalleryImage(
    url = "https://search.pstatic.net/sunny/?src=https%3A%2F%2Fpng.pn" +
        "gtree.com%2Fpng-vector%2F20240817%2Fourlarge%2Fpngtree-happy-new-ye" +
        "ar-2025-wish-png-image_13517587.png&type=a340",
    author = "채현"
)

val fakeGalleryImage: List<GalleryImage> =
    List(12) { index ->
        GalleryImage(
            id = index.toLong(),
            url = "https://unsplash.com/photos/LNRyGwIJr5c0",
            author = "Author $index"
        )
    }
