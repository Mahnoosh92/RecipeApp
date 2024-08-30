package com.example.recipeapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.data.models.local.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeEntity: RecipeEntity)

    @Query("select * from recipe_table")
    fun getRecipes(): Flow<List<RecipeEntity>>

    @Query("DELETE FROM recipe_table WHERE recipeId = :id")
    suspend fun clear(id: String)
}