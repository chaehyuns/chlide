package chaehyun.gallery.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Black = Color(0xFF121212)
val White = Color.White
val BackgroundGray = Color(0xFFF4F5F6)
val DarkGray = Color(0xFF2C2C2C)
val TemperatureBackground = Color(0xFFFEDAD0)
val TemperatureTextColor = Color(0xFFF84615)
val Gray = Color(0xFFD5D7D6)
val Orange = Color(0xFFFE6F0F)

@Composable
fun AppWhite(): Color {
    return if (isSystemInDarkTheme()) Black else White
}

@Composable
fun AppBlack(): Color {
    return if (isSystemInDarkTheme()) White else Black
}

@Composable
fun AppBackgroundGray(): Color {
    return if (isSystemInDarkTheme()) DarkGray else BackgroundGray
}
