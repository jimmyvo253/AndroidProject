package com.example.androidproject

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.androidproject.network.NetworkService
import kotlinx.coroutines.launch


//@Composable
//fun LoginScreen(
//    changeMessage: (String) -> Unit
//) {
//
//    var email by remember { mutableStateOf("") }
//    var token by remember { mutableStateOf("") }
//    val scope = rememberCoroutineScope()
//
//    Column(
//        modifier = Modifier.padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        OutlinedTextField(
//            value = token,
//            onValueChange = {},
//            modifier = Modifier
//                .fillMaxWidth()
//                .semantics { contentDescription = "tokenTextField" },
//            label = { Text("token") },
//            readOnly = true
//        )
//
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .semantics { contentDescription = "emailTextField" },
//            label = { Text("email") }
//        )
//
//        Button(
//            modifier = Modifier
//                .fillMaxWidth()
//                .semantics { contentDescription = "Enter" },
//            onClick = {
//                scope.launch {
//                    try {
//                        val result = withContext(Dispatchers.IO) {
//                            NetworkClient.service.generateToken(
//                                email = UserCredential(email)
//                            )
//                        }
//                        token = result.token
//                        changeMessage("The token has been received successfully.")
//                        Log.d("LOGIN", "token=$result")
//                    } catch (e: Exception) {
//                        changeMessage("There was an error in the token request.")
//                        Log.d("LOGIN", "Error: $e")
//                    }
//                }
//            }
//        ) {
//            Text("Enter")
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    changeMessage: (String) -> Unit,
    networkService: NetworkService,
    navigateToToken: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        changeMessage("Login Screen")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { this.contentDescription = "emailTextField" },
            label = { Text("Email") }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "Enter" },
            onClick = {
                scope.launch {
                    try {
                        val response = networkService.generateToken(
                            body = UserCredential(email)
                        )

                        if (response.code == 200) {
                            changeMessage("Token sent to your email.")
                            navigateToToken(email)
                        } else {
                            changeMessage(response.message)
                        }

                    } catch (e: Exception) {
                        changeMessage("Network error.")
                        Log.e("LOGIN", "Error", e)
                    }
                }
            }
        ) {
            Text("Enter")
        }
    }
}
