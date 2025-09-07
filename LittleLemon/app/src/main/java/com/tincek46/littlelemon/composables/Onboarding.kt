package com.tincek46.littlelemon.composables

import android.content.Context
import android.util.Patterns // For email validation
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit // For SharedPreferences.edit extension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tincek46.littlelemon.R
import com.tincek46.littlelemon.ui.theme.LittleLemonTheme
// Import centralized SharedPreferences constants
import com.tincek46.littlelemon.PREFS_NAME
import com.tincek46.littlelemon.KEY_FIRST_NAME
import com.tincek46.littlelemon.KEY_LAST_NAME
import com.tincek46.littlelemon.KEY_EMAIL
import com.tincek46.littlelemon.KEY_IS_LOGGED_IN

// Email Validation Helper
private fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

// Registration Logic Helper
private fun performRegistration(
    context: Context,
    firstName: String,
    lastName: String,
    email: String,
    navController: NavController
): String {
    if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
        return context.getString(R.string.error_registration_incomplete)
    }
    if (!isValidEmail(email)) {
        return context.getString(R.string.error_invalid_email_format)
    }

    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    sharedPreferences.edit {
        putString(KEY_FIRST_NAME, firstName)
        putString(KEY_LAST_NAME, lastName)
        putString(KEY_EMAIL, email)
        putBoolean(KEY_IS_LOGGED_IN, true)
        // apply() is called automatically by androidx.core.content.edit KTX
    }
    // Consider using type-safe navigation route if Onboarding destination object has 'home' route property
    navController.navigate(com.tincek46.littlelemon.Home.route) { // Assuming Home.route is accessible
        popUpTo(com.tincek46.littlelemon.Onboarding.route) { inclusive = true } // Assuming Onboarding.route is accessible
    }
    return context.getString(R.string.success_registration)
}

// Login Logic Helper
private fun performLogin(
    context: Context,
    email: String,
    navController: NavController
): String {
    if (email.isBlank()) {
        return context.getString(R.string.error_email_required_login)
    }
    if (!isValidEmail(email)) {
        return context.getString(R.string.error_invalid_email_login)
    }

    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val storedEmail = sharedPreferences.getString(KEY_EMAIL, null)
    val storedFirstName = sharedPreferences.getString(KEY_FIRST_NAME, null) // Check if user was ever registered

    return if (storedEmail != null && storedFirstName != null && storedEmail.equals(email, ignoreCase = true)) {
        sharedPreferences.edit {
            putBoolean(KEY_IS_LOGGED_IN, true)
        }
        navController.navigate(com.tincek46.littlelemon.Home.route) { // Assuming Home.route is accessible
            popUpTo(com.tincek46.littlelemon.Onboarding.route) { inclusive = true } // Assuming Onboarding.route is accessible
        }
        context.getString(R.string.success_login)
    } else {
        context.getString(R.string.error_login_failed_not_registered)
    }
}

@Composable
fun Onboarding(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var actionStatus by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    val successRegistrationMsg = stringResource(R.string.success_registration)
    val successLoginMsg = stringResource(R.string.success_login)

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
            text = stringResource(R.string.onboarding_title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .align(Alignment.Start)
        )

        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text(stringResource(R.string.label_first_name)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(stringResource(R.string.label_last_name)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.label_email)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        actionStatus?.let { statusMsg ->
            Text(
                text = statusMsg,
                color = if (statusMsg == successRegistrationMsg || statusMsg == successLoginMsg) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = {
                actionStatus = null // Clear previous status
                actionStatus = performRegistration(
                    context,
                    firstName.trim(),
                    lastName.trim(),
                    email.trim(),
                    navController
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = stringResource(R.string.button_register))
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                actionStatus = null // Clear previous status
                actionStatus = performLogin(
                    context,
                    email.trim(),
                    navController
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = stringResource(R.string.button_login))
        }
    }
}

@Preview(showBackground = true, name = "Onboarding Screen Preview")
@Composable
fun OnboardingPreview() {
    LittleLemonTheme { 
        Onboarding(navController = rememberNavController())
    }
}
