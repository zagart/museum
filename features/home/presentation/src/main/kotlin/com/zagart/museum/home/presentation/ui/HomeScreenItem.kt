package com.zagart.museum.home.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zagart.museum.core.ui.components.RemoteImage
import com.zagart.museum.core.ui.configs.DefaultSpacings
import com.zagart.museum.home.presentation.models.HomeScreenModelUi

@Composable
fun HomeScreenItem(
    modifier: Modifier = Modifier,
    item: HomeScreenModelUi = HomeScreenModelUi(),
    onItemPressed: (String) -> Unit = {}
) {
    Surface(
        modifier = modifier.clip(MaterialTheme.shapes.small)
    ) {
        Box(
            modifier = modifier
                .wrapContentHeight()
                .clickable { onItemPressed(item.objectNumber) }) {
            item.image?.let { image ->
                //TODO: Reduce cache image quality
                val imageHeightDp = 100.dp
                val imageWidthDp = imageWidthInDp(imageHeightDp, image.height, image.width)

                RemoteImage(
                    modifier = Modifier
                        .height(imageHeightDp)
                        .width(imageWidthDp)
                        .clip(MaterialTheme.shapes.small)
                        .border(
                            4.dp,
                            MaterialTheme.colorScheme.secondaryContainer,
                            MaterialTheme.shapes.small
                        ),
                    height = imageHeightDp,
                    width = imageWidthDp,
                    imageUrl = image.url,
                    contentDescription = item.title
                )

                val innerPadding = 8.dp
                val textSectionHeight = imageHeightDp - innerPadding.times(2f)

                Column(
                    modifier = Modifier
                        .height(textSectionHeight)
                        .fillMaxWidth()
                        .padding(start = imageWidthDp, end = innerPadding)
                        .align(Alignment.Center)
                        .clip(
                            MaterialTheme.shapes.small.copy(
                                topStart = CornerSize(0.dp),
                                bottomStart = CornerSize(0.dp)
                            )
                        )
                        .background(MaterialTheme.colorScheme.secondary),
                ) {
                    val titleHeight = textSectionHeight.times(0.75f)

                    Text(
                        modifier = Modifier
                            .height(titleHeight)
                            .padding(DefaultSpacings.itemPadding),
                        text = item.title,
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    item.productionPlaces?.let { productionPlaces ->
                        MetadataText(
                            modifier = Modifier
                                .height(textSectionHeight.times(0.25f))
                                .padding(horizontal = DefaultSpacings.itemPadding),
                            text = productionPlaces,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            }
        }
    }
}