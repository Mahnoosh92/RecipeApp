package com.example.recipeapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipeapp.data.db.dao.RecipeDao
import com.example.recipeapp.data.models.local.RecipeEntity


@Database(
    entities = [RecipeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}