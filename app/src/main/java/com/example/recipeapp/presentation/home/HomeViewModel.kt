package com.example.recipeapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.di.MainDispatcher
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
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

    init {
        val test = 1
    }

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val handler = CoroutineExceptionHandler { _, exception ->
        _uiState.update {
            it.copy(error = exception.message)
        }
    }
    fun getRecipes() {
        viewModelScope.launch(mainDispatcher+handler) {
            recipeRepository
                .getRecipes(randomChar = "a")
                .onStart {
                    _uiState.update {
                        it.copy(isLoading = true)
                    }
                }
                .onEach { data ->
                    _uiState.update {
                        it.copy(recipes = data, isLoading = false)
                    }
                }
                .onCompletion {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                }
                .catch {ex->
                    _uiState.update {
                        it.copy(error = ex.message, isLoading = false)
                    }
                }
                .collect()
        }
    }

    fun insertRecipe(recipe: Recipe) {
        viewModelScope.launch(mainDispatcher+handler) {
            recipeRepository.insertRecipe(recipe = recipe)
        }
    }

    fun clearRecipe(id: String) {
        viewModelScope.launch(mainDispatcher+handler) {
            recipeRepository.clearRecipe(id = id)
        }
    }
    fun consumeError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}

data class HomeUiState(
    val recipes: List<Recipe>? = null,
    val isLoading: Boolean? = null,
    val error: String? = null
)