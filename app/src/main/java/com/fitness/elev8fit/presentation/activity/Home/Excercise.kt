package com.fitness.elev8fit.presentation.activity.Home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.fitness.elev8fit.domain.model.exercise.Exercise


@Composable
fun Excercise(){

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(
    title: String = "Sample Recipe",
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .height(70.dp)
            .width(110.dp),
        shape = RoundedCornerShape(3.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(Color.LightGray)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = title, style = MaterialTheme.typography.bodyMedium)
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
        colors = CardDefaults.cardColors(Color.LightGray)
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
            model = exercise.gifUrl,
            contentDescription = "Exercise GIF",
                modifier = Modifier.size(60.dp).border( 1.dp,color = Color.Gray,shape = RoundedCornerShape(2.dp))
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
                    textAlign = TextAlign.End
                )
                Text(
                    text = exercise.target.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodySmall,


                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    RecipeCard(title = "Pasta Recipe")
}

//@Preview(showBackground = true)
//@Composable
//fun ExerciseCardPreview() {
//    ExerciseCard(name = Exercis, target = "Chest")
//}
