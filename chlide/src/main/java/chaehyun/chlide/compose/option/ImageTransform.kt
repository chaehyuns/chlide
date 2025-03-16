package chaehyun.chlide.compose.option

sealed class ImageTransform {
    data object Original : ImageTransform()
    data object Grayscale : ImageTransform()
}
