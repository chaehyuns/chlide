package chaehyun.gallery.presentation.ui.component

import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test

class TopRoundedBackgroundKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `TopRoundedBackground가_정상적으로_렌더링된다`() {
        // given
        val contentText = "test"
        val customColor = Color.Blue
        val customCornerRadius = 20.dp

        composeTestRule.setContent {
            TopRoundedBackground(
                color = customColor,
                cornerRadius = customCornerRadius
            ) {
                Text(text = contentText)
            }
        }

        // then
        composeTestRule
            .onNodeWithText(contentText)
            .assertExists()
    }

    @Test
    fun `TopRoundedBackground는_기본_색상과_Radius로도_렌더링된다`() {
        // given
        val contentText = "test"

        composeTestRule.setContent {
            TopRoundedBackground {
                Text(text = contentText)
            }
        }

        // then
        composeTestRule
            .onNodeWithText(contentText)
            .assertExists()
    }
}
