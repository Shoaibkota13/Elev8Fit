//package com.fitness.elev8fit.presentation.activity.Recipe
//
//import android.graphics.Color.*
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.BasicText
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Color as ComposeColor
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.fitness.elev8fit.data.Recipe.RecipeState
//import com.fitness.elev8fit.ui.theme.bg_color
//
//@Composable
//fun Recipe(recipeState: RecipeState) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(bg_color),
//        contentAlignment = Alignment.TopCenter
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            // Recipe Title
//            Text(
//                text = recipeState.recipetitle,
//                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//
//            // Recipe Category
//            Text(
//                text = "Category: ${recipeState.category}",
//                style = MaterialTheme.typography.bodyLarge,
//                color = androidx.compose.ui.graphics.Color.Black,
//                modifier = Modifier.padding(bottom = 16.dp)
//            )
//
//            // Preparation Time
//            Text(
//                text = "Prep Time: ${recipeState.prepTime} minutes",
//                color = Color.Gray,
//                modifier = Modifier.padding(bottom = 16.dp)
//            )
//
//            // Ingredients Section
//            Text(
//                text = "Ingredients:",
//                color = Color.Black,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//            recipeState.recipeIngredient.forEach { ingredient ->
//                Text(
//                    text = "• $ingredient",
//                    color = Color.Black,
//                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
//                )
//            }
//
//            // Instructions Section
//            Text(
//                text = "Instructions:",
//                color = Color.Black,
//                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
//            )
//            recipeState.instruction.forEach { step ->
//                Text(
//                    text = "• $step",
//                    color = Color.Black,
//                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
//                )
//            }
//
//            // Benefits Section
//            if (recipeState.benfits.isNotEmpty()) {
//                Text(
//                    text = "Benefits:",
//                    color = Color.Black,
//                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
//                )
//                Text(
//                    text = recipeState.benfits,
//                    color = Color.Black
//                )
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun RecipePreview() {
//    // Sample data for preview
//    val sampleRecipe = RecipeState(
//        id = 1,
//        recipetitle = "Avocado Toast",
//        recipeIngredient = listOf("1 ripe avocado", "2 slices of bread", "Salt", "Pepper"),
//        image = 0, // You can add an image resource here
//        instruction = listOf("Toast the bread", "Mash the avocado", "Spread on the toast", "Season with salt and pepper"),
//        benfits = "Rich in healthy fats and fiber",
//        prepTime = 10,
//        category = "Breakfast"
//    )
//
//    Recipe(recipeState = sampleRecipe)
//}


package com.fitness.elev8fit.presentation.activity.Recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fitness.elev8fit.presentation.intent.RecipeIntent
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.fitness.elev8fit.ui.theme.CustomBackgroundColor
import com.fitness.elev8fit.ui.theme.bg_color

@Composable
fun RecipeEntry(recipemodel : RecipeViewModel,navController: NavController) {
    // States for input fields

val recipestate by recipemodel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg_color),
        contentAlignment = Alignment.Center
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Text(
                        text = "Elev",
                        color = Color.Blue,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "8Fit",
                        color = Color.Green,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Recipe Title Input
            Text("Recipe Title:")
            TextField(
                value = recipestate.recipetitle,
                onValueChange = { recipemodel.updateRecipeTitle(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = CutCornerShape(8.dp,8.dp,0.dp,0.dp)

            )
            Spacer(modifier = Modifier.height(8.dp))

            // Recipe Category Input
            Text("Category:")
            TextField(
                value = recipestate.category,
                onValueChange = { recipemodel.updateCategory(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = CutCornerShape(8.dp,8.dp,0.dp,0.dp)

            )

            // Preparation Time Input
            Text("Prep Time (in minutes):")
            TextField(
                value = recipestate.prepTime.toString(),
                onValueChange = { recipemodel.updatePrepTime(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = CutCornerShape(8.dp,8.dp,0.dp,0.dp)

            )

            // Ingredients Input
            Text("Ingredients (comma separated):")
            TextField(
                value = recipestate.recipeIngredient.joinToString (","),
                onValueChange = { recipemodel.updateRecipeIngredients(it.split(",").map { it.trim() }) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .heightIn(100.dp),
                shape = CutCornerShape(8.dp,8.dp,0.dp,0.dp)

            )

            // Instructions Input
            Text("Instructions (comma separated):")
            TextField(
                value = recipestate.instruction.joinToString (","),
                onValueChange = { recipemodel.updateRecipeInstructions(it.split(",").map { it.trim() }) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .heightIn(100.dp),
                shape = CutCornerShape(8.dp,8.dp,0.dp,0.dp)

            )

            // Benefits Input
            Text("Benefits:")
            TextField(
                value = recipestate.benfits,
                onValueChange = {recipemodel.updateRecipeBenefits(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = CutCornerShape(8.dp,8.dp,0.dp,0.dp)

            )

                // Submit Button
                Button(
                    onClick = {
                      recipemodel.handlerecipeintent(RecipeIntent.SubmitRecipe(recipestate))
                        navController.navigate(Navdestination.Recipe.toString())

                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = CutCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(CustomBackgroundColor)
                ) {
                    Text("Submit Recipe")
                }

        }
    }
}

