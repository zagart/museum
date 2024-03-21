package com.zagart.museum.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import com.zagart.museum.ui.animations.applyZoomIn
import com.zagart.museum.ui.models.IconModel

@Composable
fun BottomBar(
    items: List<IconModel>
) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            val isSelected = index == selectedItemIndex
            NavigationBarItem(
                selected = isSelected,
                label = {
                    Text(
                        modifier = Modifier.applyZoomIn(isSelected),
                        text = item.name,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                icon = {
                    Icon(
                        modifier = Modifier.applyZoomIn(isSelected),
                        painter = rememberVectorPainter(image = if (isSelected) item.activeIcon else item.passiveIcon),
                        contentDescription = item.name
                    )
                }, onClick = {
                    selectedItemIndex = index
                    item.onItemPressed()
                })
        }
    }
}