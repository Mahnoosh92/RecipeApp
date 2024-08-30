package com.example.recipeapp.data.datasource.remote.recipe

import com.example.recipeapp.data.api.ApiService
import com.example.recipeapp.data.common.getError
import com.example.recipeapp.data.models.remote.RecipeDTO
import javax.inject.Inject

class DefaultRecipeRemoteDatasource @Inject constructor(private val apiService: ApiService) :
    RecipeRemoteDatasource {
    override suspend fun getRecipes(randomChar: String): Result<List<RecipeDTO>?> {
        val response = apiService.getRecipes(randomChar)
        if (response.isSuccessful)
            return Result.success(response.body()?.recipes)
        else
            return Result.failure(Exception(response.getError().message))

    }
}