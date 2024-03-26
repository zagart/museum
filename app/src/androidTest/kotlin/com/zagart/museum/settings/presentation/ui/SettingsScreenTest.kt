package com.zagart.museum.settings.presentation.ui

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
import com.zagart.museum.shared.strings.R
import com.zagart.museum.shared.strings.StringProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(value = [CoreDataModule::class])
class SettingsScreenTest {

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            MuseumTheme {
                NavHost(navController = rememberNavController(), startDestination = "settings") {
                    composable("settings") {
                        SettingsScreen()
                    }
                }
            }
        }
    }

    @Test
    fun darkThemePreference_isVisible() {
        composeTestRule.onNodeWithText(StringProvider.getByResource(R.string.settings_item_dark_theme))
            .assertIsDisplayed()
    }

    @Test
    fun languagePreference_isVisible() {
        composeTestRule.onNodeWithText(StringProvider.getByResource(R.string.settings_item_language))
            .assertIsDisplayed()
    }
}