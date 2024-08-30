package com.example.recipeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.di.MainDispatcher
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    @MainDispatcher private val mainDispatcher: kotlinx.coroutines.CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun getRecipes() {
        viewModelScope.launch(mainDispatcher) {
            recipeRepository
                .getRecipes(randomChar = "a")
                .onStart {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }
                }
                .onEach { data ->
                    delay(500)
                    _uiState.update {
                        it.copy(recipes = data, isLoading = false)
                    }
                }
                .onStart {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                }
                .catch {
                    _uiState.update {
                        it.copy(error = it.error)
                    }
                }
                .collect()
        }
    }

    fun insertRecipe(recipe: Recipe) {
        viewModelScope.launch(mainDispatcher) {
            recipeRepository.insertRecipe(recipe = recipe)
        }
    }

    fun clearRecipe(id: String) {
        viewModelScope.launch(mainDispatcher) {
            recipeRepository.clearRecipe(id = id)
        }
    }
}

data class HomeUiState(
    val recipes: List<Recipe>? = null,
    val isLoading: Boolean? = null,
    val error: String? = null
)