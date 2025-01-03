package com.fitness.elev8fit.presentation.activity.Recipe.RecipeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.fitness.elev8fit.domain.model.Recipe
import com.fitness.elev8fit.presentation.navigation.Navdestination

@Composable
fun RecipeScreen(recipeScreenViewModel: RecipeScreenViewModel, navController: NavController) {
    // Example implementation for the Recipe Screen
    val recipes by recipeScreenViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        recipeScreenViewModel.fetchRecipes()
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(recipes) { recipe ->
            RecipeItem(recipe)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            , // Light greenish-blue background
        contentAlignment = Alignment.Center
    ) {
        FloatingActionButton(onClick = { navController.navigate(Navdestination.recipeentry.toString()) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp, 10.dp, 10.dp, 30.dp), // Padding from the edges
            containerColor = Color.Black
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Recipe",
                tint = Color.White
            )

        }
    }
}

@Composable
fun RecipeItem(recipe: Recipe) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Recipe Image
            if (recipe.image !=0) {
                AsyncImage(
                    model = recipe.image, // Replace with a proper URL if using remote images
                    contentDescription = "Recipe Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            // Recipe Title
            Text(
                text = recipe.RecipeTitle,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Ingredients
            Text(
                text = "Ingredients:",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(top = 4.dp)
            )
            recipe.recipeIngredient.forEach { ingredient ->
                Text(
                    text = "- $ingredient",
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Instructions
            Text(
                text = "Instructions:",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(top = 8.dp)
            )
            recipe.instructions.forEachIndexed { index, step ->
                Text(
                    text = "${index + 1}. $step",
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Preparation Time
            Text(
                text = "Preparation Time: ${recipe.prepTime} mins",
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Benefits
            if (recipe.benifits.isNotEmpty()) {
                Text(
                    text = "Benefits: ${recipe.benifits}",
                    style = TextStyle(fontSize = 14.sp, color = Color.Green),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}
