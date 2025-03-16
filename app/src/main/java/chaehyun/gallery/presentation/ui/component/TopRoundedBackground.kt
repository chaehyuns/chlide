package chaehyun.gallery.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import chaehyun.gallery.presentation.ui.theme.AppWhite

@Composable
fun TopRoundedBackground(
    modifier: Modifier = Modifier,
    color: Color = AppWhite(),
    cornerRadius: Dp = 10.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(
                    topStart = cornerRadius,
                    topEnd = cornerRadius,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
    ) {
        content()
    }
}

@Preview(showBackground = false)
@Composable
fun RoundedBackgroundPreview() {
    TopRoundedBackground {
        Box(
            modifier = Modifier
                .padding(15.dp)
                .background(Color(0xFFDDE0E4))
        ) {}
    }
}
