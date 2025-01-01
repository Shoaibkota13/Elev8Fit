package com.fitness.elev8fit.presentation.intent

import com.fitness.elev8fit.data.state.RecipeState

sealed class RecipeIntent (){
    data class SubmitRecipe(val recipeState: RecipeState) : RecipeIntent()
}