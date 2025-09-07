package com.tincek46.littlelemon.composables

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape // Added for RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors // Added for TextSelectionColors
import androidx.compose.material.icons.Icons // Added for Icons
import androidx.compose.material.icons.filled.Email // Added for Email icon
import androidx.compose.material.icons.filled.Person // Added for Person icon
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun Profile(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)
    val firstName = sharedPreferences.getString("firstName", "N/A") ?: "N/A"
    val lastName = sharedPreferences.getString("lastName", "N/A") ?: "N/A"
    val email = sharedPreferences.getString("email", "N/A") ?: "N/A"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StandardAppHeader(navController = navController) 

        Text(
            text = "Profile information:", 
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp, bottom = 24.dp) 
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            val subtleGray = Color(0xFFF5F5F5)
            val blackColor = Color.Black

            TextField(
                value = firstName,
                onValueChange = {},
                label = { Text("First name") },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Person Icon") }, 
                readOnly = true,
                enabled = true, 
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = blackColor,
                    unfocusedTextColor = blackColor,
                    disabledTextColor = blackColor, 
                    cursorColor = Color.Transparent,
                    selectionColors = TextSelectionColors(handleColor = Color.Transparent, backgroundColor = Color.Transparent),
                    focusedContainerColor = subtleGray,
                    unfocusedContainerColor = subtleGray,
                    disabledContainerColor = subtleGray,
                    focusedIndicatorColor = blackColor, // Underline color
                    unfocusedIndicatorColor = blackColor, // Underline color
                    disabledIndicatorColor = blackColor, // Underline color
                    focusedLabelColor = blackColor,
                    unfocusedLabelColor = blackColor,
                    disabledLabelColor = blackColor,
                    focusedLeadingIconColor = blackColor, 
                    unfocusedLeadingIconColor = blackColor, 
                    disabledLeadingIconColor = blackColor 
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            TextField(
                value = lastName,
                onValueChange = {},
                label = { Text("Last name") },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Person Icon") }, 
                readOnly = true,
                enabled = true, 
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = blackColor,
                    unfocusedTextColor = blackColor,
                    disabledTextColor = blackColor,
                    cursorColor = Color.Transparent,
                    selectionColors = TextSelectionColors(handleColor = Color.Transparent, backgroundColor = Color.Transparent),
                    focusedContainerColor = subtleGray,
                    unfocusedContainerColor = subtleGray,
                    disabledContainerColor = subtleGray,
                    focusedIndicatorColor = blackColor, // Underline color
                    unfocusedIndicatorColor = blackColor, // Underline color
                    disabledIndicatorColor = blackColor, // Underline color
                    focusedLabelColor = blackColor,
                    unfocusedLabelColor = blackColor,
                    disabledLabelColor = blackColor,
                    focusedLeadingIconColor = blackColor, 
                    unfocusedLeadingIconColor = blackColor, 
                    disabledLeadingIconColor = blackColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            TextField(
                value = email,
                onValueChange = {},
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon") }, 
                readOnly = true,
                enabled = true, 
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = blackColor,
                    unfocusedTextColor = blackColor,
                    disabledTextColor = blackColor,
                    cursorColor = Color.Transparent,
                    selectionColors = TextSelectionColors(handleColor = Color.Transparent, backgroundColor = Color.Transparent),
                    focusedContainerColor = subtleGray,
                    unfocusedContainerColor = subtleGray,
                    disabledContainerColor = subtleGray,
                    focusedIndicatorColor = blackColor, // Underline color
                    unfocusedIndicatorColor = blackColor, // Underline color
                    disabledIndicatorColor = blackColor, // Underline color
                    focusedLabelColor = blackColor,
                    unfocusedLabelColor = blackColor,
                    disabledLabelColor = blackColor,
                    focusedLeadingIconColor = blackColor, 
                    unfocusedLeadingIconColor = blackColor, 
                    disabledLeadingIconColor = blackColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp) 
            )
        }
        
        Spacer(modifier = Modifier.weight(1f)) 

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), 
            horizontalArrangement = Arrangement.spacedBy(8.dp) 
        ) {
            Button(
                onClick = {
                    navController.popBackStack() 
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(text = "Go Back")
            }

            Button(
                onClick = {
                    sharedPreferences.edit { clear() }
                    navController.navigate("onboarding") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14), contentColor = Color.Black),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text(text = "Log out")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    Profile(navController = navController)
}
