package com.fitness.elev8fit.presentation.activity.Recipe

import androidx.lifecycle.ViewModel
import com.fitness.elev8fit.data.repository.authfirebaseimpl
import com.fitness.elev8fit.data.state.RecipeState
import com.fitness.elev8fit.domain.model.Recipe
import com.fitness.elev8fit.presentation.intent.RecipeIntent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val authfirebaseimpl: authfirebaseimpl,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) :ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> get() = _recipes


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

    }
    }


//package com.fitness.elev8fit.presentation.activity.Recipe
//
//import android.content.Context
//import android.net.Uri
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import com.amazonaws.auth.BasicAWSCredentials
//import com.amazonaws.services.s3.AmazonS3Client
//import com.amazonaws.services.s3.model.ObjectMetadata
//import com.amazonaws.services.s3.model.PutObjectRequest
//import com.fitness.elev8fit.data.repository.authfirebaseimpl
//import com.fitness.elev8fit.data.state.RecipeState
//import com.fitness.elev8fit.domain.model.Recipe
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import java.io.File
//import java.util.UUID
//import javax.inject.Inject
//
//@HiltViewModel
//class RecipeViewModel @Inject constructor(
//    private val authfirebaseimpl: authfirebaseimpl
//) : ViewModel() {
//
//    private val _state = MutableStateFlow(RecipeState())
//    val state: StateFlow<RecipeState> = _state
//
//
//
////    // Function to update recipe ingredients
//    fun updateRecipeIngredients(newIngredients: List<String>) {
//        _state.value = _state.value.copy(recipeIngredient = newIngredients)
//    }
////
////    // Function to update recipe image
//    fun updateRecipeImage(newImage: Int) {
//        _state.value = _state.value.copy(image = newImage)
//    }
////
////    // Function to update recipe instructions
//    fun updateRecipeInstructions(newInstructions: List<String>) {
//        _state.value = _state.value.copy(instruction = newInstructions)
//    }
////
////    // Function to update recipe benefits
//    fun updateRecipeBenefits(newBenefits: String) {
//        _state.value = _state.value.copy(benfits = newBenefits)
//    }
////
////    // Function to update preparation time
//    fun updatePrepTime(newPrepTime: String) {
//        _state.value = _state.value.copy(prepTime = newPrepTime)
//    }
////
////    // Function to update recipe category
//    fun updateCategory(newCategory: String) {
//        _state.value = _state.value.copy(category = newCategory)
//    }
////
////    // Function to update loading state
//    fun setLoadingState(isLoading: Boolean) {
//        _state.value = _state.value.copy(isLoading = isLoading)
//    }
////
////    // Function to set success message
//    fun setSuccessMessage(message: String) {
//        _state.value = _state.value.copy(successMessage = message)
//    }
////
////    // Function to set error message
//    fun setErrorMessage(message: String) {
//        _state.value = _state.value.copy(errorMessage = message)
//    }
////
////    suspend fun handlerecipeintent(intent: RecipeIntent) {
////        when (intent) {
////            is RecipeIntent.SubmitRecipe -> {
////                Recipeupdate(intent.recipeState)
////            }
////        }
////    }
//    // Function to upload image to S3
//    fun uploadImageToS3WithAccessKeys(
//        imageUri: Uri,
//        context: Context,
//        onUploadComplete: (String) -> Unit
//    ) {
//        try {
//            val imageFile = getFileFromUri(imageUri,context) // Helper function for file conversion
//            val s3Client = AmazonS3Client(credentials)
//
//            val bucketName = "your-bucket-name"
//            val fileName = "recipes/${UUID.randomUUID()}.jpg"
//            val objectKey = fileName
//
//            val metadata = ObjectMetadata().apply { contentType = "image/jpeg" }
//
//            val request = PutObjectRequest(bucketName, objectKey, imageFile).apply {
//                this.metadata = metadata
//            }
//
//            s3Client.putObject(request)
//
//            val imageUrl = "https://$bucketName.s3.amazonaws.com/$objectKey"
//            onUploadComplete(imageUrl)
//        } catch (e: Exception) {
//            Log.e("S3 Upload", "Error uploading image: ${e.message}")
//        }
//    }
//
//    private fun getFileFromUri(uri: Uri,context: Context): File {
//        val inputStream = context.contentResolver.openInputStream(uri)!!
//        val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
//        tempFile.outputStream().use { outputStream ->
//            inputStream.copyTo(outputStream)
//        }
//        return tempFile
//    }
//
//    suspend fun saveRecipeWithImage(imageUrl: String) {
//        val updatedRecipe = Recipe(
//            id = _state.value.id,
//            image = imageUrl, // Store the image URL as a string
//            RecipeTitle = _state.value.recipetitle,
//            recipeIngredient = _state.value.recipeIngredient,
//            instructions = _state.value.instruction,
//            prepTime = _state.value.prepTime.toInt(),
//            benifits = _state.value.benfits
//        )
//
//        try {
//            authfirebaseimpl.saveRecipeWithImage(updatedRecipe)
//            Log.d("Firebase", "Recipe saved successfully!")
//        } catch (exception: Exception) {
//            Log.e("Firebase", "Error saving recipe: ${exception.message}")
//        }
//    }
//}
