
package com.fitness.elev8fit.presentation.activity.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fitness.elev8fit.domain.model.BottomMenuItems
import com.fitness.elev8fit.presentation.activity.Excercise.Excercise
import com.fitness.elev8fit.presentation.activity.Profile.ProfileScreen
import com.fitness.elev8fit.presentation.activity.Recipe.RecipeScreen.RecipeScreen




@Composable
fun HomePage(navController: NavController) {
    val bottomMenuItems = preparebottom()
    var selected by remember { mutableStateOf("Home") }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        // Display content based on the selected item
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp), // Adjust for NavigationBar height
            contentAlignment = Alignment.Center
        ) {
            when (selected) {
                "Home" -> Excercise(exerciseViewModel = hiltViewModel(), recipeScreenViewModel = hiltViewModel(),navController)
                "Recipe" -> RecipeScreen(recipeScreenViewModel = hiltViewModel(),navController)
                "Account" -> ProfileScreen(profileViewModel = hiltViewModel(), navController, common = hiltViewModel(),context)

            }
        }

        // Bottom navigation bar
        NavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            containerColor = MaterialTheme.colorScheme.secondary
        ) {
            bottomMenuItems.forEach { menuItem ->
                NavigationBarItem(
                    selected = selected == menuItem.label,
                    onClick = { selected = menuItem.label },
                    icon = {
                        Icon(
                            imageVector = menuItem.icon,
                            contentDescription = menuItem.label
                        )
                    },
                    label = { Text(menuItem.label) },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Color.Black,
                        selectedTextColor =MaterialTheme.colorScheme.primary,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        }
    }
}

private fun preparebottom(): List<BottomMenuItems> {
    return listOf(
        BottomMenuItems("Home", Icons.Filled.Home),
        BottomMenuItems("Recipe", Icons.Filled.Add),
        BottomMenuItems("Account", Icons.Filled.AccountCircle)
    )
}

@Preview(showBackground = true)
@Composable
fun HomepagePreview() {
    val navController = rememberNavController()
    HomePage(navController)
}
