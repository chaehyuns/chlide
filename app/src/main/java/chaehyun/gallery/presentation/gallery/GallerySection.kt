package chaehyun.gallery.presentation.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import chaehyun.gallery.R
import chaehyun.gallery.domain.model.GalleryImage
import chaehyun.gallery.presentation.gallery.component.GalleryItemCard
import chaehyun.gallery.presentation.gallery.preview.GalleryPreviewParameterProvider
import chaehyun.gallery.presentation.ui.component.EmptyScreen
import chaehyun.gallery.presentation.ui.component.ErrorScreen
import chaehyun.gallery.presentation.ui.component.LoadingScreen
import kotlinx.coroutines.flow.flowOf

@Composable
fun GallerySection(
    galleryImages: LazyPagingItems<GalleryImage>,
    onImageClick: (Long) -> Unit
) {
    when (val state = galleryImages.loadState.refresh) {
        is androidx.paging.LoadState.Loading -> {
            LoadingScreen()
        }

        is androidx.paging.LoadState.Error -> {
            ErrorScreen(
                message = state.error.message ?: stringResource(id = R.string.error_image_load),
                onRetry = { galleryImages.retry() }
            )
        }

        else -> {
            GalleryContent(
                modifier = Modifier.fillMaxSize(),
                galleryImages = galleryImages,
                onClick = { onImageClick(it.id) }
            )
        }
    }
}

@Composable
private fun GalleryContent(
    modifier: Modifier = Modifier,
    galleryImages: LazyPagingItems<GalleryImage>,
    gridCellCount: Int = 3,
    onClick: (GalleryImage) -> Unit
) {
    if (galleryImages.itemCount == 0) {
        EmptyScreen(
            message = stringResource(id = R.string.gallery_empty_content)
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(gridCellCount),
            modifier = modifier,
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = galleryImages.itemCount,
                key = { index -> galleryImages[index]?.id ?: index }
            ) { index ->
                val image = galleryImages[index]
                image?.let {
                    GalleryItemCard(
                        item = it,
                        onClick = { onClick(it) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryContentPreview(
    @PreviewParameter(GalleryPreviewParameterProvider::class) images: List<GalleryImage>
) {
    val pagingData = PagingData.from(images)
    val lazyPagingItems = flowOf(pagingData).collectAsLazyPagingItems()

    GalleryContent(galleryImages = lazyPagingItems, onClick = {})
}
