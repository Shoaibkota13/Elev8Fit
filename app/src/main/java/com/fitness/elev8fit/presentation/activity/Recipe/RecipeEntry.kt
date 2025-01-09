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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.fitness.elev8fit.ui.theme.quicksand
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeEntry(recipemodel : RecipeViewModel, navController: NavController) {
    val recipestate by recipemodel.state.collectAsState()
    val routine = rememberCoroutineScope()
    var instructionInput by remember { mutableStateOf(recipestate.instruction.joinToString(", ")) }
    var ingredientInput by remember { mutableStateOf(recipestate.recipeIngredient.joinToString(", ")) }

    val isFormValid = remember(recipestate) {
        recipestate.recipetitle.isNotBlank() &&
                recipestate.category.isNotBlank() &&
                recipestate.recipeIngredient.isNotEmpty() &&
                recipestate.instruction.isNotEmpty() &&
                recipestate.benfits.isNotBlank()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header
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
            Text("Recipe Title:", color = MaterialTheme.colorScheme.primary, fontFamily = quicksand)
            TextField(
                value = recipestate.recipetitle,
                onValueChange = { recipemodel.updateRecipeTitle(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 8.dp),
                shape = CutCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    focusedTextColor = Color.Black
                ),
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontFamily = quicksand)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Recipe Category Input
            Text("Category:", color = MaterialTheme.colorScheme.primary)
            TextField(
                value = recipestate.category,
                onValueChange = { recipemodel.updateCategory(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 8.dp),
                shape = CutCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    focusedTextColor = Color.Black
                ),
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontFamily = quicksand)
            )

            // Preparation Time Input
            Text("Prep Time (in minutes):", color = MaterialTheme.colorScheme.primary)
            TextField(
                value = recipestate.prepTime.toString(),
                onValueChange = { recipemodel.updatePrepTime(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 8.dp),
                shape = CutCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    focusedTextColor = Color.Black
                ),
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontFamily = quicksand)
            )

            // Ingredients Input
            Text("Ingredients (comma separated):", color = MaterialTheme.colorScheme.primary)
            TextField(
                value = ingredientInput,
                onValueChange = {
                    ingredientInput = it
                    recipemodel.updateRecipeIngredients(
                        ingredientInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 8.dp)
                    .heightIn(100.dp),
                shape = CutCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    focusedTextColor = Color.Black
                ),
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontFamily = quicksand)
            )

            // Instructions Input
            Text("Instructions (comma separated):", color = MaterialTheme.colorScheme.primary)
            TextField(
                value = instructionInput,
                onValueChange = {
                    instructionInput = it
                    recipemodel.updateRecipeInstructions(
                        instructionInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 8.dp)
                    .heightIn(100.dp),
                shape = CutCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    focusedTextColor = Color.Black
                ),
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontFamily = quicksand)
            )

            // Benefits Input
            Text("Benefits:",color = MaterialTheme.colorScheme.primary)
            TextField(
                value = recipestate.benfits,
                onValueChange = { recipemodel.updateRecipeBenefits(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 8.dp),
                shape = CutCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,

                    ),
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontFamily = quicksand)
            )



                Spacer(modifier = Modifier.height(8.dp))

                // Submit Button
                Button(
                    onClick = {
                        routine.launch {
                            recipemodel.setLoadingState(true)
                            recipemodel.handlerecipeintent(RecipeIntent.SubmitRecipe(recipestate))
                            navController.navigate(Navdestination.home.toString()){
                                popUpTo(Navdestination.home.toString()) { inclusive = true}
                            }
                        }


                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = CutCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(CustomBackgroundColor),
                    enabled = isFormValid
                ) {
                    Text("Submit Recipe")
                }
            }
        }
    }



