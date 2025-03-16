package chaehyun.gallery.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import chaehyun.gallery.R
import chaehyun.gallery.presentation.ui.theme.AppWhite
import chaehyun.gallery.presentation.ui.theme.Gray

@Composable
fun CloseButton(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    backgroundColor: Color = Gray,
    iconTint: Color = AppWhite()
) {
    IconButton(
        modifier = modifier
            .clip(CircleShape)
            .size(40.dp)
            .background(backgroundColor),
        onClick = onClose
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = stringResource(id = R.string.close),
            tint = iconTint
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCloseButton() {
    CloseButton(onClose = {})
}
