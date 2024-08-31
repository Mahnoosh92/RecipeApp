package com.example.recipeapp.presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipeapp.R
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.presentation.components.RecipeItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    viewModel: SearchViewModel,
    onRecipeItemClicked: (Recipe) -> Unit,
    onErrorReceived: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    LazyColumn(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        item {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = viewModel.emailText.value,
                onValueChange = {
                    viewModel.updateEmail(it)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {

                }),
                isError = !viewModel.isEmailValid(),

                supportingText = {
                    if (viewModel.isEmailValid().not())
                        Text(text = "Invalid email", color = MaterialTheme.colorScheme.error)
                },
                label = {
                    Text(text = stringResource(R.string.email))
                }
            )
        }
        item {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                value = viewModel.searchRecipeText.value,
                onValueChange = {
                    viewModel.updateSearchRecipe(it)
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            )
        }
        val (recipes, isLoading) = uiState
        if (isLoading) {
            item {
                CircularProgressIndicator()
            }
        }
        recipes?.let {
            items(it, key = { it.recipeId }) { recipe ->
                RecipeItem(
                    recipe = recipe,
                    onItemClick = { clickedRecipe -> onRecipeItemClicked(clickedRecipe) }) { favRecipe ->
                    if (favRecipe.isFavorite) {
                        viewModel.clearRecipe(id = favRecipe.recipeId)
                    } else {
                        viewModel.insertRecipe(recipe = favRecipe)
                    }
                }
            }
        }
    }
}