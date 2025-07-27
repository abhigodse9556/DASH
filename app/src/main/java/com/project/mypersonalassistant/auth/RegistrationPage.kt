package com.project.mypersonalassistant.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.project.mypersonalassistant.components.CustomButton
import com.project.mypersonalassistant.components.CustomTextField
import com.project.mypersonalassistant.components.showToast
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityQuestionDropdown(
    selectedQuestion: String,
    onQuestionSelected: (String) -> Unit
) {
    val questions = listOf(
        "What is your mother's maiden name?",
        "What was your first pet's name?",
        "What is your favorite teacher's name?",
        "What city were you born in?",
        "What is your favorite food?"
    )

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = selectedQuestion,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Security Question") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = TextFieldDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            questions.forEach { question ->
                DropdownMenuItem(
                    text = { Text(question) },
                    onClick = {
                        onQuestionSelected(question)
                        expanded = false
                    }
                )
            }
        }
    }
}



@Composable
fun RegistrationPage( onLoginClick: () -> Unit) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedQuestion by remember { mutableStateOf("") }
    var securityAnswer by remember { mutableStateOf("") }

    fun cancelRegister() {
        user = ""
        password = ""
    }

    fun disableBtn() = user.isBlank() || password.isBlank()

    val context = LocalContext.current

    fun handleRegister() {
        if (user == "Abhi" && password == "1234") {
            showToast(context, "success", "Thanks $user!")
            onLoginClick()
        } else {
            showToast(context, "error","Incorrect Credentials!", Toast.LENGTH_LONG)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(text = "Register", style = MaterialTheme.typography.headlineMedium)

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

        SecurityQuestionDropdown(
            selectedQuestion = selectedQuestion,
            onQuestionSelected = { selectedQuestion = it }
        )

        Spacer(modifier = Modifier.height(10.dp))

        CustomTextField(
            value = securityAnswer,
            onValueChange = { securityAnswer = it },
            label = "Your Answer",
            enabled = selectedQuestion != ""
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CustomButton(
                text = "Cancel",
                onClick = {
                    cancelRegister()
                },
                modifier = Modifier.weight(1f),
                enabled = !disableBtn(),
            )

            Spacer(modifier = Modifier.width(10.dp))

            CustomButton(
                text = "Register",
                onClick = {
                    handleRegister()
                },
                modifier = Modifier.weight(1f),
                enabled = !disableBtn(),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(onClick = onLoginClick) {
            Text("Already have an account? Login")
        }
    }
}