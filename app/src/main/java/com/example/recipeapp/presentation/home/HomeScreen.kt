package com.example.recipeapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.pm.ShortcutInfoCompat.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.presentation.components.RecipeItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onRecipeItemClicked: (Recipe) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getRecipes()
    }
    val (recipes, isLoading, errorMessage) = uiState
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
