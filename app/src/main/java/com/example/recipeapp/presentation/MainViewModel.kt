package com.example.recipeapp.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.datasource.remote.category.CategoryRemoteDatasource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val categoryRemoteDatasource: CategoryRemoteDatasource): ViewModel() {

    val isDark = mutableStateOf(false)

    fun updateTheme() {
        isDark.value = !isDark.value
    }
    init {
        viewModelScope.launch {
            categoryRemoteDatasource.getCategories()
        }
    }
}