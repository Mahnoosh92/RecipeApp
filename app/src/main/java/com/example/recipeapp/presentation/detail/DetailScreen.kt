package com.example.recipeapp.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.recipeapp.R
import com.example.recipeapp.presentation.components.RecipeTopbar
import com.example.recipeapp.presentation.navigation.RecipeScreens

@Composable
fun DetailScreen(recipeId: String?, recipeImage: String?, modifier: Modifier = Modifier, onBackPressed:()->Unit) {
    Scaffold(
        topBar = {
            RecipeTopbar(
                title = RecipeScreens.DETAIL.label,
                isBackVisible = true,
                onBackClicked = onBackPressed
            )
        },
        bottomBar = {

        }) { paddings ->
        Surface(modifier = modifier.padding(paddings)) {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://www.themealdb.com/images/media/meals/" + recipeImage)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.mipmap.ic_recipe),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .size(150.dp)
                        .clip(CircleShape)

                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .size(150.dp)
                        .background(Color.Red, CircleShape)
                        .clip(
                            CircleShape
                        )
                ) {}
                recipeId?.let {
                    Text(text = recipeId)
                }
            }
        }
    }
}