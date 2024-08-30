package com.example.recipeapp.data.datasource.local.recipe

import com.example.recipeapp.data.models.local.RecipeEntity
import kotlinx.coroutines.flow.Flow

interface RecipeLocalDatasource {
    suspend fun clear(id: String)
    fun getRecipes(): Flow<List<RecipeEntity>>
    suspend fun insert(recipeEntity: RecipeEntity)
}