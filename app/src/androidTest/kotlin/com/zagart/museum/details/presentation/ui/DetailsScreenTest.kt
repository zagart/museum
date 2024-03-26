package com.zagart.museum.details.presentation.ui

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zagart.museum.MainActivity
import com.zagart.museum.core.data.di.CoreDataModule
import com.zagart.museum.core.ui.theme.MuseumTheme
import com.zagart.museum.details.presentation.models.DetailsUiAuthor
import com.zagart.museum.details.presentation.models.DetailsUiModel
import com.zagart.museum.details.presentation.viewmodels.DetailsScreenState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(value = [CoreDataModule::class])
class DetailsScreenTest {

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val detailsUiModel = DetailsUiModel(
        title = "Details screen title",
        description = "Details screen description",
        authors = listOf(
            DetailsUiAuthor(
                name = "Author name"
            )
        )
    )

    @Before
    fun setUp() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            MuseumTheme {
                NavHost(navController = rememberNavController(), startDestination = "details") {
                    composable("details") {
                        DetailsScreen(DetailsScreenState.Success(detailsUiModel))
                    }
                }
            }
        }
    }

    @Test
    fun authorName_isVisible() {
        composeTestRule.onNodeWithText(detailsUiModel.authors?.first()?.name ?: "")
            .assertIsDisplayed()
    }

    @Test
    fun detailsTitle_isVisible() {
        composeTestRule.onNodeWithText(detailsUiModel.title).assertIsDisplayed()
    }

    @Test
    fun detailsDescription_isVisible() {
        composeTestRule.onNodeWithText(detailsUiModel.description).assertIsDisplayed()
    }
}