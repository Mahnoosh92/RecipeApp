package com.example.recipeapp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipeapp.presentation.components.RecipeBottombar
import com.example.recipeapp.presentation.components.RecipeTopbar
import com.example.recipeapp.presentation.detail.DetailScreen
import com.example.recipeapp.presentation.home.HomeScreen
import com.example.recipeapp.presentation.navigation.DETAIL_ROUTE
import com.example.recipeapp.presentation.navigation.HOME_ROUTE
import com.example.recipeapp.presentation.navigation.RecipeScreens
import com.example.recipeapp.presentation.navigation.SEARCH_ROUTE
import com.example.recipeapp.presentation.search.SearchScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val topLevelDestinations = listOf(RecipeScreens.Home, RecipeScreens.SEARCH)
    var currentScreenLabel by rememberSaveable {
        mutableStateOf<String>(RecipeScreens.Home.label)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            RecipeTopbar(
                title = currentScreenLabel,
                isBackVisible = topLevelDestinations.find { it.label == currentScreenLabel } == null
            ) {
                navController.navigateUp()
            }
        },
        bottomBar = {
            RecipeBottombar(
                destinations = topLevelDestinations,
                currentRoute = currentRoute ?: HOME_ROUTE
            ) { destination ->
                currentScreenLabel = destination.label
                navController.navigate(destination.route) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                }
            }
        }) { paddings ->
        NavHost(
            navController = navController,
            startDestination = HOME_ROUTE,
            modifier = modifier.padding(paddings)
        ) {
            composable(HOME_ROUTE) {
                HomeScreen(onRecipeItemClicked = {
                    navController.navigate("detail/${it.recipeId}/${it.recipeImage?.replace("https://www.themealdb.com/images/media/meals/", "")}")
                    currentScreenLabel = RecipeScreens.DETAIL.label
                })
            }
            composable(SEARCH_ROUTE) {
                SearchScreen()
            }
            composable(DETAIL_ROUTE,
                arguments = listOf(
                    navArgument("recipeId") { type = NavType.StringType },
                    navArgument("recipeImage") { type = NavType.StringType }
                )) { backStackEntry ->

                val recipeId = backStackEntry.arguments?.getString("recipeId")
                val recipeImage = backStackEntry.arguments?.getString("recipeImage")
                DetailScreen(recipeId = recipeId, recipeImage = recipeImage)
            }
        }
    }


}