package com.example.recipeapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.datasource.remote.category.CategoryRemoteDatasource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val categoryRemoteDatasource: CategoryRemoteDatasource): ViewModel() {
    init {
        viewModelScope.launch {
            categoryRemoteDatasource.getCategories()
        }
    }
}