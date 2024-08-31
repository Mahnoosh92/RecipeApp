package com.example.recipeapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.pm.ShortcutInfoCompat.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.presentation.components.RecipeBottombar
import com.example.recipeapp.presentation.components.RecipeItem
import com.example.recipeapp.presentation.components.RecipeTopbar
import com.example.recipeapp.presentation.detail.DetailScreen
import com.example.recipeapp.presentation.navigation.DETAIL_ROUTE
import com.example.recipeapp.presentation.navigation.HOME_ROUTE
import com.example.recipeapp.presentation.navigation.RecipeScreens
import com.example.recipeapp.presentation.navigation.SEARCH_ROUTE
import com.example.recipeapp.presentation.search.SearchScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onRecipeItemClicked: (Recipe) -> Unit,
    onErrorReceived: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getRecipes()
    }

    val (recipes, isLoading, errorMessage) = uiState
    errorMessage?.let {
        onErrorReceived(it)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        isLoading?.let {
            if (it)
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        recipes?.let { notNullRecipes ->
            if (notNullRecipes.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(notNullRecipes, key = { it.recipeId }) {
                        RecipeItem(
                            recipe = it,
                            onItemClick = { onRecipeItemClicked(it) },
                            onFavoriteClick = {
                                if (it.isFavorite) {
                                    viewModel.clearRecipe(id = it.recipeId)
                                } else {
                                    viewModel.insertRecipe(recipe = it)
                                }
                            })
                    }
                }
            }
        }
    }

}
