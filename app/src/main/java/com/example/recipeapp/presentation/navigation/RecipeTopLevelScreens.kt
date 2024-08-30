package com.example.recipeapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class RecipeScreens(val route: String, val label: String, val icon: ImageVector, val isTopLevel:Boolean = false) {
    object Home :
        RecipeScreens(route = HOME_ROUTE, label = "Home", icon = Icons.Default.Home, isTopLevel = true)

    object SEARCH :
        RecipeScreens(SEARCH_ROUTE, label = "Search", icon = Icons.Default.Search, isTopLevel = true)

    object DETAIL :
        RecipeScreens(DETAIL_ROUTE, label = "Detail", icon = Icons.Default.Search)
}