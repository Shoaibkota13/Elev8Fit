package com.fitness.elev8fit.presentation.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fitness.elev8fit.data.constant.DataStoreManager
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class commonviewmodel @Inject constructor(
    private val auth: FirebaseAuth
):ViewModel() {
    fun logout(context: Context, navController: NavController) {
        viewModelScope.launch {
            DataStoreManager.saveAuthState(context, false)
            auth.signOut()
            navController.navigate(Navdestination.onboarding1.toString()) {
                popUpTo(Navdestination.home.toString()) { inclusive = true }
            }
        }
    }
}