package com.example.recipeapp.domain.repository

import com.example.recipeapp.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getRecipes(randomChar: String): Flow<List<Recipe>>
    suspend fun insertRecipe(recipe: Recipe)
    suspend fun clearRecipe(id: String)
}