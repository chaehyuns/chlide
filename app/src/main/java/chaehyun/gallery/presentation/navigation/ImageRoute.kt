package chaehyun.gallery.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable

@Serializable
sealed class ImageRoute(val route: String) {
    @Serializable
    data object Gallery : ImageRoute("gallery")

    @Serializable
    data class Detail(val imageId: Long) : ImageRoute("detail/$imageId") {
        companion object {
            const val ROUTE = "detail/{imageId}"
            val arguments = listOf(navArgument("imageId") { type = NavType.LongType })
        }
    }
}
