package com.example.recipeapp.presentation.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.presentation.components.RecipeBottombar
import com.example.recipeapp.presentation.components.RecipeTopbar
import com.example.recipeapp.presentation.home.HomeScreen
import com.example.recipeapp.presentation.home.HomeViewModel
import com.example.recipeapp.presentation.navigation.RecipeScreens
import com.example.recipeapp.presentation.search.SearchScreen
import com.example.recipeapp.presentation.search.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable

fun DashboardScreen(modifier: Modifier = Modifier, onRecipeItemClicked: (Recipe) -> Unit) {

    val topLevelDestinations = listOf(RecipeScreens.Home, RecipeScreens.SEARCH)
    var currentRoute by rememberSaveable {
        mutableStateOf<String>(RecipeScreens.Home.route)
    }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            RecipeTopbar(
                title = getTitle(currentRoute),
                isBackVisible = false
            ) {
            }
        },
        bottomBar = {
            RecipeBottombar(
                destinations = topLevelDestinations,
                currentRoute = currentRoute
            ) { destination ->
                currentRoute = destination.route
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddings ->
        Surface(modifier = modifier.padding(paddings)) {
            when (currentRoute) {
                RecipeScreens.Home.route -> {
                    val homeViewModel: HomeViewModel = hiltViewModel()
                    HomeScreen(
                        onRecipeItemClicked = onRecipeItemClicked,
                        viewModel = homeViewModel,
                        onErrorReceived = {
                            val job = coroutineScope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                            job.invokeOnCompletion {
                                homeViewModel.consumeError()
                            }
                        }
                    )
                }

                RecipeScreens.SEARCH.route -> {
                    val searchViewModel: SearchViewModel = hiltViewModel()
                    SearchScreen(
                        coroutineScope = coroutineScope,
                        viewModel = searchViewModel,
                        onRecipeItemClicked = onRecipeItemClicked,
                        onErrorReceived = {
                            val job = coroutineScope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                            job.invokeOnCompletion {
                                searchViewModel.consumeError()
                            }
                        })
                }

                else -> {}
            }
        }
    }
}

private fun getTitle(currentTabRoute: String): String {
    return when (currentTabRoute) {
        RecipeScreens.Home.route -> {
            RecipeScreens.Home.label
        }

        RecipeScreens.SEARCH.route -> {
            RecipeScreens.SEARCH.label
        }

        else -> {
            ""
        }
    }
}