package com.example.recipeapp.data.datasource.local

import androidx.test.filters.MediumTest
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.recipeapp.data.db.AppDatabase
import com.example.recipeapp.data.db.dao.RecipeDao
import com.example.recipeapp.data.models.local.RecipeEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@HiltAndroidTest
@MediumTest
class RecipeDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var recipeDao: RecipeDao

    @Before
    fun setup() {
        hiltRule.inject()
        recipeDao = database.recipeDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertRecipe() = runTest {
        recipeDao.insert(RecipeEntity.DEFAULT)

        recipeDao.getRecipes().test {
            assertThat(awaitItem()).isEqualTo(listOf( RecipeEntity.DEFAULT))
        }
    }

    @Test
    fun insertAndClearRecipe() = runTest {
        recipeDao.insert(RecipeEntity.DEFAULT)
        recipeDao.insert(RecipeEntity.SECONDARY)

        recipeDao.clear(RecipeEntity.DEFAULT.recipeId)

        recipeDao.getRecipes().test {
            assertThat(awaitItem()).isEqualTo(listOf( RecipeEntity.SECONDARY))
        }
    }
}