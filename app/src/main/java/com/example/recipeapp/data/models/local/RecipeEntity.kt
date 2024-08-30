package com.example.recipeapp.data.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class RecipeEntity(
    @PrimaryKey val recipeId: String,
    @ColumnInfo("recipeMeal") val recipeMeal: String?,
    @ColumnInfo("recipeImage") val recipeImage: String?,
    @ColumnInfo("recipeInstructions") val recipeInstructions: String?,
    @ColumnInfo("recipeTags") val recipeTags: String?,
    @ColumnInfo("isFavourite") val isFavourite: Boolean = false
) {
    companion object {
        val DEFAULT = RecipeEntity("1", "meal", "image", "instructions", "tags", false)
        val SECONDARY = RecipeEntity("2", "meal", "image", "instructions", "tags", false)
    }
}
