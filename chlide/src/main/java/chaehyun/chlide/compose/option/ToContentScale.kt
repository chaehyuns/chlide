package chaehyun.chlide.compose.option

import androidx.compose.ui.layout.ContentScale

fun ImageContentScale.toContentScale(): ContentScale {
    return when (this) {
        is ImageContentScale.Crop -> ContentScale.Crop
        is ImageContentScale.Fit -> ContentScale.Fit
        is ImageContentScale.FillBounds -> ContentScale.FillBounds
        is ImageContentScale.Inside -> ContentScale.Inside
    }
}
