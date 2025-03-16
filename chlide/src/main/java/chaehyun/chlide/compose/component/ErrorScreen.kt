package chaehyun.chlide.compose.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    message: String= "Error",
) {
    Text(
        modifier = modifier,
        text = message,
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center
    )
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        message = "Error",
    )
}
