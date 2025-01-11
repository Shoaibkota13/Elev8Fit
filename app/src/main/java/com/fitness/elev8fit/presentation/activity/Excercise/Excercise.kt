package com.fitness.elev8fit.presentation.activity.Excercise

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.fitness.elev8fit.domain.model.exercise.Exercise
import com.fitness.elev8fit.presentation.activity.Recipe.RecipeScreen.RecipeCard
import com.fitness.elev8fit.presentation.activity.Recipe.RecipeScreen.RecipeScreenViewModel
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.google.firebase.auth.FirebaseAuth
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay



@Composable
fun Excercise(exerciseViewModel: ExerciseViewModel,recipeScreenViewModel: RecipeScreenViewModel, navController: NavController) {
    val state by exerciseViewModel.state.collectAsState()
    val recipe by recipeScreenViewModel.state.collectAsState()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    val coachId = "oriw3fgPs4NqpN5BxfDb58Uq6532"
    val context = LocalContext.current

    LaunchedEffect(currentUserId) {

    }

    LaunchedEffect(true) {
        recipeScreenViewModel.fetchRecipes(navController)
    }
//    LaunchedEffect(Unit) {
//        if (state.exerciseList.isEmpty()) {
//
//            exerciseViewModel.handleAPIintent(ExerciseIntent.Loadexcercises(0, 30))
//
//        }
//    }
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
                        ExerciseCard(exercise = exercise,navController)
                    }
                }
            }
        }


        FloatingActionButton(
            onClick = {
                 navController.navigate(Navdestination.chat.toString()) },
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
    exercise: Exercise,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("exercise_detail/${exercise.id}")
            },
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



@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ExerciseDetailScreen(
    exerciseId: String,
    navController: NavController,
    viewModel: ExerciseViewModel
) {
    val showfull = remember {
        mutableStateOf(false)
    }
    val state by viewModel.state.collectAsState()
    val exercise = state.exerciseList.find { it.id == exerciseId }
    var timerRunning by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableStateOf(60) } // 3 minutes in second // s
    val scrollState = rememberScrollState()
    LaunchedEffect(timerRunning) {
        if (timerRunning) {
            while (remainingTime > 0 && timerRunning) {
                delay(1000L)
                remainingTime -= 1
            }
            if (remainingTime <= 0) {
                timerRunning = false // Stop the timer when time is up
            }
        }
    }
//    LaunchedEffect(Unit) {
//        if (state.exerciseList.isEmpty()) {
//            viewModel.handleAPIintent(ExerciseIntent.Loadexcercises(0, 30))
//        }
//    }

    // Log when the screen is opened
    LaunchedEffect(Unit) {
        Log.i("ExerciseDetailScreen", "Screen opened with exerciseId: $exerciseId")
        Log.d("ExerciseDetailScreen", "State: ${state.exerciseList}")
    }

    exercise?.let {

        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                // Back button
                IconButton(
                    onClick = {
                        Log.d("ExerciseDetailScreen", "Back button clicked")
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                // Exercise details
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    GlideImage(
                        model = exercise.gifUrl,
                        contentDescription = "Exercise GIF",
                        modifier = Modifier
                            .height(200.dp)
                            .width(350.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = it.name.replaceFirstChar { char -> char.uppercase() },
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Target: ${it.target}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Instructions:",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                    val instruction = if(showfull.value)it.instructions else it.instructions.take(3)
                    instruction.forEachIndexed{ index, step->
                        Text(text = "${index+1} $step", color = MaterialTheme.colorScheme.primary)
                    }

                if(it.instructions.size>1){
                    TextButton(onClick = {showfull.value =! showfull.value}) {

                        Text(text = if(showfull.value) "Read less" else "Read more")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Occupies the full width
                        .padding(vertical = 16.dp), // Optional vertical padding
                    contentAlignment = Alignment.Center // Centers the content horizontally
                ) {
                    if (timerRunning) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Remaining Time: ${remainingTime / 60}:${
                                    (remainingTime % 60).toString().padStart(2, '0')
                                }",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = {
                                timerRunning = false
                                remainingTime = 60 // Reset timer
                            }) {
                                Text("Stop")
                            }
                        }
                    } else {
                        Button(onClick = {
                            timerRunning = true
                            remainingTime = 60 // Reset timer
                        }) {
                            Text("Start Timer")
                        }
                    }
                }

            }
        }
            } ?: run {
        // Log if no exercise was found
        Log.d("ExerciseDetailScreen", "No exercise found with id: $exerciseId")
    }
}



