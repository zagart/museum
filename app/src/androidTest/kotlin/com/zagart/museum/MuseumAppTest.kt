package com.zagart.museum

import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.zagart.museum.core.data.di.CoreDataModule
import com.zagart.museum.core.ui.theme.MuseumTheme
import com.zagart.museum.shared.strings.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(value = [CoreDataModule::class])
class MuseumAppTest {

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            MuseumTheme {
                MuseumApp()
            }
        }
    }

    @Test
    fun appTitle_IsVisible() {
        composeTestRule.onNodeWithText(getString(R.string.app_title)).assertIsDisplayed()
    }

    @Test
    fun loadingText_IsVisible() {
        composeTestRule.onNodeWithText(getString(R.string.text_loading)).assertIsDisplayed()
    }

    @Test
    fun settingsTabWithDarkTheme_isVisible() {
        composeTestRule.onNodeWithText(getString(R.string.nav_text_settings)).performClick()
        composeTestRule.onNodeWithText(getString(R.string.settings_item_dark_theme))
            .assertIsDisplayed()
    }

    @StringRes
    private fun getString(res: Int): String {
        return composeTestRule.activity.getString(res)
    }
}