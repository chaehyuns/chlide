package chaehyun.gallery.domain.model

data class GalleryImage(
    val id: Long = 0L,
    val author: String,
    val width: Int = 40,
    val height: Int = 40,
    val url: String,
    val downloadUrl: String = ""
)
