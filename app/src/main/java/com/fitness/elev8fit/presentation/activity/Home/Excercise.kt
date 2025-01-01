package com.fitness.elev8fit.presentation.activity.Home

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fitness.elev8fit.R


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
@Composable
fun ExerciseCard(
    name: String = "Exercise Name",
    target: String = "Target Muscle",
    imageRes: Int = R.drawable.boy // Replace with an actual resource ID
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
            // Image on the left
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Exercise Image",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp)
            )
            // Column with text aligned to the end
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = target,
                    style = MaterialTheme.typography.bodySmall
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

@Preview(showBackground = true)
@Composable
fun ExerciseCardPreview() {
    ExerciseCard(name = "Push-Ups", target = "Chest")
}
