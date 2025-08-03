package com.project.mypersonalassistant.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.mypersonalassistant.R

import com.project.mypersonalassistant.components.CustomButton
import com.project.mypersonalassistant.components.CustomTextField
import com.project.mypersonalassistant.components.showToast
import com.project.mypersonalassistant.navigation.auth.AuthRoutes
import com.project.mypersonalassistant.viewModel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage( navigateTo: (AuthRoutes) -> Unit ) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    fun cancelLogin() {
        user = ""
        password = ""
    }

    fun disableBtn() = user.isBlank() || password.isBlank()

    fun handleLogin() {
        if (user == "" || password == "") {
            showToast(context, "error", "Missing Credentials!")
        } else {
            authViewModel.login(
                user,
                password,
                onSuccess = {
                    showToast(context, "success", "Login Successfull!")
                    navigateTo(AuthRoutes.Home)
                },
                onFailure = {
                    showToast(context, "error", "Invalid Credentials!")
                }
            )
        }
    }

    fun handleRegister() {
        showToast(context, "info", "Thanks!")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color.Yellow)
            .border(
                width = 2.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(16.dp) // border radius
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.poster_mini),
            contentDescription = "App Poster",
            modifier = Modifier
                .height(200.dp)
                .width(400.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            CustomTextField(
                value = user,
                onValueChange = { user = it },
                label = "User Name or Email Id",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                CustomButton(
                    text = "Cancel",
                    onClick = {
                        cancelLogin()
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !disableBtn(),
                )

                Spacer(modifier = Modifier.width(10.dp))

                CustomButton(
                    text = "Login",
                    onClick = {
                        handleLogin()
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !disableBtn(),
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(
            onClick = {
                navigateTo(AuthRoutes.Register)
            }
        ) {
            Text("Don't have an account? Register")
        }

    }
}
