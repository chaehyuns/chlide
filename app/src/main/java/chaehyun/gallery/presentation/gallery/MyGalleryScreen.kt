package chaehyun.gallery.presentation.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import chaehyun.gallery.R
import chaehyun.gallery.domain.model.User
import chaehyun.gallery.presentation.ui.component.TopRoundedBackground
import chaehyun.gallery.presentation.ui.theme.AppBackgroundGray
import chaehyun.gallery.presentation.ui.theme.AppBlack
import chaehyun.gallery.presentation.ui.theme.AppWhite
import chaehyun.gallery.presentation.ui.theme.BackgroundGray
import chaehyun.gallery.presentation.ui.theme.Typography

@Composable
fun MyGalleryScreen(
    viewModel: GalleryViewModel = hiltViewModel(),
    navigateToDetail: (Long) -> Unit
) {
    val user = User()
    val galleryImages = viewModel.galleryImages.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackgroundGray())
    ) {
        Text(
            modifier = Modifier
                .padding(start = 15.dp, top = 25.dp),
            text = stringResource(id = R.string.gallery_my_carrot),
            color = AppBlack(),
            style = Typography.titleLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        TopRoundedBackground(
            modifier = Modifier
                .fillMaxWidth(),
            color = AppWhite()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                ProfileSection(user)

                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = BackgroundGray
                )

                Text(
                    modifier = Modifier.padding(top = 15.dp, start = 8.dp),
                    text = stringResource(id = R.string.gallery_image),
                    color = AppBlack(),
                    style = Typography.titleSmall
                )

                GallerySection(
                    galleryImages = galleryImages,
                    onImageClick = navigateToDetail
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryScreenPreview() {
    MyGalleryScreen(navigateToDetail = {})
}
