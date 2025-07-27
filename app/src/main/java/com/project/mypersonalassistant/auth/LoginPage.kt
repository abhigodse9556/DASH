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

import com.project.mypersonalassistant.components.CustomButton
import com.project.mypersonalassistant.components.CustomTextField
import com.project.mypersonalassistant.components.showToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage( onRegisterClick: () -> Unit) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    fun cancelLogin() {
        user = ""
        password = ""
    }

    fun disableBtn() = user.isBlank() || password.isBlank()
    val context = LocalContext.current

    fun handleLogin() {
        if (user == "Abhi" && password == "1234") {
            showToast(context, "success", "Hello $user!")
        } else {
            showToast(context, "error","Incorrect Credentials!", Toast.LENGTH_LONG)
        }
    }

    fun handleRegister() {
        showToast(context, "info", "Thanks!")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        CustomTextField(
            value = user,
            onValueChange = { user = it },
            label = "User Name",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
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

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(onClick = onRegisterClick) {
            Text("Don't have an account? Register")
        }

    }
}
