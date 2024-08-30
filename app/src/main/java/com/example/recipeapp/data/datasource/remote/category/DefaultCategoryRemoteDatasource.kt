package com.example.recipeapp.data.datasource.remote.category

import com.example.recipeapp.data.api.ApiService
import com.example.recipeapp.data.common.getError
import com.example.recipeapp.data.models.remote.CategoryDTO
import javax.inject.Inject

class DefaultCategoryRemoteDatasource @Inject constructor(private val apiService: ApiService):CategoryRemoteDatasource {
    override suspend fun getCategories(): Result<List<CategoryDTO>?> {
        val response  = apiService.getCategories()
        if(response.isSuccessful)
            return Result.success(response.body()?.categories)
        else
            return Result.failure(Exception(response.getError().message))
    }
}