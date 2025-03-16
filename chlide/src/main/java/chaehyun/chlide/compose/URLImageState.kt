package chaehyun.chlide.compose

import android.graphics.Bitmap

sealed class URLImageState {
    data object Loading : URLImageState()
    data class Success(val bitmap: Bitmap) : URLImageState()
    data object Error : URLImageState()
}
