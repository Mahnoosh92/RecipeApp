package com.example.recipeapp.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.recipeapp.R

@Composable
fun DetailScreen(recipeId: String?, recipeImage: String?, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://www.themealdb.com/images/media/meals/"+recipeImage)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.mipmap.ic_recipe),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                
        )
        Spacer(modifier = Modifier.height(10.dp))
        recipeId?.let {
            Text(text = recipeId)
        }
    }
}