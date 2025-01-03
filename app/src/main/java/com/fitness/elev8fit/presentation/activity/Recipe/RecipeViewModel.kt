package com.fitness.elev8fit.presentation.activity.Recipe

import androidx.lifecycle.ViewModel
import com.fitness.elev8fit.data.repository.authfirebaseimpl
import com.fitness.elev8fit.data.state.RecipeState
import com.fitness.elev8fit.domain.model.Recipe
import com.fitness.elev8fit.presentation.intent.RecipeIntent
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val authfirebaseimpl: authfirebaseimpl,
    private val auth: FirebaseAuth
) :ViewModel() {
    private val _state = MutableStateFlow(RecipeState())
    val state: StateFlow<RecipeState> = _state
    fun updateRecipeTitle(newTitle: String) {
        _state.value = _state.value.copy(recipetitle = newTitle)
    }

    // Function to update recipe ingredients
    fun updateRecipeIngredients(newIngredients: List<String>) {
        _state.value = _state.value.copy(recipeIngredient = newIngredients)
    }

    // Function to update recipe image
    fun updateRecipeImage(newImage: Int) {
        _state.value = _state.value.copy(image = newImage)
    }

    // Function to update recipe instructions
    fun updateRecipeInstructions(newInstructions: List<String>) {
        _state.value = _state.value.copy(instruction = newInstructions)
    }

    // Function to update recipe benefits
    fun updateRecipeBenefits(newBenefits: String) {
        _state.value = _state.value.copy(benfits = newBenefits)
    }

    // Function to update preparation time
    fun updatePrepTime(newPrepTime: String) {
        _state.value = _state.value.copy(prepTime = newPrepTime)
    }

    // Function to update recipe category
    fun updateCategory(newCategory: String) {
        _state.value = _state.value.copy(category = newCategory)
    }

    // Function to update loading state
    fun setLoadingState(isLoading: Boolean) {
        _state.value = _state.value.copy(isLoading = isLoading)
    }

    // Function to set success message
    fun setSuccessMessage(message: String) {
        _state.value = _state.value.copy(successMessage = message)
    }

    // Function to set error message
    fun setErrorMessage(message: String) {
        _state.value = _state.value.copy(errorMessage = message)
    }
    
 suspend fun handlerecipeintent(intent :RecipeIntent){
        when(intent){
            is RecipeIntent.SubmitRecipe -> {
                Recipeupdate(intent.recipeState)
            }
        }
    }

     suspend fun Recipeupdate(recipeState: RecipeState) {
        val recipeInfo = Recipe(
            id = recipeState.id,
            image = recipeState.image,
            RecipeTitle = recipeState.recipetitle,
            recipeIngredient = recipeState.recipeIngredient,
            instructions = recipeState.instruction,
            prepTime = recipeState.prepTime.toInt(),
            benifits = recipeState.benfits
        )

         authfirebaseimpl.RecipeDb(this,recipeInfo)



    }

    fun saved() {
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "Saved in Firebase",
            errorMessage = null
        )
        auth.signOut()
    }
}