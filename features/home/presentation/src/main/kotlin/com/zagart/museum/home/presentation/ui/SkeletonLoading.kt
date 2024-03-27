package com.zagart.museum.home.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zagart.museum.core.ui.configs.DefaultSpacings
import com.zagart.museum.core.ui.extentions.disableTouchEvents
import com.zagart.museum.home.presentation.models.HomeScreenImageUi
import com.zagart.museum.home.presentation.models.HomeScreenModelUi
import com.zagart.museum.shared.strings.R

@Composable
fun SkeletonLoading() {
    LazyColumn(
        modifier = Modifier.disableTouchEvents(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(10) { index ->
            if (index == 0) {
                Text(
                    modifier = Modifier.padding(DefaultSpacings.itemPadding),
                    text = stringResource(id = R.string.text_updating),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            HomeScreenItem(
                modifier = Modifier.disableTouchEvents(),
                item = HomeScreenModelUi(
                    image = HomeScreenImageUi(
                        url = "",
                        height = 900,
                        width = 1600
                    )
                )
            )
        }
    }
}