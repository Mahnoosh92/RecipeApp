package com.example.recipeapp.data.di

import com.example.recipeapp.data.repository.DefaultRecipeRepository
import com.example.recipeapp.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {


    @Binds
    internal abstract fun bindRecipeRepository(
        defaultRecipeRepository: DefaultRecipeRepository
    ): RecipeRepository

}