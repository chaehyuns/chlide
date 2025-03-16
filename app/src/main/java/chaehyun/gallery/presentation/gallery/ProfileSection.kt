package chaehyun.gallery.presentation.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import chaehyun.gallery.R
import chaehyun.gallery.domain.model.User
import chaehyun.gallery.presentation.ui.component.TemperatureText
import chaehyun.gallery.presentation.ui.theme.AppBlack
import chaehyun.gallery.presentation.ui.theme.Typography

@Composable
fun ProfileSection(user: User) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(40.dp),
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = stringResource(id = R.string.gallery_profile)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            modifier = Modifier.wrapContentSize(),
            text = user.name,
            color = AppBlack(),
            style = Typography.titleMedium
        )

        Spacer(modifier = Modifier.width(10.dp))

        TemperatureText(temperature = user.getTemperatureString())
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileSectionPreview() {
    ProfileSection(user = User())
}
