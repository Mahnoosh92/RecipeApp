package com.example.recipeapp.data.datasource.local.recipe

import com.example.recipeapp.data.db.dao.RecipeDao
import com.example.recipeapp.data.models.local.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultRecipeLocalDatasource @Inject constructor(private val recipeDao: RecipeDao) :
    RecipeLocalDatasource {
    override suspend fun clear(id: String) = recipeDao.clear(id = id)

    override fun getRecipes(): Flow<List<RecipeEntity>> = recipeDao.getRecipes()

    override suspend fun insert(recipeEntity: RecipeEntity) =
        recipeDao.insert(recipeEntity = recipeEntity)
}