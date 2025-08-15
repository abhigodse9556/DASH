package com.project.mypersonalassistant.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.project.mypersonalassistant.roomDB.AppDatabase
import com.project.mypersonalassistant.roomDB.entity.User
import com.project.mypersonalassistant.roomDB.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getInstance(application).userDao()
    private val repository = UserRepository(userDao)
    private val _question = MutableStateFlow("")
    val question: StateFlow<String> get() = _question

    private val _resetStatus = MutableStateFlow("")
    val resetStatus: StateFlow<String> get() = _resetStatus
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

    fun fetchQuestion(username: String) {
        _resetStatus.value = ""
        viewModelScope.launch {
            val qa = repository.getSecurityQA(username)
            if (qa != null) {
                _question.value = qa.question
                _resetStatus.value = "user_found"
            } else {
                _resetStatus.value = "not_found"
            }
        }
    }

    fun verifyAnswer(username: String, enteredAnswer: String) {
        _resetStatus.value = ""
        viewModelScope.launch {
            val qa = repository.getSecurityQA(username)
            if (qa != null && qa.answer.equals(enteredAnswer, ignoreCase = true)) {
                _resetStatus.value = "verified"
            } else {
                _resetStatus.value = "error"
            }
        }
    }

    fun updatePassword(username: String, newPassword: String) {
        _resetStatus.value = ""
        viewModelScope.launch {
            repository.updatePassword(username, newPassword)
            _resetStatus.value = "success"
        }
    }
}