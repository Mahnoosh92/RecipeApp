package com.example.recipeapp.data.repository

import com.example.recipeapp.data.datasource.local.recipe.RecipeLocalDatasource
import com.example.recipeapp.data.datasource.remote.recipe.RecipeRemoteDatasource
import com.example.recipeapp.data.di.IoDispatcher
import com.example.recipeapp.domain.mapper.toEntity
import com.example.recipeapp.domain.mapper.toRecipe
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultRecipeRepository @Inject constructor(
    private val recipeRemoteDatasource: RecipeRemoteDatasource,
    private val recipeLocalDatasource: RecipeLocalDatasource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : RecipeRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getRecipes(randomChar: String): Flow<List<Recipe>> {
        return recipeLocalDatasource
            .getRecipes()
            .flatMapLatest { listOfRecipeEntities ->
                val remoteResult = recipeRemoteDatasource.getRecipes(randomChar = randomChar)
                val recipes = mutableListOf<Recipe>()
                if (remoteResult.isSuccess) {
                    remoteResult.getOrNull()?.forEach { recipeDTO ->
                        if (listOfRecipeEntities.find { it.recipeId == recipeDTO.recipeId } != null)
                            recipes.add(recipeDTO.toRecipe().copy(isFavorite = true))
                        else
                            recipes.add(recipeDTO.toRecipe().copy(isFavorite = false))
                    }
                    flowOf(recipes)
                } else {
                    throw Exception(
                        remoteResult.exceptionOrNull()?.message ?: "Something went wronge"
                    )
                }

            }
            .catch {
                throw it
            }
            .flowOn(ioDispatcher)
    }

    override suspend fun insertRecipe(recipe: Recipe) = withContext(ioDispatcher) {
        recipeLocalDatasource
            .insert(recipe.toEntity())
    }

    override suspend fun clearRecipe(id: String) = withContext(ioDispatcher) {
        recipeLocalDatasource.clear(id = id)
    }
}