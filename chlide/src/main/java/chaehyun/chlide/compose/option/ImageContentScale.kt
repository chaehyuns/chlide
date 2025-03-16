package chaehyun.chlide.compose.option

sealed class ImageContentScale {
    data object Crop : ImageContentScale()
    data object Fit : ImageContentScale()
    data object FillBounds : ImageContentScale()
    data object Inside : ImageContentScale()
}
