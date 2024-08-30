package com.example.recipeapp.data.models.remote

import com.google.gson.annotations.SerializedName

data class RecipeResponseDTO (@SerializedName("meals") var recipes: MutableList<RecipeDTO>?)