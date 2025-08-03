package com.project.mypersonalassistant.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypersonalassistant.roomDB.AppDatabase
import com.project.mypersonalassistant.roomDB.entity.User
import com.project.mypersonalassistant.roomDB.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getInstance(application).userDao()
    private val repository = UserRepository(userDao)

    fun register(user: User, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            if (repository.isUserExists(user.username)) {
                onFailure("Username already exists")
            } else {
                repository.register(user)
                onSuccess()
            }
        }
    }

    fun login(username: String, password: String, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            val user = repository.login(username, password)
            if (user != null) {
                onSuccess(user)
            } else {
                onFailure("Invalid credentials")
            }
        }
    }
}