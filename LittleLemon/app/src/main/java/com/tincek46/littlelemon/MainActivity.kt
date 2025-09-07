package com.tincek46.littlelemon

import android.os.Bundle
import android.util.Log // <-- ADD THIS IMPORT
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
// import androidx.compose.material3.Text // No longer needed directly here
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState // Needed for LiveData to State conversion
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tincek46.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers // For launching coroutines on IO thread
import kotlinx.coroutines.launch // For launching coroutines
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {

    // Database instance
    private lateinit var database: AppDatabase

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            val customJson = Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                useAlternativeNames = false
            }
            json(customJson, ContentType.Application.Json)
            json(customJson, ContentType.Text.Plain) // Handle Text.Plain as JSON
        }
        followRedirects = true
        expectSuccess = true
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        val url = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
        return try {
            val response: MenuNetwork = client.get(url).body()
            response.menu
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize database
        database = AppDatabase.getDatabase(applicationContext)

        setContent {
            LittleLemonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Observe menu items from the database
                    val menuItemsFromDb by database.menuDao().getAllMenuItems().observeAsState(initial = emptyList())

                    // Fetch and save to DB only if DB is empty
                    LaunchedEffect(Unit) {
                        launch(Dispatchers.IO) { // Use IO dispatcher for DB operations
                            if (database.menuDao().isEmpty()) {
                                Log.d("MainActivity", "Database is empty, fetching menu...")
                                val networkMenuItems = fetchMenu()
                                if (networkMenuItems.isNotEmpty()) {
                                    Log.d("MainActivity", "Fetched ${networkMenuItems.size} items from network.")
                                    val entityMenuItems = networkMenuItems.map { networkItem ->
                                        // Log each item's title and image URL
                                        Log.d("MainActivity", "Mapping item: ${networkItem.title}, Image URL: ${networkItem.image}")
                                        MenuItemEntity(
                                            id = networkItem.id,
                                            title = networkItem.title,
                                            description = networkItem.description,
                                            price = networkItem.price,
                                            image = networkItem.image.replace("github.com/", "raw.githubusercontent.com/").replace("/blob", "").replace("?raw=true", ""),
                                            category = networkItem.category
                                        )
                                    }
                                    database.menuDao().insertAll(entityMenuItems)
                                    Log.d("MainActivity", "Inserted ${entityMenuItems.size} items into database.")
                                } else {
                                    Log.d("MainActivity", "Network fetch returned no items.")
                                }
                            } else {
                                Log.d("MainActivity", "Database already populated. Found ${menuItemsFromDb.size} items.")
                            }
                        }
                    }

                    // Set up the navigation, passing the menu items
                    NavigationComposable(menuItems = menuItemsFromDb)
                }
            }
        }
    }
}

// The Greeting composable and its preview can be removed as they are no longer used.
// @Composable
// fun Greeting(name: String, modifier: Modifier = Modifier) {
// Text(
// text = "Message: $name",
// modifier = modifier
// )
// }
//
// @Preview(showBackground = true)
// @Composable
// fun GreetingPreview() {
// LittleLemonTheme {
// Greeting("Preview Greeting")
// }
// }
