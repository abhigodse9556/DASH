package com.project.mypersonalassistant.auth

import android.widget.Toast
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.project.mypersonalassistant.components.CustomButton
import com.project.mypersonalassistant.components.CustomTextField
import com.project.mypersonalassistant.components.showToast
import androidx.compose.material3.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.mypersonalassistant.navigation.auth.AuthRoutes
import com.project.mypersonalassistant.roomDB.entity.User
import com.project.mypersonalassistant.viewModel.AuthViewModel

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
        val containerColor = MaterialTheme.colorScheme.surface // or any color you prefer

        TextField(
            value = selectedQuestion,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Security Question") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor
            ),
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
fun RegistrationPage( navigateTo: (AuthRoutes) -> Unit) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedQuestion by remember { mutableStateOf("") }
    var securityAnswer by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    fun cancelRegister() {
        user = ""
        password = ""
    }

    fun disableBtn() = user.isBlank() || password.isBlank()

    fun isFormValid():Boolean {
        if (
            user == "" ||
            firstName == "" ||
            lastName == "" ||
            email == "" ||
            mobile == "" ||
            selectedQuestion == "" ||
            securityAnswer == "" ||
            password == ""
            ) {
            return false
        }
        return true
    }

    fun handleRegister() {
        if (!isFormValid()) {
            showToast(context, "error", "All fields are required!", Toast.LENGTH_LONG)
            return
        } else {
            val newUser = User(
                firstname = firstName,
                lastname = lastName,
                email = email,
                mobile = mobile,
                username = user,
                password = password,
                question = selectedQuestion,
                answer = securityAnswer
            )

            authViewModel.register(
                user = newUser,
                onSuccess = {
                    showToast(context, "success", "Registration successful!", Toast.LENGTH_LONG)
                    navigateTo(AuthRoutes.Login) // navigate to login
                },
                onFailure = { error ->
                    showToast(context, "error", error, Toast.LENGTH_LONG)
                }
            )
        }
    }

    fun handleEmailChange(mail: String) {
        email = mail
        user = if (mail.contains("@")) mail.substringBefore("@") else mail
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
                .zIndex(1000F)
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = 80.dp)
                .padding(horizontal = 10.dp)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(4.dp))

            Row {
                CustomTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = "First Name",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))
                CustomTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = "Last Name",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(
                value = email,
                onValueChange = { handleEmailChange(it) },
                label = "Email ID",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(
                value = mobile,
                onValueChange = { mobile = it },
                label = "Mobile No.",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

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

            Spacer(modifier = Modifier.height(10.dp))

            SecurityQuestionDropdown(
                selectedQuestion = selectedQuestion,
                onQuestionSelected = { selectedQuestion = it }
            )

            CustomTextField(
                value = securityAnswer,
                onValueChange = { securityAnswer = it },
                label = "Your Answer",
                enabled = selectedQuestion.isNotBlank()
            )

            Spacer(modifier = Modifier.height(150.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .shadow(8.dp, shape = RoundedCornerShape(12.dp))
                .background(Color.White, shape = RoundedCornerShape(18.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextButton(
                onClick = {
                    navigateTo(AuthRoutes.Login)
                }
            ) {
                Text("Already have an account? Login")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CustomButton(
                    text = "Cancel",
                    onClick = {
                        cancelRegister()
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !disableBtn()
                )
                Spacer(modifier = Modifier.width(10.dp))
                CustomButton(
                    text = "Register",
                    onClick = {
                        handleRegister()
                    },
                    modifier = Modifier.weight(1f),
                    enabled = user.isNotBlank() && password.isNotBlank()
                )
            }

        }
    }
}