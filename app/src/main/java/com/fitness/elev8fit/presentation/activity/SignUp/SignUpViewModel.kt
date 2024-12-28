package com.fitness.elev8fit.presentation.activity.SignUp

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fitness.elev8fit.data.login.SignUpState
import com.fitness.elev8fit.domain.Firebase.FirebaseClass
import com.fitness.elev8fit.domain.model.User
import com.fitness.elev8fit.presentation.intent.SignUpIntent
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignUpViewModel:ViewModel() {


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state
    private var userAge: Int? = null
    private var userName: String? = null
    fun setAge(age: Int) {
        userAge = age
    }

    fun setUsername(username: String) {
        userName = username
    }

    fun email(newusername :String){
        _state.value = _state.value.copy(email =  newusername)
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
    fun checkAuth(username: String, password: String,navController: NavController,phonenumber:String) {
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

        if (username.isBlank() || password.isBlank()) {
            _state.value = _state.value.copy(
                errorMessage = "Username and password cannot be empty",
                isLoading = false,
               successMessage = null
            )
            return
        }
        createUser(username,password,navController, phonenumber)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun createUser(username: String, password: String, navController: NavController,phonenumber:String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val task = auth.createUserWithEmailAndPassword(username, password)
                    task.await()
                if (task.isSuccessful) {
                    val firebaseuser :FirebaseUser = task.result!!.user!!
                    val email = firebaseuser.email!!
                    val user = User(firebaseuser.uid,username,email,phonenumber)
                    //passing the activity

                    FirebaseClass().registerUser(this@SignUpViewModel,user)
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





