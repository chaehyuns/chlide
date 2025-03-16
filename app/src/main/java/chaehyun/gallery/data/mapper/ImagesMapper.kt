package chaehyun.gallery.data.mapper

import chaehyun.gallery.data.dto.ImagesResponse
import chaehyun.gallery.domain.model.GalleryImage

fun ImagesResponse.toDomain(): GalleryImage {
    return GalleryImage(
        id = this.id,
        author = this.author,
        width = this.width,
        height = this.height,
        url = this.url,
        downloadUrl = this.downloadUrl
    )
}

fun List<ImagesResponse>.toDomainList(): List<GalleryImage> {
    return this.map { it.toDomain() }
}
