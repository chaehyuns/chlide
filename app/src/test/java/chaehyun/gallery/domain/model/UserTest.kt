package chaehyun.gallery.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class UserTest {
    @Test
    fun `User 온도가 0도보다 작으면 예외를 던진다`() {
        // given
        val invalidTemperature = -1.0

        // when & then
        val exception = assertThrows<IllegalArgumentException> {
            User(temperature = invalidTemperature)
        }

        assertEquals(User.INVALID_TEMPERATURE, exception.message)
    }

    @Test
    fun `User 온도가 100도보다 크면 예외를 던진다`() {
        // given
        val invalidTemperature = 101.0

        // when & then
        val exception = assertThrows<IllegalArgumentException> {
            User(temperature = invalidTemperature)
        }

        assertEquals(User.INVALID_TEMPERATURE, exception.message)
    }

    @Test
    fun `User 온도가 유효하면 객체가 정상적으로 생성된다`() {
        // given
        val validTemperature = 50.0
        val name = "채현"

        // when
        val user = User(name = name, temperature = validTemperature)

        // then
        assertEquals(name, user.name)
    }

    @Test
    fun `User 온도가 유효하면 온도 문자열이 $temperature℃로 반환된다`() {
        // given
        val temperature = 22.0
        val user = User(temperature = temperature)

        // when
        val temperatureString = user.getTemperatureString()

        // then
        assertEquals("$temperature℃", temperatureString)
    }
}
