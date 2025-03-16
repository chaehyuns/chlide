package chaehyun.gallery.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import chaehyun.gallery.presentation.navigation.GalleryNavHost
import chaehyun.gallery.presentation.ui.theme.ShoppingCartTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingCartTheme {
                GalleryNavHost()
            }
        }
    }
}
