package com.example.recipeapp.data.repository

import app.cash.turbine.test
import com.example.recipeapp.data.datasource.local.recipe.RecipeLocalDatasource
import com.example.recipeapp.data.datasource.remote.recipe.RecipeRemoteDatasource
import com.example.recipeapp.data.models.local.RecipeEntity
import com.example.recipeapp.data.models.remote.RecipeDTO
import com.example.recipeapp.domain.mapper.toRecipe
import com.example.recipeapp.domain.repository.RecipeRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultRecipeRepositoryTest {

    @Mock
    private lateinit var recipeRemoteDatasource: RecipeRemoteDatasource

    @Mock
    private lateinit var recipeLocalDatasource: RecipeLocalDatasource

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var recipeRepository: RecipeRepository

    val scope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        recipeRepository = DefaultRecipeRepository(
            recipeRemoteDatasource = recipeRemoteDatasource,
            recipeLocalDatasource = recipeLocalDatasource,
            ioDispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `should return a list of recipes when getRecipes is called remotely  and data base is empty`() =
        scope.runTest {
            Mockito.`when`(recipeLocalDatasource.getRecipes())
                .thenReturn(flowOf(emptyList<RecipeEntity>()))
            Mockito.`when`(recipeRemoteDatasource.getRecipes("")).thenReturn(
                Result.success(
                    listOf(
                        RecipeDTO.DEFAULT, RecipeDTO.SECONDARY
                    )
                )
            )

            recipeRepository.getRecipes("").test{
                assertThat(awaitItem()).isEqualTo(listOf(RecipeDTO.DEFAULT.toRecipe(), RecipeDTO.SECONDARY.toRecipe()))
                awaitComplete()
            }
        }

    @Test
    fun `should return a list of recipes when getRecipes is called remotely when data base is not empty and change isFavourite`()= scope.runTest{
        Mockito.`when`(recipeLocalDatasource.getRecipes())
            .thenReturn(flowOf(listOf(RecipeEntity.DEFAULT.copy(isFavourite = true))))

        Mockito.`when`(recipeRemoteDatasource.getRecipes("")).thenReturn(
            Result.success(
                listOf(
                    RecipeDTO.DEFAULT, RecipeDTO.SECONDARY
                )
            )
        )

        recipeRepository.getRecipes("").test{
            assertThat(awaitItem()).isEqualTo(listOf(RecipeDTO.DEFAULT.toRecipe().copy(isFavorite = true), RecipeDTO.SECONDARY.toRecipe()))
            awaitComplete()
        }
    }
}