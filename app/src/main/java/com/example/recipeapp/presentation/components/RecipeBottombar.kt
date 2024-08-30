package com.example.recipeapp.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.recipeapp.presentation.navigation.RecipeScreens

@Composable
fun RecipeBottombar(
    modifier: Modifier = Modifier,
    destinations: List<RecipeScreens>,
    currentRoute: String,
    onNavigationBottomItemClicked: (RecipeScreens) -> Unit
) {

    NavigationBar {
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = destination.route == currentRoute,
                onClick = { onNavigationBottomItemClicked(destination) },
                icon = {
                    Icon(imageVector = destination.icon, contentDescription = null)
                },
                label = {
                    Text(text = destination.label)
                }
            )
        }
    }
}