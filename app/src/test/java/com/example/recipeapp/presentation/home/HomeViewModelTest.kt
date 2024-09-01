package com.example.recipeapp.presentation.home

import com.example.recipeapp.data.di.MainDispatcher
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.domain.repository.RecipeRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.stubbing.Answer
import java.lang.reflect.Method

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @Mock
    private lateinit var recipeRepository: RecipeRepository
    private val testDispatcher = StandardTestDispatcher()
    private val scope = TestScope(testDispatcher)
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(recipeRepository, testDispatcher)
    }

    @After
    fun tearDown() {
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should show onloading true when getRecipes is called`() = scope.runTest {
        Mockito.`when`(recipeRepository.getRecipes("a"))
            .thenAnswer(object : Answer<Flow<List<Recipe>>> {
                override fun answer(invocation: InvocationOnMock?): Flow<List<Recipe>> = flow {
                    delay(100)
                    emit(listOf(Recipe.DEFAULT))
                }
            })
        launch {
            homeViewModel.getRecipes()

        }
        advanceTimeBy(1)
        runCurrent()
        assertThat(homeViewModel.uiState.value.isLoading).isTrue()
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should show onloading false when getRecipes is called with recipe list`() = scope.runTest {
        Mockito.`when`(recipeRepository.getRecipes("a"))
            .thenAnswer(object : Answer<Flow<List<Recipe>>> {
                override fun answer(invocation: InvocationOnMock?): Flow<List<Recipe>> = flow {
                    delay(100)
                    emit(listOf(Recipe.DEFAULT))
                }
            })
        launch {
            homeViewModel.getRecipes()

        }
        advanceTimeBy(100)
        runCurrent()
        assertThat(homeViewModel.uiState.value.isLoading).isFalse()
        assertThat(homeViewModel.uiState.value.recipes).isEqualTo(listOf(Recipe.DEFAULT))
    }
}