package com.fitness.elev8fit.domain.Firebase

import com.fitness.elev8fit.data.constant.Constants
import com.fitness.elev8fit.domain.model.Recipe
import com.fitness.elev8fit.domain.model.User
import com.fitness.elev8fit.presentation.activity.Recipe.RecipeViewModel
import com.fitness.elev8fit.presentation.activity.SignUp.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirebaseClass() {

    private val mfirestore = FirebaseFirestore.getInstance()

    fun registerUser(signinactivity: SignUpViewModel, userInfo: User) {
        mfirestore.collection(Constants.user)
            .document(getcurrentuser()).set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                signinactivity.onregistersucess()
            }
    }

    //to get the current user
    fun getcurrentuser(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun RecipeDb(Recipe: RecipeViewModel, recipeInfo:Recipe){
        mfirestore.collection(Constants.Recipe).document(getcurrentuser()).set(recipeInfo,
            SetOptions.merge()).addOnSuccessListener {
                Recipe.saved()
        }

    }

}