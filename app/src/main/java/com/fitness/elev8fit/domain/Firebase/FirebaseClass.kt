package com.fitness.elev8fit.domain.Firebase

import com.fitness.elev8fit.data.constant.Constants
import com.fitness.elev8fit.domain.model.Recipe
import com.fitness.elev8fit.presentation.activity.Recipe.RecipeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClass @Inject constructor(
    private val auth :FirebaseAuth,
    private val firestore: FirebaseFirestore
) {



//    fun facebookregister(facebookActivity: FacebookSignInViewModel,userInfo: User){
//        firestore.collection(Constants.user)
//            .document(getcurrentuser())
//            .set(userInfo,SetOptions.merge())
//            .addOnSuccessListener {
//                facebookActivity.onsucess()
//            }
//    }

    //to get the current user
    fun getcurrentuser(): String {
        return auth.currentUser?.uid ?: throw IllegalStateException("No User Found")
    }

    fun RecipeDb(Recipe: RecipeViewModel, recipeInfo:Recipe){
        firestore.collection(Constants.Recipe).document(getcurrentuser()).set(recipeInfo,
            SetOptions.merge()).addOnSuccessListener {
                Recipe.saved()
        }

    }

}