package com.fitness.elev8fit.presentation.activity.Home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.fitness.elev8fit.domain.model.exercise.Exercise
import com.fitness.elev8fit.presentation.intent.ExerciseIntent
import com.fitness.elev8fit.presentation.viewmodel.ExerciseViewModel



@Composable
fun ExerciseScreen(viewModel: ExerciseViewModel) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleAPIintent(ExerciseIntent.Loadexcercises(0, 15))
    }

    if (state.isLoading) {
        Text(text = "Loading...")
    } else if (state.errorMessage != null) {
        Text(text = state.errorMessage!!)
    } else {
        LazyColumn {
            items(state.exerciseList) { exercise ->
                ExerciseCard(exercise = exercise)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ExerciseItem(exercise: Exercise) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Name: ${exercise.name}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Exercise ID: ${exercise.id}")
        Text(text = "Instructions:")
        exercise.instructions.forEach { instruction ->
            Text(text = "- $instruction")
        }
        Text(text = "Target Muscles: ${exercise.secondaryMuscles.joinToString()}")
//        Text(text = "Body Parts: ${exercise.bodyParts.joinToString()}")
//        Text(text = "Equipments: ${exercise.equipments.joinToString()}")
        Text(text = "Secondary Muscles: ${exercise.secondaryMuscles.joinToString()}")
        Spacer(modifier = Modifier.height(8.dp))
//        AsyncImage(
//            model = exercise.gifUrl,
//            contentDescription = "Exercise GIF",
//            modifier = Modifier.fillMaxWidth()
//        )
//        Glide.with(context)
//            .load(exercise.gifUrl)

        GlideImage(model = exercise.gifUrl, contentDescription = "Ex")
    }
}
