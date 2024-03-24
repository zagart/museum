package com.zagart.museum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.zagart.museum.details.presentation.DetailsViewModel
import com.zagart.museum.home.presentation.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val detailsViewModel by viewModels<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MuseumApp(
                homeViewModel = homeViewModel,
                detailsViewModel = detailsViewModel
            )
        }
    }
}