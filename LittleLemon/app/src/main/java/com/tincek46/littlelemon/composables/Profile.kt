package com.tincek46.littlelemon.composables

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tincek46.littlelemon.R
import com.tincek46.littlelemon.ui.theme.LittleLemonTheme
// Import centralized SharedPreferences constants
import com.tincek46.littlelemon.PREFS_NAME
import com.tincek46.littlelemon.KEY_FIRST_NAME
import com.tincek46.littlelemon.KEY_LAST_NAME
import com.tincek46.littlelemon.KEY_EMAIL
// KEY_IS_LOGGED_IN is not directly used in Profile.kt for reading, but good to be aware of AppConstants

@Composable
fun Profile(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val textNotAvailable = stringResource(R.string.text_not_available)
    val firstName = sharedPreferences.getString(KEY_FIRST_NAME, textNotAvailable) ?: textNotAvailable
    val lastName = sharedPreferences.getString(KEY_LAST_NAME, textNotAvailable) ?: textNotAvailable
    val email = sharedPreferences.getString(KEY_EMAIL, textNotAvailable) ?: textNotAvailable

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StandardAppHeader(navController = navController)

        Text(
            text = stringResource(R.string.profile_information_title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp, bottom = 24.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileInfoItem(label = stringResource(R.string.label_first_name), value = firstName, icon = Icons.Filled.Person)
            ProfileInfoItem(label = stringResource(R.string.label_last_name), value = lastName, icon = Icons.Filled.Person)
            ProfileInfoItem(label = stringResource(R.string.label_email), value = email, icon = Icons.Filled.Email)
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            ) {
                Text(text = stringResource(R.string.button_go_back))
            }

            Button(
                onClick = {
                    sharedPreferences.edit { clear() }
                    navController.navigate(com.tincek46.littlelemon.Onboarding.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary, 
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text(text = stringResource(R.string.button_log_out))
            }
        }
    }
}

@Composable
private fun ProfileInfoItem(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = "$label icon",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = if (icon != null) 28.dp else 0.dp)
        )
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    LittleLemonTheme { 
        Profile(navController = rememberNavController())
    }
}
