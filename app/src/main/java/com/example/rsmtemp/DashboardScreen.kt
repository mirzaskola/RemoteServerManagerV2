package com.example.rsmtemp

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.rsmtemp.data.Server
import com.example.rsmtemp.data.User
import com.example.rsmtemp.ui.theme.RSMTempTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardScreen {
}
@Composable
fun DashboardScreenContent(userEmail: String?, navController: NavController ,modifier: Modifier = Modifier, serverViewModel: ServerViewModel, userViewModel: UserViewModel) {
    val email = userEmail!!.toString()
    println(email)
    val defaultUser = User(1, "", "", "", "")
    // Ova linija ispod je zasto se ne renderuje ulogovani user
    val currentUser by userViewModel.getUserByEmail(email).collectAsState(defaultUser)
    val serversByOwner by serverViewModel.getAllByOwnerId(currentUser.id!!).collectAsState(emptyList())

    Column(modifier = modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Welcome,")
            Text(text = currentUser.username)
        }

        Column(modifier = modifier.padding(8.dp)) {
            Text(text = "Server list", modifier = modifier.padding(start = 2.dp), fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Gray)
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())

        ) {
            Column() {
                serversByOwner.forEach { server ->
                    DashboardServerCard(navController = navController, server_id = server.id!!,name = server.name, type = server.type, serverViewModel = serverViewModel, server = server)
                }
            }
        }
    }
}
@Composable
fun DashboardServerCard(navController: NavController, modifier: Modifier = Modifier, server_id: Int, name: String, type: String, serverViewModel: ServerViewModel, server: Server){
//    val server by serverViewModel.getOneById(server_id).collectAsState(emptyList())
    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var status by remember { mutableStateOf(server.status) }
    var stringStatus by remember { mutableStateOf("") }
    var stringFirewall by remember { mutableStateOf("") }

    stringStatus = when(status){
        true -> "Active"
        false -> "Inactive"
    }

    Card(backgroundColor = Color.White,
//        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier
            .fillMaxWidth()
//            .padding(horizontal = 5.dp))
    )
    {
        Divider(Modifier.padding(horizontal = 10.dp))
        Row(modifier = modifier
            .padding(5.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center) {
                Text(text = name, modifier = modifier.padding(start = 5.dp), fontWeight = FontWeight.Medium)
                Text(text = stringStatus, modifier = modifier.padding(start = 5.dp), fontSize = 14.sp, color = Color.Gray)
                Text(text = type, modifier = modifier.padding(start = 5.dp), fontSize = 14.sp, color = Color.Gray)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { showMenu = !showMenu}
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More"
                    )
                    DropdownMenu(expanded = showMenu, onDismissRequest = {} ) {
                        DropdownMenuItem(onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                showMenu = false
                                status = true
                                server.status = true
                                serverViewModel.upsertServer(server = server)
                            }
                        }) {
                            Text(text = "Start")
                        }
                        DropdownMenuItem(onClick = {
                                CoroutineScope(Dispatchers.Main).launch {
                                    showMenu = false
                                    status = false
                                    server.status = false
                                    serverViewModel.upsertServer(server = server)
                            }
                            }) {
                            Text(text = "Stop")
                        }
//                        navController.navigate(Screen.ManageScreen.withArgs(username))
                        DropdownMenuItem(onClick = {
                            showMenu = false
                            navController.navigate(Screen.ManageScreen.withArgs("$server_id"))
                        }) {
                            Text(text = "Manage")
                        }
                        DropdownMenuItem(onClick = {CoroutineScope(Dispatchers.Main).launch {
                            showMenu = false
                            try {
                                serverViewModel.deleteServer(server)

                            }catch (e: Exception){
                                println("splovilo")
                            }
                        }}) {
                            Text(text = "Delete")
                        }
//                        DropdownMenuItem(onClick = {navController.navigate(Screen.ManageScreen.route)}) {
//                            Text(text = "Manage")
//                        }
                    }
                }
                IconButton(onClick = { /*TODO*/ }
                )
                {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Execute script"
                    )
                }
            }

        }
        Divider(Modifier.padding(horizontal = 10.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    RSMTempTheme {
//        DashboardScreenContent(navController = rememberNavController())
    }
}