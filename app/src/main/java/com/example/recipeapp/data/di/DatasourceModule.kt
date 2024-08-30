package com.example.recipeapp.data.di

import com.example.recipeapp.data.datasource.local.recipe.DefaultRecipeLocalDatasource
import com.example.recipeapp.data.datasource.local.recipe.RecipeLocalDatasource
import com.example.recipeapp.data.datasource.remote.category.CategoryRemoteDatasource
import com.example.recipeapp.data.datasource.remote.category.DefaultCategoryRemoteDatasource
import com.example.recipeapp.data.datasource.remote.recipe.DefaultRecipeRemoteDatasource
import com.example.recipeapp.data.datasource.remote.recipe.RecipeRemoteDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DatasourceModule {


    @Binds
    internal abstract fun bindRecipeRemoteDatasource(
        defaultRecipeRemoteDatasource: DefaultRecipeRemoteDatasource
    ): RecipeRemoteDatasource

    @Binds
    internal abstract fun bindCategoryRemoteDatasource(
        defaultCategoryRemoteDatasource: DefaultCategoryRemoteDatasource
    ): CategoryRemoteDatasource

    @Binds
    internal abstract fun bindRecipeLocalDatasource(
        defaultRecipeLocalDatasource: DefaultRecipeLocalDatasource
    ): RecipeLocalDatasource
}