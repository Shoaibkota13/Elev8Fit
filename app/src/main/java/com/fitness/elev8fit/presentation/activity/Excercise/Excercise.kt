package com.fitness.elev8fit.presentation.activity.Excercise

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.fitness.elev8fit.domain.model.exercise.Exercise
import com.fitness.elev8fit.presentation.activity.Recipe.RecipeScreen.RecipeCard
import com.fitness.elev8fit.presentation.activity.Recipe.RecipeScreen.RecipeScreenViewModel
import com.fitness.elev8fit.presentation.intent.ExerciseIntent
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.google.firebase.auth.FirebaseAuth
import com.valentinilk.shimmer.shimmer


@Composable
fun Excercise(exerciseViewModel: ExerciseViewModel,recipeScreenViewModel: RecipeScreenViewModel, navController: NavController) {
    val state by exerciseViewModel.state.collectAsState()
    val recipe by recipeScreenViewModel.state.collectAsState()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    val coachId = "oriw3fgPs4NqpN5BxfDb58Uq6532"

    LaunchedEffect(currentUserId) {

    }

    LaunchedEffect(true) {
        recipeScreenViewModel.fetchRecipes(navController)
    }
    LaunchedEffect(Unit) {
        if (state.exerciseList.isEmpty()) {

            exerciseViewModel.handleAPIintent(ExerciseIntent.Loadexcercises(0, 15))

        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize() // Ensure the Column takes the full screen space
                .padding(16.dp)
        ) {

            LazyRow() {
                items(recipe) {
                    RecipeCard(it, navController = navController)
                }
            }

            if (state.isLoading) {
                LazyColumn() {
                    items(10) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(8.dp)
                                .background(Color.LightGray)
                                .shimmer()
                        )
                    }
                }
            } else if (state.errorMessage != null) {
                Text(text = state.errorMessage!!)
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(state.exerciseList) { exercise ->
                        ExerciseCard(exercise = exercise)
                    }
                }
            }
        }


        FloatingActionButton(
            onClick = { navController.navigate(Navdestination.chat.toString()) },
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


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ExerciseCard(
    exercise: Exercise
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
            model = exercise.gifUrl,
            contentDescription = "Exercise GIF",
                modifier = Modifier
                    .size(60.dp)
                    .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(2.dp))
        )
//            GlideImage(model = exercise.gifUrl, contentDescription = "Ex")
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = exercise.name.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = exercise.target.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary


                )
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun RecipeCardPreview() {
//    RecipeCard(title = "Pasta Recipe")
//}

//@Preview(showBackground = true)
//@Composable
//fun ExerciseCardPreview() {
//    ExerciseCard(name = Exercis, target = "Chest")
//}
