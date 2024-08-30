package com.example.recipeapp.data.datasource.remote.recipe

import com.example.recipeapp.data.models.remote.RecipeDTO

interface RecipeRemoteDatasource {
    suspend fun getRecipes(randomChar: String): Result<List<RecipeDTO>?>
}