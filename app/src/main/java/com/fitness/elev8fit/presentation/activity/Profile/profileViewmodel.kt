package com.fitness.elev8fit.presentation.activity.Profile
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.elev8fit.data.repository.authfirebaseimpl
import com.fitness.elev8fit.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
  private val authRepo :authfirebaseimpl
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
}
