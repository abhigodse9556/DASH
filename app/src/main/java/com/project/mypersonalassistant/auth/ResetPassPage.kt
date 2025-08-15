package com.project.mypersonalassistant.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.mypersonalassistant.components.CustomButton
import com.project.mypersonalassistant.components.CustomTextField
import com.project.mypersonalassistant.components.showToast
import com.project.mypersonalassistant.navigation.auth.AuthRoutes
import com.project.mypersonalassistant.viewModel.AuthViewModel

@Composable
fun ResetPasswordPage(navigateTo: (AuthRoutes) -> Unit) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()

    var currentStep by remember { mutableStateOf(1) }
    var username by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    val question by authViewModel.question.collectAsState()
    val status by authViewModel.resetStatus.collectAsState()

    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    @Composable
    fun ActionButtons(btnTxt: String, action: () -> Unit) {
        Row(
            modifier = Modifier
            .padding(10.dp)
        ) {
            CustomButton(
                text = "Cancel",
                onClick = { navigateTo(AuthRoutes.Login) },
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomButton(
                text = btnTxt,
                onClick = action,
                modifier = Modifier.weight(1f)
            )
        }
    }

    // Handle navigation when reset is successful
    LaunchedEffect(status) {
        when (status) {
            "user_found" -> currentStep = 2
            "verified" -> currentStep = 3
            "success" -> {
                showToast(context, "success", "Password updated successfully!")
                navigateTo(AuthRoutes.Login)
            }
            "error" -> {
                showError = true
                errorMessage = "Incorrect answer!"
            }
            "not_found" -> {
                showError = true
                errorMessage = "Incorrect username or email id!"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .padding(5.dp)
                .background(Color.Yellow)
                .border(
                    width = 2.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(16.dp) // border radius
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (currentStep) {
                1 -> {
                    CustomTextField(
                        value = username,
                        onValueChange = {
                            username = it
                            showError = false
                            errorMessage = ""
                        },
                        label = "Username or Email",
                        isError = showError,
                        supportingText = {
                            if (showError) Text(errorMessage, color = Color.Red)
                        },
                    )
                    ActionButtons("Next") {
                        if (username.isBlank()) {
                            showError = true
                            errorMessage = "Please enter your username or email"
                        } else {
                            authViewModel.fetchQuestion(username)
                        }
                    }
                }

                2 -> {
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Security Question:")
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "$question", color = Color.Blue, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    CustomTextField(
                        value = answer,
                        onValueChange = {
                            answer = it
                            showError = false
                            errorMessage = ""
                        },
                        label = "Answer",
                        isError = showError,
                        supportingText = {
                            if (showError) Text(errorMessage, color = Color.Red)
                        },
                    )
                    ActionButtons("Verify Answer") {
                        if (answer.isBlank()) {
                            showError = true
                            errorMessage = "Please enter your answer"
                        } else {
                            authViewModel.verifyAnswer(username, answer)
                        }
                    }
                }

                3 -> {
                    CustomTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = "New Password",
                    )
                    ActionButtons("Reset Password") {
                        if (newPassword.isBlank()) {
                            showError = true
                            errorMessage = "Password cannot be empty"
                        } else {
                            authViewModel.updatePassword(username, newPassword)
                        }
                    }
                }
            }
        }

    }
}


