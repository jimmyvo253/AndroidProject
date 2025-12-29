package com.example.androidproject

import Navigator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.androidproject.data.local.MenuDatabase
import com.example.androidproject.ui.theme.AndroidProjectTheme
import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.androidproject.data.local.FlashCardDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java
import com.example.androidproject.network.NetworkService

//The Preferences DataStore implementation uses the DataStore and Preferences classes to persist key-value pairs to disk.
//Use the property delegate created by preferencesDataStore to create an instance of DataStore<Preferences>.
//Call it once at the top level of your Kotlin file. Access DataStore through this property
//throughout the rest of your application. This makes it easier to keep your DataStore as a singleton.
val Context.dataStore by preferencesDataStore(
    name = "user_credentials"
)

//Because Preferences DataStore doesn't use a predefined schema,
//you must use the corresponding key type function to define a key for each value that you need to store
//in the DataStore<Preferences> instance.
//For example, to define a key for an int value, use intPreferencesKey()
val TOKEN = stringPreferencesKey("token")
val EMAIL = stringPreferencesKey("email")
class MainActivity : ComponentActivity() {

    private lateinit var db: MenuDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidProjectTheme {
                val navController = rememberNavController()
                val appContext = applicationContext
                // Pass DAO into Navigator

                val db = FlashCardDatabase.getDatabase(appContext)
                val flashCardDao = db.flashCardDao()

                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl("https://egsbwqh7kildllpkijk6nt4soq0wlgpe.lambda-url.ap-southeast-1.on.aws/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val networkService = retrofit.create(NetworkService::class.java)

                Navigator(navController, flashCardDao, networkService)
            }
        }
    }
}
