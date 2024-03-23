package com.zagart.museum.ui

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.zagart.museum.details.presentation.DetailsScreen
import com.zagart.museum.details.presentation.DetailsViewModel
import com.zagart.museum.home.presentation.HomeScreen
import com.zagart.museum.home.presentation.HomeViewModel
import com.zagart.museum.ui.components.BottomBar
import com.zagart.museum.ui.icons.IconsProvider
import com.zagart.museum.ui.models.IconModel
import com.zagart.museum.ui.theme.MuseumTheme

@Composable
fun MuseumApp(
    homeViewModel: HomeViewModel,
    detailsViewModel: DetailsViewModel,
) {
    val context = LocalContext.current
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
                        HomeScreen(
                            viewModel = homeViewModel,
                            onItemPressed = { objectNumber ->
                                detailsViewModel.prepare(objectNumber)
                                navController.navigate("details")
                            },
                            onBackPressed = {
                                if (context is Activity) {
                                    context.finish()
                                }
                            }
                        )
                    }
                    composable("details") {
                        DetailsScreen(
                            viewModel = detailsViewModel,
                            onBackPressed = {
                                navController.navigateUp()
                            }
                        )
                    }
                }
                composable("settings") {
                    BackHandler {
                        if (context is Activity) {
                            context.finish()
                        }
                    }
                }
            }
        }
    }
}