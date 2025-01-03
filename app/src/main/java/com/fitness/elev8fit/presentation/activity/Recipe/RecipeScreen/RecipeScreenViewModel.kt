package com.fitness.elev8fit.presentation.activity.Recipe.RecipeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.elev8fit.domain.model.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeScreenViewModel @Inject constructor(
    private val firestore: FirebaseFirestore

) :ViewModel(){


    private val _state = MutableStateFlow<List<Recipe>>(emptyList()) // StateFlow to hold the list of recipes
    val state: StateFlow<List<Recipe>> = _state


    fun fetchRecipes() {
        viewModelScope.launch {
            firestore.collection("Recipe")
                .get()
                .addOnSuccessListener { documents ->
                    val recipeList = documents.mapNotNull { document ->
                        Log.d("RecipeViewModel", "Successfully fetched documents: ${documents.size()} items")

                        document.toObject(Recipe::class.java)
                    // Automatically maps Firestore fields to Recipe class
                    }

                    _state.value = recipeList
                }
                .addOnFailureListener { exception ->
                    // Handle errors, e.g., log or update a UI state for errors
                    Log.e("RecipeViewModel", "Error fetching recipes: ${exception.message}")
                }
        }

        }
    }
//
//
//
//@HiltViewModel
//class RecipeScreenViewModel @Inject constructor(
//    private val firestore: FirebaseFirestore
//) : ViewModel() {
//
//    private val _state = MutableStateFlow<List<Recipe>>(emptyList()) // StateFlow to hold the list of recipes
//    val state: StateFlow<List<Recipe>> = _state
//
//    fun fetchRecipes() {
//        viewModelScope.launch {
//            firestore.collection("Recipe")
//                .get()
//                .addOnSuccessListener { documents ->
//                    Log.d("RecipeViewModel", "Successfully fetched ${documents.size()} documents")
//
//                    val recipeList = documents.mapNotNull { document ->
//                        try {
//                            Log.d("RecipeViewModel", "Document Data: ${document.data}")
//                            document.toObject(Recipe::class.java)
//                        } catch (e: Exception) {
//                            Log.e("MappingError", "Error mapping document: ${document.id}, ${e.message}")
//                            null
//                        }
//                    }
//
//                    if (recipeList.isNotEmpty()) {
//                        Log.d("RecipeViewModel", "Recipes fetched successfully: $recipeList")
//                        _state.value = recipeList
//                    } else {
//                        Log.w("RecipeViewModel", "No recipes found in the database.")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.e("RecipeViewModel", "Error fetching recipes: ${exception.message}")
//                }
//        }
//    }
//}
