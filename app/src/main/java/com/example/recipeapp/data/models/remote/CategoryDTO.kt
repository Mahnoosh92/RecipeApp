package com.example.recipeapp.data.models.remote

import com.google.gson.annotations.SerializedName

data class CategoryDTO(
    @SerializedName("strCategory") var categoryTitle: String?,
    @SerializedName("idCategory") var categoryId: String?,
    @SerializedName("strCategoryThumb") var categoryImage: String?,
    @SerializedName("strCategoryDescription") var categoryDescription: String?,
) {
    companion object {
        val DEFAULT = CategoryDTO(
            categoryTitle = null,
            categoryId = "1",
            categoryImage = null,
            categoryDescription = null
        )
        val SECONDARY = CategoryDTO(
            categoryTitle = null,
            categoryId = "1",
            categoryImage = null,
            categoryDescription = null
        )
    }
}
