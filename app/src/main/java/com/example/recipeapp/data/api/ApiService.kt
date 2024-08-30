package com.example.recipeapp.data.api

import com.example.recipeapp.data.models.remote.CategoryResponseDTO
import com.example.recipeapp.data.models.remote.RecipeResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search.php")
    suspend fun getRecipes(@Query("f") randomChar: String): Response<RecipeResponseDTO>

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryResponseDTO>

    @GET("lookup.php")
    fun getRecipeDetailById(@Query("i") recipeId: String): Response<RecipeResponseDTO>

    @GET("search.php")
    fun search(@Query("s") query: String): Response<RecipeResponseDTO>
}