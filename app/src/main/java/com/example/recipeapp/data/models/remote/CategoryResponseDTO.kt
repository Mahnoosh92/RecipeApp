package com.example.recipeapp.data.models.remote

import com.google.gson.annotations.SerializedName

data class CategoryResponseDTO(@SerializedName("categories") var categories: List<CategoryDTO>?)
