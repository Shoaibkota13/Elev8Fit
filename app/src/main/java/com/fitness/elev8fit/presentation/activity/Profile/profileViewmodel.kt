package com.fitness.elev8fit.presentation.activity.Profile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.elev8fit.data.repository.authfirebaseimpl
import com.fitness.elev8fit.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
  private val authRepo :authfirebaseimpl,
  private val firestore: FirebaseFirestore,
  private val firebaseAuth: FirebaseAuth

) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    fun fetchCurrentUser() {
        viewModelScope.launch {
            authRepo.fetchCurrentUser { fetchedUser ->
                _user.postValue(fetchedUser)
            }
        }
    }

    fun updateSingleField(field: String, value: String) {
        viewModelScope.launch {
            authRepo.updateSingleField(field, value)  // Update the field in Firestore
            fetchCurrentUser()

        }

    }
}
