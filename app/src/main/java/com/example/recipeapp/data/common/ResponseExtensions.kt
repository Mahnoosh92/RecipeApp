package com.example.recipeapp.data.common

import com.example.recipeapp.data.models.remote.ErrorDTO
import com.google.gson.Gson
import retrofit2.Response

fun <T> Response<T>.getError(): ErrorDTO {
    val responseBody = errorBody()?.string()
//    return Gson().fromJson(responseBody, ErrorDTO::class.java)
    return ErrorDTO(code = 400, message = "Something went wrong")
}