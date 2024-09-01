package com.example.recipeapp.presentation.search

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.di.MainDispatcher
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    @MainDispatcher private val mainDispatcher: kotlinx.coroutines.CoroutineDispatcher
) :
    ViewModel() {
    val emailText = mutableStateOf("")
    val searchRecipeText = MutableStateFlow("")

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            searchRecipeText
                .filterNotNull()
                .filter {
                    it.isNotBlank()
                }
                .debounce(500)
                .distinctUntilChanged()
                .flatMapLatest {
                    recipeRepository.getRecipes(randomChar = it)
                }
                .collect { recipes ->
                    _uiState.update { oldState ->
                        oldState.copy(
                            recipes = recipes
                        )
                    }
                }
        }

    }

    fun updateEmail(newEmail: String) {
        emailText.value = newEmail
    }

    fun isEmailValid() =
        emailText.value.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(emailText.value).matches()

    fun updateSearchRecipe(newSearchRecipe: String) {
        searchRecipeText.value = newSearchRecipe
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

    fun consumeError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }

}

data class SearchUiState(
    val recipes: List<Recipe>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)