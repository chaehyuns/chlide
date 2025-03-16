package chaehyun.gallery.presentation.imagedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import chaehyun.chlide.compose.URLImage
import chaehyun.chlide.compose.option.ImageContentScale
import chaehyun.gallery.R
import chaehyun.gallery.domain.model.GalleryImage
import chaehyun.gallery.presentation.ui.component.CloseButton
import chaehyun.gallery.presentation.ui.component.ErrorScreen
import chaehyun.gallery.presentation.ui.component.LoadingScreen
import chaehyun.gallery.presentation.ui.theme.AppWhite

@Composable
fun ImageDetailScreen(
    imageId: Long,
    onClose: () -> Unit,
    viewModel: GalleryDetailViewModel = hiltViewModel()
) {
    val pagedImages = viewModel.galleryImages.collectAsLazyPagingItems()
    val savedImageId by viewModel.imageId.collectAsState()

    val initialPageCalculated = remember { mutableStateOf(false) }
    val pagerState = rememberPagerState { pagedImages.itemCount }

    var scale by remember { mutableFloatStateOf(1f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val transformableState = remember {
        TransformableState { zoomChange, panChange, _ ->
            scale = (scale * zoomChange).coerceIn(1f, 5f)
            if (scale > 1f) {
                offsetX += panChange.x
                offsetY += panChange.y
            } else {
                offsetX = 0f
                offsetY = 0f
            }
        }
    }

    LaunchedEffect(pagedImages.loadState.refresh) {
        if (savedImageId != imageId) viewModel.setImageId(imageId)
        if (!initialPageCalculated.value && pagedImages.loadState.refresh is androidx.paging.LoadState.NotLoading) {
            val initialPage = pagedImages.itemSnapshotList.items.indexOfFirst { it.id == imageId }.takeIf { it != -1 } ?: 0
            pagerState.scrollToPage(initialPage)
            viewModel.setCurrentPage(initialPage)
            initialPageCalculated.value = true
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        viewModel.setCurrentPage(pagerState.currentPage)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppWhite())
    ) {
        when (pagedImages.loadState.refresh) {
            is androidx.paging.LoadState.Loading -> LoadingScreen()
            is androidx.paging.LoadState.Error -> ErrorScreen(
                message = stringResource(id = R.string.error_image_load),
                onRetry = { pagedImages.refresh() }
            )
            else -> {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .transformable(state = transformableState),
                    flingBehavior = PagerDefaults.flingBehavior(state = pagerState)
                ) { page ->
                    pagedImages[page]?.let { image ->
                        ImageDetailContent(
                            modifier = Modifier.fillMaxSize(),
                            imageDetail = image,
                            scale = scale,
                            offsetX = offsetX,
                            offsetY = offsetY
                        )
                    }
                }
            }
        }

        CloseButton(
            onClose = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 30.dp, end = 20.dp)
        )
    }
}

@Composable
fun ImageDetailContent(
    modifier: Modifier = Modifier.fillMaxSize(),
    imageDetail: GalleryImage,
    scale: Float,
    offsetX: Float,
    offsetY: Float
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offsetX.takeIf { scale > 1f } ?: 0f,
                translationY = offsetY.takeIf { scale > 1f } ?: 0f
            )
    ) {
        URLImage(
            modifier = Modifier.fillMaxSize(),
            url = imageDetail.downloadUrl,
            contentScale = ImageContentScale.Fit
        )
    }
}
