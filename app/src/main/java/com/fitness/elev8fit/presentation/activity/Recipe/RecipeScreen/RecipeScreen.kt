package com.fitness.elev8fit.presentation.activity.Recipe.RecipeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
    // Collect the recipe state
    val recipes by recipeScreenViewModel.state.collectAsState()

    // Trigger the fetching of recipes when the screen is launched
    LaunchedEffect(true) {
        recipeScreenViewModel.fetchRecipes(navController)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Ensure the Column takes the full screen space and has padding
        ) {
            // LazyColumn inside the Column
            LazyColumn(
                modifier = Modifier.weight(1f) // To fill the remaining space of the screen
            ) {
                items(recipes) { recipe ->
                    RecipeItem(recipe)
                }
            }
        }

        // FloatingActionButton positioned at the bottom-right corner
        FloatingActionButton(
            onClick = { navController.navigate(Navdestination.recipeentry.toString()) },
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
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Recipe image as background with fixed aspect ratio (adjust if needed)
            AsyncImage(
                model = recipe.image,
                contentDescription = "Recipe Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
                      // Set a fixed height or adjust to fit your design
            )

            // Full-screen black semi-transparent overlay to ensure the whole image gets shaded
            Box(
                modifier = Modifier
                    .matchParentSize() // Ensure it fills the entire space
                    .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent black overlay
            )

            // Content on top of the overlay
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomStart) // Align content to the bottom of the Box
            ) {
                // Recipe Title
                Text(
                    text = recipe.recipeTitle,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                // Ingredients Section
                Text(
                    text = "Ingredients:",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )

                val visibleIngredients =
                    if (showFull.value) recipe.recipeIngredient else recipe.recipeIngredient.take(1)
                visibleIngredients.forEachIndexed { index, ingredient ->
                    Text(
                        text = "${index + 1}. $ingredient",
                        style = TextStyle(fontSize = 14.sp, color = Color.White),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // "Read More" or "Read Less" for ingredients
                if (recipe.recipeIngredient.size > 1) {
                    TextButton(
                        onClick = { showFull.value = !showFull.value }
                    ) {
                        Text(text = if (showFull.value) "Read Less" else "Read More", color = Color.White)
                    }
                }

                // Instructions Section
                Text(
                    text = "Instructions:",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )

                val visibleInstructions =
                    if (showFulls.value) recipe.instructions else recipe.instructions.take(1)
                visibleInstructions.forEachIndexed { index, instruction ->
                    Text(
                        text = "${index + 1}. $instruction",
                        style = TextStyle(fontSize = 14.sp, color = Color.White),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                // "Read More" or "Read Less" for instructions
                if (recipe.instructions.size > 1) {
                    TextButton(
                        onClick = { showFulls.value = !showFulls.value }
                    ) {
                        Text(text = if (showFulls.value) "Read Less" else "Read More", color = Color.White)
                    }
                }


                Text(
                    text = "Recipe Link",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = recipe.dplink,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                // Preparation Time Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "Preparation Time",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${recipe.prepTime} mins",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }

                // Benefits
                if (recipe.benifits.isNotEmpty()) {
                    Text(
                        text = "Benefits:",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = recipe.benifits,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(recipe: Recipe,navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(8.dp, bottom = 32.dp, top = 8.dp, end = 8.dp)
            .height(100.dp)
            .width(200.dp)
            .clickable {
                navController.navigate(Navdestination.Recipe.toString())
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
    ){
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Recipe image as background
            AsyncImage(
                model = recipe.image,
                contentDescription = "Recipe Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )


            // Overlay for text content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent overlay
                    .padding(8.dp)
            ) {
                // Recipe title
                Text(
                    text = recipe.recipeTitle,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White),
                    modifier = Modifier.padding(vertical = 8.dp)
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