package com.example.recipeapp.data.models.remote

import com.google.gson.annotations.SerializedName

data class ErrorDTO(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String?
)
