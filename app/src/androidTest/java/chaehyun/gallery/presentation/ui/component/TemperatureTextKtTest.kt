package chaehyun.gallery.presentation.ui.component

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class TemperatureTextKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `TemperatureText가_정상적으로_렌더링된다`() {
        val temperature = "15°C"
        val customBackgroundColor = Color.Red
        val customTextColor = Color.Yellow

        // given
        composeTestRule.setContent {
            TemperatureText(
                temperature = temperature,
                backgroundColor = customBackgroundColor,
                textColor = customTextColor
            )
        }

        // then
        composeTestRule
            .onNodeWithText(temperature)
            .assertExists()
    }

    @Test
    fun `TemperatureText는_기본_배경색과_텍스트색상으로도_렌더링된다`() {
        val temperature = "30°C"

        // given
        composeTestRule.setContent {
            TemperatureText(temperature = temperature)
        }

        // then
        composeTestRule
            .onNodeWithText(temperature)
            .assertExists()
    }
}
