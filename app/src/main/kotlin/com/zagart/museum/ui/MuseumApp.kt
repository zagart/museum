package com.zagart.museum.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.zagart.museum.ui.components.BottomBar
import com.zagart.museum.ui.icons.IconsProvider
import com.zagart.museum.ui.models.IconModel
import com.zagart.museum.ui.theme.MuseumTheme

@Composable
fun MuseumApp() {
    val navController = rememberNavController()

    MuseumTheme {
        Scaffold(
            bottomBar = {
                BottomBar(
                    items = listOf(
                        IconModel(
                            name = stringResource(id = com.zagart.museum.shared.strings.R.string.nav_text_home),
                            activeIcon = IconsProvider.getHomeIcon(true),
                            passiveIcon = IconsProvider.getHomeIcon(false),
                            onItemPressed = {
                                navController.navigate("home")
                            }
                        ),
                        IconModel(
                            name = stringResource(id = com.zagart.museum.shared.strings.R.string.nav_text_settings),
                            activeIcon = IconsProvider.getSettingsIcon(true),
                            passiveIcon = IconsProvider.getSettingsIcon(false),
                            onItemPressed = {
                                navController.navigate("settings")
                            }
                        )
                    )
                )
            }
        ) { scaffoldPadding ->
            NavHost(
                modifier = Modifier.padding(scaffoldPadding),
                navController = navController,
                startDestination = "home"
            ) {
                navigation(startDestination = "items", route = "home") {
                    composable("items") {

                    }
                    composable("details") {

                    }
                }
                composable("settings") {

                }
            }
        }
    }
}