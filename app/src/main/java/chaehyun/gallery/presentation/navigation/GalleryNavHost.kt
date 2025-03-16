package chaehyun.gallery.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import chaehyun.gallery.presentation.gallery.MyGalleryScreen
import chaehyun.gallery.presentation.imagedetail.ImageDetailScreen

@Composable
fun GalleryNavHost(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = ImageRoute.Gallery.route
    ) {
        composable(route = ImageRoute.Gallery.route) {
            MyGalleryScreen(
                navigateToDetail = { imageId ->
                    navHostController.navigate(ImageRoute.Detail(imageId).route)
                }
            )
        }

        composable(
            route = ImageRoute.Detail.ROUTE,
            arguments = ImageRoute.Detail.arguments
        ) { backStackEntry ->
            val imageId = backStackEntry.arguments?.getLong("imageId") ?: -1L
            ImageDetailScreen(
                imageId = imageId,
                onClose = { navHostController.popBackStack() }
            )
        }
    }
}
