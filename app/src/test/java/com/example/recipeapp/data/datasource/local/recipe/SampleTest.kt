package com.example.recipeapp.data.datasource.local.recipe

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.recipeapp.R
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowApplication


@RunWith(RobolectricTestRunner::class)
class SampleTest {
    private lateinit var context: Context
    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }
    @Test
    fun testSomething() {
        assertThat(context.getString(R.string.app_name)).isEqualTo("RecipeApp")
        
    }
}