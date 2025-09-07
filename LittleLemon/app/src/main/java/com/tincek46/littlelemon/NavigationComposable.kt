package com.tincek46.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tincek46.littlelemon.composables.Home
import com.tincek46.littlelemon.composables.Onboarding
import com.tincek46.littlelemon.composables.Profile
// Import centralized SharedPreferences constants
import com.tincek46.littlelemon.PREFS_NAME
import com.tincek46.littlelemon.KEY_IS_LOGGED_IN

@Composable
fun NavigationComposable(menuItems: List<MenuItemEntity>) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Determine start destination based on whether user details are saved
    val startDestination = if (sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) {
        Home.route
    } else {
        Onboarding.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Onboarding.route) { Onboarding(navController) }
        composable(Home.route) { Home(navController, menuItems) } 
        composable(Profile.route) { Profile(navController) }
    }
}
