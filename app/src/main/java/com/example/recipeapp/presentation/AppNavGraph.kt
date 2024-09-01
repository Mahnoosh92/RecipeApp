package com.example.recipeapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipeapp.presentation.components.RecipeBottombar
import com.example.recipeapp.presentation.components.RecipeTopbar
import com.example.recipeapp.presentation.dashboard.DashboardScreen
import com.example.recipeapp.presentation.detail.DetailScreen
import com.example.recipeapp.presentation.home.HomeScreen
import com.example.recipeapp.presentation.home.HomeViewModel
import com.example.recipeapp.presentation.navigation.DASHBOARD_ROUTE
import com.example.recipeapp.presentation.navigation.DETAIL_ROUTE
import com.example.recipeapp.presentation.navigation.HOME_ROUTE
import com.example.recipeapp.presentation.navigation.RecipeScreens
import com.example.recipeapp.presentation.navigation.SEARCH_ROUTE
import com.example.recipeapp.presentation.search.SearchScreen
import com.example.recipeapp.ui.theme.RecipeAppTheme
import kotlinx.coroutines.delay

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onThemeChanged: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val mainViewModel: MainViewModel = hiltViewModel()


    NavHost(
        navController = navController,
        startDestination = DASHBOARD_ROUTE,
        modifier = modifier
    ) {
        composable(DASHBOARD_ROUTE) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            DashboardScreen(
                onRecipeItemClicked = {
                    navController.navigate(
                        "detail/${it.recipeId}/${
                            it.recipeImage?.replace(
                                "https://www.themealdb.com/images/media/meals/",
                                ""
                            )
                        }"
                    )
                },
                homeViewModel = homeViewModel
            )
        }
        composable(DETAIL_ROUTE,
            arguments = listOf(
                navArgument("recipeId") { type = NavType.StringType },
                navArgument("recipeImage") { type = NavType.StringType }
            )) { backStackEntry ->

            val recipeId = backStackEntry.arguments?.getString("recipeId")
            val recipeImage = backStackEntry.arguments?.getString("recipeImage")
            DetailScreen(recipeId = recipeId, recipeImage = recipeImage){
                navController.navigateUp()
            }
        }

    }
}