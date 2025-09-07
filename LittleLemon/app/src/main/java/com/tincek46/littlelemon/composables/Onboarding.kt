package com.tincek46.littlelemon.composables

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Onboarding(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var actionStatus by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), 
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        LittleLemonTopAppBar( 
            modifier = Modifier
                .padding(top = 0.dp, bottom = 24.dp) 
        )

        Text(
            text = "Let's get to know you",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .align(Alignment.Start)
        )

        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First name") },
            singleLine = true, 
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last name") },
            singleLine = true, 
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true, 
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        actionStatus?.let {
            Text(
                text = it,
                color = if (it.contains("successful")) Color.Green else Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = {
                actionStatus = null // Clear previous status
                val currentFirstName = firstName.trim()
                val currentLastName = lastName.trim()
                val currentEmail = email.trim()

                if (currentFirstName.isBlank() || currentLastName.isBlank() || currentEmail.isBlank()) {
                    actionStatus = "Registration unsuccessful. Please enter all data."
                } else {
                    val atIndex = currentEmail.indexOf('@')
                    val dotIndex = currentEmail.lastIndexOf('.')
                    val domainPart = if (atIndex != -1 && dotIndex > atIndex) currentEmail.substring(atIndex + 1, dotIndex) else ""
                    val tldPart = if (dotIndex != -1 && dotIndex < currentEmail.length -1) currentEmail.substring(dotIndex + 1) else ""

                    if (atIndex <= 0 || dotIndex <= atIndex + 1 || dotIndex >= currentEmail.length - 1 || domainPart.isBlank() || tldPart.isBlank()) {
                        actionStatus = "Invalid email format. Please enter a valid email."
                    } else {
                        val sharedPreferences = context.getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)
                        with(sharedPreferences.edit()) {
                            putString("firstName", currentFirstName)
                            putString("lastName", currentLastName)
                            putString("email", currentEmail)
                            putBoolean("isLoggedIn", true) 
                            apply()
                        }
                        actionStatus = "Registration successful!"
                        navController.navigate("home") {
                            popUpTo("onboarding") { inclusive = true } 
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow, contentColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                actionStatus = null // Clear previous status
                val currentEmail = email.trim()

                if (currentEmail.isBlank()) {
                    actionStatus = "Email address is required for login."
                    return@OutlinedButton
                }
                val atIndex = currentEmail.indexOf('@')
                val dotIndex = currentEmail.lastIndexOf('.')
                val domainPart = if (atIndex != -1 && dotIndex > atIndex) currentEmail.substring(atIndex + 1, dotIndex) else ""
                val tldPart = if (dotIndex != -1 && dotIndex < currentEmail.length -1) currentEmail.substring(dotIndex + 1) else ""

                if (atIndex <= 0 || dotIndex <= atIndex + 1 || dotIndex >= currentEmail.length - 1 || domainPart.isBlank() || tldPart.isBlank()) {
                    actionStatus = "Invalid email format for login."
                    return@OutlinedButton
                }

                val sharedPreferences = context.getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)
                val storedEmail = sharedPreferences.getString("email", null)
                val storedFirstName = sharedPreferences.getString("firstName", null)

                if (storedEmail != null && storedFirstName != null && storedEmail.equals(currentEmail, ignoreCase = true)) {
                     with(sharedPreferences.edit()) {
                        putBoolean("isLoggedIn", true) 
                        apply()
                    }
                    actionStatus = "Login successful!"
                    navController.navigate("home") {
                        popUpTo("onboarding") { inclusive = true } 
                    }
                } else {
                    actionStatus = "Login failed. Email not registered."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "Log In")
        }
    }
}
