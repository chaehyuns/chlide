package chaehyun.gallery.presentation.gallery

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import chaehyun.gallery.domain.model.User
import org.junit.Rule
import org.junit.Test

class ProfileSectionKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `ProfileSection은_유저_이름과_온도를_표시한다`() {
        val user = User(
            id = 1L,
            name = "당근조아",
            temperature = 99.9
        )

        composeTestRule.setContent {
            ProfileSection(user = user)
        }

        // 유저 이름 확인
        composeTestRule.onNodeWithText(user.name).assertExists()

        // 유저 온도 확인
        composeTestRule.onNodeWithText(user.getTemperatureString()).assertExists()
    }
}
