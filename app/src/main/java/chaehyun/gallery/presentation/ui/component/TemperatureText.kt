package chaehyun.gallery.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import chaehyun.gallery.presentation.ui.theme.TemperatureBackground
import chaehyun.gallery.presentation.ui.theme.TemperatureTextColor
import chaehyun.gallery.presentation.ui.theme.Typography

@Composable
fun TemperatureText(
    modifier: Modifier = Modifier,
    temperature: String,
    backgroundColor: Color = TemperatureBackground,
    textColor: Color = TemperatureTextColor,
    cornerRadius: Int = 10
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(cornerRadius.dp)
            )
            .padding(horizontal = 7.dp, vertical = 2.dp)
    ) {
        Text(
            text = temperature,
            color = textColor,
            style = Typography.labelMedium
        )
    }
}

@Preview(showBackground = false)
@Composable
fun TemperatureTextPreview() {
    TemperatureText(temperature = "25°C")
}
