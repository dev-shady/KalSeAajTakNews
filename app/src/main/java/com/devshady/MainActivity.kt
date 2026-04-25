package com.devshady

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devshady.navigation.Route
import com.devshady.ui.ViewModelFactory
import com.devshady.ui.details.DetailsScreen
import com.devshady.ui.feed.FeedScreen
import com.devshady.ui.feed.FeedViewModel
import com.devshady.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Required for statusBarsPadding() to work correctly
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        setContent {
            AppTheme {
                MainScreen()
            }

        }
    }

    @Composable
    fun MainScreen() {
        Scaffold(containerColor = Color.Black) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.surface // Setting background to black so white text is visible
            ) {
                val navController = rememberNavController()
                val newsApplication = (application as NewsApplication)
                val appContainer = newsApplication.appContainer

                NavHost(navController = navController, startDestination = Route.NewsFeed) {
                    composable<Route.NewsFeed> {
                        val feedsViewModel = viewModel<FeedViewModel>(factory = ViewModelFactory {
                            FeedViewModel(
                                refreshFeedsUseCase =
                                    appContainer.refreshFeedsUseCase,
                                loadNextPageUseCase =
                                    appContainer.loadNextPageUseCase,
                                getFeedsUseCase = appContainer.getFeedsUseCase
                            )
                        })

                        val feedsUiState = feedsViewModel.feedsUiState.collectAsStateWithLifecycle()
                        val feedsEvents = feedsViewModel.feedsEventFlow

                        FeedScreen(
                            uiState = feedsUiState.value,
                            feedsEvents = feedsEvents,
                            onArticleClick = {
                                navController.navigate(Route.Details(it))
                            }) { page ->
                            feedsViewModel.loadNextPage(page)
                        }
                    }
                    composable<Route.Details> {
                        DetailsScreen(123)
                    }
                }

            }
        }
    }
}