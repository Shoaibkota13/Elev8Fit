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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fitness.elev8fit.presentation.intent.RecipeIntent
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.fitness.elev8fit.ui.theme.CustomBackgroundColor
import com.fitness.elev8fit.ui.theme.bg_color
import com.fitness.elev8fit.ui.theme.quicksand
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeEntry(recipemodel : RecipeViewModel,navController: NavController) {
    // States for input fields

val recipestate by recipemodel.state.collectAsState()
    val routine = rememberCoroutineScope()

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
                    .padding(bottom = 8.dp, top = 8.dp),

                shape = CutCornerShape(8.dp,8.dp,0.dp,0.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, // Change this to your desired color
                    unfocusedContainerColor = Color.LightGray,
                    disabledContainerColor = Color.White,
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = quicksand
                )

            )
            Spacer(modifier = Modifier.height(8.dp))

            // Recipe Category Input
            Text("Category:")
            TextField(
                value = recipestate.category,
                onValueChange = { recipemodel.updateCategory(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 8.dp),
                shape = CutCornerShape(8.dp, 8.dp, 0.dp, 0.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, // Change this to your desired color
                    unfocusedContainerColor = Color.LightGray,
                    disabledContainerColor = Color.White,
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = quicksand
                )

            )

            // Preparation Time Input
            Text("Prep Time (in minutes):")
            TextField(
                value = recipestate.prepTime.toString(),
                onValueChange = { recipemodel.updatePrepTime(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 8.dp),
                shape = CutCornerShape(8.dp,8.dp,0.dp,0.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, // Change this to your desired color
                    unfocusedContainerColor = Color.LightGray,
                    disabledContainerColor = Color.White,
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = quicksand
                )

            )

            // Ingredients Input
            Text("Ingredients (comma separated):")
            TextField(
                value = recipestate.recipeIngredient.joinToString (","),
                onValueChange = { recipemodel.updateRecipeIngredients(it.split(",").map { it.trim() }) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 8.dp)
                    .heightIn(100.dp),
                shape = CutCornerShape(8.dp,8.dp,0.dp,0.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, // Change this to your desired color
                    unfocusedContainerColor = Color.LightGray,
                    disabledContainerColor = Color.White,
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = quicksand
                )

            )

            // Instructions Input
            Text("Instructions (comma separated):")
            TextField(
                value = recipestate.instruction.joinToString (","),
                onValueChange = { recipemodel.updateRecipeInstructions(it.split(",").map { it.trim() }) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 8.dp)
                    .heightIn(100.dp),
                shape = CutCornerShape(8.dp,8.dp,0.dp,0.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, // Change this to your desired color
                    unfocusedContainerColor = Color.LightGray,
                    disabledContainerColor = Color.White,
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = quicksand
                )

            )

            // Benefits Input
            Text("Benefits:")
            TextField(
                value = recipestate.benfits,
                onValueChange = {recipemodel.updateRecipeBenefits(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 8.dp),
                shape = CutCornerShape(8.dp,8.dp,0.dp,0.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, // Change this to your desired color
                    unfocusedContainerColor = Color.LightGray,
                    disabledContainerColor = Color.White,
                ),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = quicksand
                )

            )

                // Submit Button
                Button(
                    onClick = {
                        routine.launch {
                            recipemodel.handlerecipeintent(RecipeIntent.SubmitRecipe(recipestate))

                        }
                        navController.navigate(Navdestination.home.toString())

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

