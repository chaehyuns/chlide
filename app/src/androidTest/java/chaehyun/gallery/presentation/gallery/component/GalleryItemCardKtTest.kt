package chaehyun.gallery.presentation.gallery.component

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import chaehyun.gallery.galleryImage
import org.junit.Rule
import org.junit.Test

class GalleryItemCardKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `GalleryItemCard가_이미지와_저자_이름을_렌더링한다`() {
        // given
        val item = galleryImage

        // when
        composeTestRule.setContent {
            GalleryItemCard(item = item, onClick = {})
        }

        // then
        composeTestRule.onNodeWithText(galleryImage.author).assertExists()
    }
}
