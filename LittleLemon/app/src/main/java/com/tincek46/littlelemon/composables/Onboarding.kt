package com.tincek46.littlelemon.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.tincek46.littlelemon.R


@Composable
fun Onboarding() {
    // State for user input
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // Context for SharedPreferences
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Header with Logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .size(100.dp)
                .padding(top = 32.dp, bottom = 16.dp)
        )

        // Static Text
        Text(
            text = "Let's get to know you",
            style = MaterialTheme.typography.headlineSmall, // Changed from h4 to Material 3 equivalent
            modifier = Modifier
                .padding(bottom = 32.dp)
                .align(Alignment.Start)
        )

        // TextFields for user input
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("First name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        // Register Button
        Button(
            onClick = {
                // Validate inputs
                if (firstName.isNotBlank() && lastName.isNotBlank() && email.contains("@")) {
                    // Save user data using SharedPreferences
                    val sharedPreferences = context.getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)
                    sharedPreferences.edit {
                        putString("firstName", firstName)
                        putString("lastName", lastName)
                        putString("email", email)
                        // apply() is called automatically by the KTX extension
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Yellow, // Material 3 way to set background
                contentColor = Color.Black    // Material 3 way to set text color
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.labelLarge // Changed from button to Material 3 equivalent
            )
        }
    }
}

// Preview function
@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    MaterialTheme {
        Onboarding()
    }
}
