package com.example.recipeapp.domain.mapper

import com.example.recipeapp.data.models.local.RecipeEntity
import com.example.recipeapp.data.models.remote.RecipeDTO
import com.example.recipeapp.domain.model.Recipe

fun Recipe.toEntity() = RecipeEntity(
    recipeId = this.recipeId,
    recipeMeal = this.recipeMeal,
    recipeImage = this.recipeImage,
    recipeInstructions = this.recipeInstructions,
    recipeTags = this.recipeTags,
    isFavourite = this.isFavorite
)

fun RecipeDTO.toRecipe() = Recipe(
    recipeId = this.recipeId ?: "",
    recipeMeal = this.recipeMeal,
    recipeImage = this.recipeImage,
    recipeInstructions = this.recipeInstructions,
    recipeTags = this.recipeTags
)

fun RecipeDTO.toEntity() = RecipeEntity(
    recipeId = this.recipeId?:"",
    recipeMeal = this.recipeMeal,
    recipeImage = this.recipeImage,
    recipeInstructions = this.recipeInstructions,
    recipeTags = this.recipeTags
)