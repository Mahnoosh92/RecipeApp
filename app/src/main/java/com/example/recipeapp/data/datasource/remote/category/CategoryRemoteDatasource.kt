package com.example.recipeapp.data.datasource.remote.category

import com.example.recipeapp.data.models.remote.CategoryDTO

interface CategoryRemoteDatasource {
    suspend fun getCategories(): Result<List<CategoryDTO>?>
}