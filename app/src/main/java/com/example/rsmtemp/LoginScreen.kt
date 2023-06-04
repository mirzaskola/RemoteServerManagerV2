package com.example.rsmtemp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.rsmtemp.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.Exception


@Composable
fun LoginScreen(
    userViewModel: UserViewModel = viewModel(factory = UserViewModel.factory),
    navController: NavController,
    onLoginSuccess: (User) -> Unit
) {
    val defaultUser = User(1, "", "", "", "")
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
//    val user by userViewModel.getUserById(2).collectAsState(defaultUser)
    var user by remember { mutableStateOf(defaultUser) }
    var showError by remember { mutableStateOf(false) }
//    println(user.username)

    //val openDialog = remember { mutableStateOf(false)  }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.padding(16.dp)
        )
        Button(
            /* IMPLEMENT AUTH LOGIC HERE */
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val tempUser = userViewModel.getUserByEmail(email).first()
                        if (password == tempUser.password) {
                            //                    navController.navigate(Screen.DashboardScreen.withArgs(username))
                            user = tempUser
//                            navController.navigate(Screen.DashboardScreen.withArgs(user.email))
                            onLoginSuccess(user)
                            navController.navigate(Screen.DashboardScreen.route)
                            //user = tempUser
                        } else {
                            println("mjau")
                        }
                    } catch (e: Exception) {
                        showError = true
                        println("MIJAAAAAAAAAAAAAAU")
                    }
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Login")
        }
        if (showError) {
            AlertDialog(
                onDismissRequest = {
                    showError = false
                },
                title = {
                    Text(text = "Invalid login")
                },
                text = {
                    Text("Invalid username or password. Please try again.")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showError = false
                        }
                    ) {
                        Text("Close")
                    }
                }
            )
        }

        /*
        if(!password.equals("1")){
            Column {
                openDialog.value = true
                if (openDialog.value) {

                    AlertDialog(
                        onDismissRequest = {
                            openDialog.value = false
                        },
                        title = {
                            Text(text = "Authentication error !")
                        },
                        text = {
                            Text("Username or password are incorrect !")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    openDialog.value = false
                                }) {
                                Text("Close")
                            }
                        }
                    )
                }
            }
        }
        */
    }
}








