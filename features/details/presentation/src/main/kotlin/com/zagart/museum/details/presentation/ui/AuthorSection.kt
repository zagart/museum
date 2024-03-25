package com.zagart.museum.details.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zagart.museum.core.ui.configs.DefaultSpacings
import com.zagart.museum.core.ui.theme.MuseumTheme
import com.zagart.museum.details.presentation.models.DetailsUiAuthor

@Preview
@Composable
fun AuthorSectionPreview() {
    MuseumTheme {
        AuthorSection(
            author = DetailsUiAuthor(
                name = "Test Author Name",
                qualification = "possibly",
                nationality = "nationality"
            )
        )
    }
}

@Composable
fun AuthorSection(modifier: Modifier = Modifier, author: DetailsUiAuthor) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(MaterialTheme.shapes.large)
            .border(4.dp, MaterialTheme.colorScheme.surfaceTint, MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(vertical = DefaultSpacings.itemPadding)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = DefaultSpacings.itemPadding),
            text = author.name,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
        author.roles.takeIf { it.isNotEmpty() }?.let { roles ->
            AuthorLine(text = roles)
        }
    }
}

@Composable
fun AuthorLine(text: String) {
    Text(
        modifier = Modifier
            .padding(horizontal = DefaultSpacings.itemPadding),
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onTertiaryContainer
    )
}