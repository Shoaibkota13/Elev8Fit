package com.fitness.elev8fit.presentation.intent

import com.fitness.elev8fit.data.Recipe.RecipeState

sealed class RecipeIntent (){
    data class SubmitRecipe(val recipeState: RecipeState) : RecipeIntent()
}