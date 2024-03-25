package com.zagart.museum

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.zagart.museum.core.ui.components.BottomBar
import com.zagart.museum.core.ui.components.TopBar
import com.zagart.museum.core.ui.icons.IconsProvider
import com.zagart.museum.core.ui.models.IconModel
import com.zagart.museum.core.ui.theme.MuseumTheme
import com.zagart.museum.details.presentation.DetailsScreen
import com.zagart.museum.details.presentation.DetailsViewModel
import com.zagart.museum.home.presentation.HomeScreen
import com.zagart.museum.home.presentation.HomeViewModel
import com.zagart.museum.settings.presentation.ui.SettingsScreen
import com.zagart.museum.settings.presentation.viewmodels.SettingsScreenState
import com.zagart.museum.settings.presentation.viewmodels.SettingsViewModel
import com.zagart.museum.shared.strings.R

@Composable
fun MuseumApp(
    homeViewModel: HomeViewModel,
    detailsViewModel: DetailsViewModel,
    settingsViewModel: SettingsViewModel,
) {
    val navController = rememberNavController()
    val homeListState = rememberLazyListState()
    var forceDarkTheme by rememberSaveable { mutableStateOf(false) }
    val settingsScreenState by settingsViewModel.state.collectAsStateWithLifecycle()
    var leftTopBarButton: IconModel? by remember { mutableStateOf(null) }

    forceDarkTheme = when (val currentState = settingsScreenState) {
        is SettingsScreenState.Success -> currentState.forceDarkTheme
        else -> false
    }

    MuseumTheme(darkTheme = forceDarkTheme || isSystemInDarkTheme()) {
        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(id = R.string.app_title),
                    leftAction = leftTopBarButton
                )
            },
            bottomBar = {
                BottomBar(
                    items = listOf(
                        IconModel(
                            name = stringResource(id = R.string.nav_text_home),
                            activeIcon = IconsProvider.getHomeIcon(true),
                            passiveIcon = IconsProvider.getHomeIcon(false),
                            onItemPressed = {
                                navController.navigate("home")
                            }
                        ),
                        IconModel(
                            name = stringResource(id = R.string.nav_text_settings),
                            activeIcon = IconsProvider.getSettingsIcon(true),
                            passiveIcon = IconsProvider.getSettingsIcon(false),
                            onItemPressed = {
                                leftTopBarButton = null
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
                            listState = homeListState,
                            onItemPressed = { objectNumber ->
                                detailsViewModel.prepare(objectNumber)
                                navController.navigate("details")
                                leftTopBarButton = IconModel.BACK.copy(
                                    onItemPressed = {
                                        leftTopBarButton = null
                                        navController.navigateUp()
                                    }
                                )
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
                    SettingsScreen(viewModel = settingsViewModel)
                }
            }
        }
    }
}