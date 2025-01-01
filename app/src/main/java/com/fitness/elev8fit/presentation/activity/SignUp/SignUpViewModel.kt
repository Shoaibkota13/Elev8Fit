package com.fitness.elev8fit.presentation.activity.SignUp

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fitness.elev8fit.data.state.SignUpState
import com.fitness.elev8fit.domain.Firebase.FirebaseClass
import com.fitness.elev8fit.domain.model.User
import com.fitness.elev8fit.presentation.intent.SignUpIntent
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth :FirebaseAuth,
    private val firebaseClass: FirebaseClass

) :ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state
    fun setAge(newage: String) {
      _state.value = _state.value.copy( age = newage)
    }

    fun setUsername(username: String) {
        _state.value = _state.value.copy(name = username)
    }

    fun email(newemail :String){
        _state.value = _state.value.copy(email =  newemail)
    }
    fun password(password :String){
        _state.value = _state.value.copy(password = password)
    }

    fun confirmPassword(newPassword: String) {
        _state.value = _state.value.copy(Confirmpassword = newPassword)
    }
    fun phonenumber(phonenumber: String){
        _state.value = _state.value.copy(phonenumber = phonenumber)
    }

    fun SignUpIntentHandler(intent: SignUpIntent,navController: NavController) {

        when (intent) {
            is SignUpIntent.Signup -> checkAuth(intent.email, intent.password,navController,intent.phonenumber)
        }
    }
    fun checkAuth(email: String, password: String,navController: NavController,phonenumber:String) {
        _state.value = _state.value.copy(isLoading = true)
        val confirmPassword = _state.value.Confirmpassword
        if (password != confirmPassword) {
            _state.value = _state.value.copy(
                errorMessage = "Passwords do not match",
                isLoading = false,
                successMessage = null
            )
            return
        }

        if (email.isBlank() || password.isBlank()) {
            _state.value = _state.value.copy(
                errorMessage = "email and password cannot be empty",
                isLoading = false,
               successMessage = null
            )
            return
        }
        createUser(email,password,navController, phonenumber)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun createUser(email: String, password: String, navController: NavController,phonenumber:String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val task = auth.createUserWithEmailAndPassword(email, password)
                    task.await()
                if (task.isSuccessful) {
                    val firebaseuser :FirebaseUser = task.result!!.user!!
                    val email = firebaseuser.email!!
                    val user = User(firebaseuser.uid, name = _state.value.name,email,phonenumber, age = _state.value.age)
                    //passing the activity


                    firebaseClass.registerUser(this@SignUpViewModel,user)
                    navController.navigate(Navdestination.home.toString())
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = "User creation failed: No user returned",
                        successMessage = null
                    )
                }
            } catch (e: FirebaseAuthException) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Firebase Error: ${e.message}",
                    successMessage = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Unknown Error: ${e.message}",
                    successMessage = null
                )
            }
        }
    }



    fun clearSuccessMessage() {
        _state.value = _state.value.copy(successMessage = null)
    }
    fun clearErrorMessage() {
        _state.value = _state.value.copy(errorMessage = null)
    }

    fun onregistersucess() {
        _state.value = _state.value.copy(
            isLoading = false,
            successMessage = "User created successfully!",
            errorMessage = null
        )
        auth.signOut()

    }

    fun resetFields() {
        _state.value = _state.value.copy(
            email = "",
            password = "",
            Confirmpassword = "",
            phonenumber = "",

        )
    }


}





