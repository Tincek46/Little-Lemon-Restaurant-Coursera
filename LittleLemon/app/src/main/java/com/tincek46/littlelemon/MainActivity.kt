package com.tincek46.littlelemon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.tincek46.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {

    // Database instance
    private lateinit var database: AppDatabase

    // Ktor HTTP Client instance
    // For a larger app, consider using a DI framework like Hilt to manage singleton instances.
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            val customJson = Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                useAlternativeNames = false // Default, added for clarity
            }
            json(customJson, ContentType.Application.Json)
            json(customJson, ContentType.Text.Plain) // Handle Text.Plain as JSON, if applicable
        }
        followRedirects = true // Default is true, explicit for clarity
        expectSuccess = true // Default is false, set to true to throw exceptions for non-2xx responses
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val url = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
        return try {
            val response: MenuNetwork = client.get(url).body()
            response.menu
        } catch (e: Exception) {
            // For production, consider using a more robust logging framework than printStackTrace()
            // and potentially different error handling strategies.
            e.printStackTrace()
            emptyList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize database
        // For a larger app, consider using a DI framework like Hilt.
        database = AppDatabase.getDatabase(applicationContext)

        setContent {
            LittleLemonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val menuItemsFromDb by database.menuDao().getAllMenuItems().observeAsState(initial = emptyList())

                    // Fetch and save to DB only if DB is empty.
                    // Logs here are for debugging; consider wrapping in BuildConfig.DEBUG for release.
                    LaunchedEffect(Unit) {
                        launch(Dispatchers.IO) { // Use IO dispatcher for DB and network operations
                            if (database.menuDao().isEmpty()) {
                                Log.d("MainActivity", "Database is empty, fetching menu...")
                                val networkMenuItems = fetchMenu()
                                if (networkMenuItems.isNotEmpty()) {
                                    Log.d("MainActivity", "Fetched ${networkMenuItems.size} items from network.")
                                    val entityMenuItems = networkMenuItems.map { networkItem ->
                                        Log.d("MainActivity", "Mapping item: ${networkItem.title}, Image URL original: ${networkItem.image}")
                                        // Convert GitHub blob URLs to raw content URLs for direct image loading
                                        val imageUrl = networkItem.image
                                            .replace("github.com/", "raw.githubusercontent.com/")
                                            .replace("/blob", "")
                                            .replace("?raw=true", "")
                                        Log.d("MainActivity", "Image URL transformed: $imageUrl")
                                        MenuItemEntity(
                                            id = networkItem.id,
                                            title = networkItem.title,
                                            description = networkItem.description,
                                            price = networkItem.price,
                                            image = imageUrl,
                                            category = networkItem.category
                                        )
                                    }
                                    database.menuDao().insertAll(entityMenuItems)
                                    Log.d("MainActivity", "Inserted ${entityMenuItems.size} items into database.")
                                } else {
                                    Log.d("MainActivity", "Network fetch returned no items.")
                                }
                            } else {
                                Log.d("MainActivity", "Database already populated. Found ${menuItemsFromDb.size} items in observed LiveData.")
                            }
                        }
                    }
                    NavigationComposable(menuItems = menuItemsFromDb)
                }
            }
        }
    }
}

// Removed Greeting and GreetingPreview as they are no longer used.
