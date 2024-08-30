package com.example.recipeapp.data.datasource.remote.category

import com.example.recipeapp.data.api.ApiService
import com.example.recipeapp.data.models.remote.CategoryDTO
import com.example.recipeapp.data.models.remote.CategoryResponseDTO
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class DefaultCategoryRemoteDatasourceTest {

    @Mock
    lateinit var apiService: ApiService

    lateinit var categoryRemoteDatasource: CategoryRemoteDatasource

    private val responseRemoteSuccessful =
        Response.success<CategoryResponseDTO>(200, CategoryResponseDTO(categories = listOf(CategoryDTO.DEFAULT, CategoryDTO.SECONDARY)))

    private val responseRemoteError = Response.error<CategoryResponseDTO>(
        400,
        "{\"message\":\"sample error\"}"
            .toResponseBody("application/json".toMediaTypeOrNull())
    )
    @Before
    fun setUp() {
        categoryRemoteDatasource = DefaultCategoryRemoteDatasource(apiService)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `should call getCategories and return success`() = runTest {
        Mockito.`when`(apiService.getCategories()).thenReturn(responseRemoteSuccessful)

        val result = categoryRemoteDatasource.getCategories()

        Mockito.verify(apiService, times(1)).getCategories()
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()?.size).isEqualTo(2)
    }
    @Test
    fun `should call getCategories and return failure`() = runTest {
        Mockito.`when`(apiService.getCategories()).thenReturn(responseRemoteError)

        val result = categoryRemoteDatasource.getCategories()

        Mockito.verify(apiService, times(1)).getCategories()
        assertThat(result.isSuccess).isFalse()
        assertThat(result.exceptionOrNull()).isInstanceOf(Exception::class.java)

    }

}