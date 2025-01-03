package com.fitness.elev8fit.presentation.activity.Recipe.RecipeScreen

import android.util.Log
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
    LaunchedEffect(true) {
        // This ensures the recipe fetching happens once during the initial composition
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
    val showFull = remember { mutableStateOf(false) }
    val showFulls = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp , bottom = 32.dp, top = 8.dp, end = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Recipe Image

            AsyncImage(
                model = recipe.image,
                contentDescription = "Recipe Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop,
                onError = { error ->
                    Log.e("RecipeItem", "Error loading image: ${error.result.throwable}")
                }
            )

            Log.d("RecipeItem", "Image URI: ${recipe.image}")


            // Recipe Title
            Text(
                text = "Title:",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black),
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = recipe.recipeTitle,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold,color = Color.Black),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Ingredients
            Text(
                text = "Ingredients:",
                style = TextStyle(color = Color.Black,fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(top = 4.dp)
            )
            val visibleingrdient = if (showFull.value) recipe.recipeIngredient else recipe.recipeIngredient.take(1)
            visibleingrdient.forEachIndexed { index, step ->
                Text(
                    text = "${index + 1}. $step",
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // "Read More" or "Read Less" for instructions
            if (recipe.instructions.size > 1) {
                TextButton(
                    onClick = { showFull.value = !showFull.value }
                ) {
                    Text(text = if (showFull.value) "Read Less" else "Read More")
                }
            }

            // Instructions
            Text(
                text = "Instructions:",
                style = TextStyle(color = Color.Black,fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(top = 8.dp)
            )

            val visibleInstructions = if (showFulls.value) recipe.instructions else recipe.instructions.take(1)
            visibleInstructions.forEachIndexed { index, step ->
                Text(
                    text = "${index + 1}. $step",
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // "Read More" or "Read Less" for instructions
            if (recipe.instructions.size > 1) {
                TextButton(
                    onClick = { showFulls.value = !showFulls.value }
                ) {
                    Text(text = if (showFulls.value) "Read Less" else "Read More")
                }
            }


            // Preparation Time
            Text(
                text = "Preparation Time: ${recipe.prepTime} mins",
                style = TextStyle(color = Color.Black,fontSize = 14.sp, fontWeight = FontWeight.Bold),
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

@Preview(showBackground = true)
@Composable
fun PreviewRecipeItem() {
    // Mock recipe data for preview
    val mockRecipe = Recipe(
        recipeTitle = "Healthy Salad",
        recipeIngredient = listOf(
            "1 cup Lettuce",
            "1/2 cup Cherry Tomatoes",
            "1/4 cup Cucumber",
            "1/4 cup Red Onion",
            "1 tbsp Olive Oil",
            "1 tbsp Lemon Juice",
            "Salt and Pepper to taste"
        ),
        instructions = listOf(
            "Wash and chop the vegetables.",
            "Toss the vegetables in a bowl.",
            "Add olive oil and lemon juice.",
            "Mix and season with salt and pepper.",
            "Serve immediately."
        ),
        prepTime = 10,
        benifits = "Rich in fiber and antioxidants",
        image = "https://www.example.com/healthy_salad.jpg"
    )

    // RecipeItem composable with mock data
    RecipeItem(recipe = mockRecipe)
}



//@Composable
//fun MyScreen() {
//    val annotatedString = buildAnnotatedString {
//        // Regular text
//        append("By clicking, you agree to our ")
//
//        // Clickable "Terms and Conditions" with underlining and blue color
//        pushStringAnnotation(tag = "terms", annotation = "terms")
//        withStyle(style = TextStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
//            append("Terms and Conditions")
//        }
//        pop()
//
//        // Regular text
//        append(" and ")
//
//        // Clickable "Privacy Policy" with underlining and blue color
//        pushStringAnnotation(tag = "privacy", annotation = "privacy")
//        withStyle(style = TextStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
//            append("Privacy Policy")
//        }
//        pop()
//
//        // End text
//        append(".")
//    }
//
//    // ClickableText for handling actions when the user clicks on the text
//    ClickableText(
//        text = annotatedString,
//        onClick = { offset ->
//            annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.let {
//                when (it.item) {
//                    "terms" -> {
//                        // Handle terms click (e.g., open terms page)
//                        println("Terms clicked")
//                    }
//                    "privacy" -> {
//                        // Handle privacy policy click (e.g., open privacy policy page)
//                        println("Privacy Policy clicked")
//                    }
//                }
//            }
//        },
//        modifier = Modifier.padding(16.dp)
//    )
//}

