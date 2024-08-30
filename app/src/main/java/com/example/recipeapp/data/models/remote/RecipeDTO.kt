package com.example.recipeapp.data.models.remote

import com.google.gson.annotations.SerializedName

data class RecipeDTO(
    @SerializedName("idMeal") val recipeId: String?,
    @SerializedName("strMeal") val recipeMeal: String?,
    @SerializedName("strMealThumb") val recipeImage: String?,
    @SerializedName("strInstructions") val recipeInstructions: String?,
    @SerializedName("strTags") val recipeTags: String?,
){
    companion object{
        val DEFAULT = RecipeDTO("1", "meal", "image", "instructions", "tags")
        val SECONDARY = RecipeDTO("2", "meal", "image", "instructions", "tags")
    }
}
