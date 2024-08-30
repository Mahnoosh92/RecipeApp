package com.example.recipeapp.domain.model

data class Recipe(
    val recipeId: String,
    val recipeMeal: String?,
    val recipeImage: String?,
    val recipeInstructions: String?,
    val recipeTags: String?,
    val isFavorite: Boolean = false
) {
    companion object {
        val DEFAULT = Recipe(
            recipeId = "",
            recipeMeal = "",
            recipeImage = "",
            recipeInstructions = "",
            recipeTags = "",
            isFavorite = false
        )
        val SECONDARY = Recipe(
            recipeId = "",
            recipeMeal = "",
            recipeImage = "",
            recipeInstructions = "",
            recipeTags = "",
            isFavorite = true
        )
    }
}
