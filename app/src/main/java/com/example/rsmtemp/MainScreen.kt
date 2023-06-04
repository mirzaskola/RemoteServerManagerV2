package com.example.rsmtemp

import android.widget.ToggleButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rsmtemp.data.Alert
import com.example.rsmtemp.data.Server
import com.example.rsmtemp.data.User
import com.example.rsmtemp.ui.theme.RSMTempTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch


class MainScreen {
}
@Composable
fun Navigation(navController: NavHostController, userViewModel: UserViewModel, serverViewModel: ServerViewModel, alertViewModel: AlertViewModel, onShowAddServerDialogChange: (Boolean) -> Unit, dialogIsShown: Boolean){

    val defaultUser = User(1, "", "", "", "")
    var loggedInUser by remember { mutableStateOf<User>(defaultUser) }

    if (dialogIsShown == true){
        AddNewServerDialog(
            onConfirmClick = { onShowAddServerDialogChange(false) },
            onDismissClick = { onShowAddServerDialogChange(false) },
            serverViewModel = serverViewModel,
            alertViewModel = alertViewModel,
            user = loggedInUser
        )
    }

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route){
        composable(route = Screen.LoginScreen.route){
            // Ovdje cemo ubaciti Login skrin
            LoginScreen(navController = navController){ user ->
                loggedInUser = user
            }

        }
        // koja je jebena poenta ovoga
//       composable(
//            route = Screen.DashboardScreen.route + "/{userEmail}",
//            arguments = listOf(
//                navArgument("userEmail"){
//                    type = NavType.StringType
//                    defaultValue = ""
//                    nullable = true
//                }
//            )
//        ){
//            entry -> DashboardScreenContent(navController = navController, serverViewModel = serverViewModel, userViewModel = userViewModel, userEmail = entry.arguments?.getString("userEmail"))
//        }

        // Ovo ispod je razlog sto dashboard vazda root na dashboardu prikazuje
        composable(route = Screen.DashboardScreen.route){
            // Ovdje cemo ubaciti Dashboard skrin
            DashboardScreenContent(navController = navController, serverViewModel = serverViewModel, userViewModel = userViewModel, userEmail = loggedInUser.email)
        }
        composable(route = Screen.AlertScreen.route){
            // Ovdje cemo ubaciti Alert skrin
            AlertScreenContent(alertViewModel = alertViewModel, serverViewModel = serverViewModel, user = loggedInUser)
        }
        composable(route = Screen.ProfileScreen.route){
            // Ovdje cemo ubaciti Profile skrin
            ProfileScreenContent(navController = navController, userViewModel = userViewModel, user = loggedInUser)
        }
        composable(
            route = Screen.ManageScreen.route + "/{serverId}",
            arguments = listOf(
                navArgument("serverId"){
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
                entry -> ServerContent(serverId = entry.arguments?.getString("serverId"), serverViewModel)
        }
//        composable(route = Screen.ManageScreen.route){
//            ServerContentAhh(serverViewModel)
//        }

    }
}


@Composable
fun RemoteServerManagerApp(modifier: Modifier = Modifier,) {
    val serverViewModel: ServerViewModel = viewModel(factory = ServerViewModel.factory)
    val userViewModel: UserViewModel = viewModel(factory = UserViewModel.factory)
    val alertViewModel: AlertViewModel = viewModel(factory = AlertViewModel.factory)

//    var listaServera: Flow<List<Server>> = serverViewModel.getAllByOwnerId(1)
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState(drawerState = DrawerState(DrawerValue.Closed))
    val appTitle = "Remote Server Manager"
    var topBarTitle by remember { mutableStateOf(appTitle) }
    var isDrawerEnabled by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf(Screen(route = Screen.LoginScreen.route)) }
    var showAddServerDialog by remember { mutableStateOf(false) }

    // Uzimanje current skrina kroz rute
    val backStackEntry by navController.currentBackStackEntryAsState()
        currentScreen = when (backStackEntry?.destination?.route) {
        Screen.DashboardScreen.route -> Screen.DashboardScreen
        Screen.AlertScreen.route -> Screen.AlertScreen
        Screen.ProfileScreen.route -> Screen.ProfileScreen
        Screen.ManageScreen.route -> Screen.ManageScreen
        Screen.LoginScreen.route -> Screen.LoginScreen
        else -> Screen.ManageScreen// Default screen if the route is not matched
    }
    // Updateovanje TopBarTitlea kroz currentscreen
    LaunchedEffect(currentScreen) {
        topBarTitle = when (currentScreen) {
            is Screen.DashboardScreen -> "Remote Server Manager"
            is Screen.AlertScreen -> "Alerts"
            is Screen.ProfileScreen -> "Profile"
            is Screen.ManageScreen -> "Manage Servers"
            is Screen.LoginScreen -> "Login"

            else -> {""}
        }
//        scope.launch {
//            listaServera.collect { serverList ->
//                serverList.forEach { server ->
//                    println(server)
//                }
//            }
//        }
    }
    DisposableEffect(currentScreen) {
        if (currentScreen == Screen.LoginScreen) {
            // Disable drawer gesture
            isDrawerEnabled = false
        }

        onDispose {
            // Re-enable drawer gesture when effect is disposed
            isDrawerEnabled = true
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { if(currentScreen != Screen.LoginScreen){
                    TopBar(topText = topBarTitle, onNavigationIconClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    })
                }
        },
        drawerGesturesEnabled = isDrawerEnabled,
        drawerContent = {
            if(currentScreen != Screen.LoginScreen){
                MenuDrawer(navController, closeDrawer = { scope.launch { scaffoldState.drawerState.close() } })
            }
        },

        bottomBar = { if(currentScreen != Screen.LoginScreen){
                        BottomBar(
                        items = listOf(
                            BottomNavItem(name = "Dashboard", route = Screen.DashboardScreen.route, icon = Icons.Default.Home),
                            BottomNavItem(name = "Alerts", route = Screen.AlertScreen.route, icon = Icons.Default.Notifications),
                            BottomNavItem(name = "Profile", route = Screen.ProfileScreen.route, icon = Icons.Default.Person)
                        ),
                        navController = navController,
                        onItemCLick = {
                            navController.navigate(it.route)
                        }
                    )
                }
        },
        content = { paddingValues ->
            Box(modifier = modifier.padding(paddingValues)) {
                Navigation(navController = navController, userViewModel = userViewModel, serverViewModel = serverViewModel, alertViewModel = alertViewModel, onShowAddServerDialogChange = { showAddServerDialog = it }, dialogIsShown = showAddServerDialog)
            }
        },
        floatingActionButton = {
            if(currentScreen == Screen.DashboardScreen){
                Surface(
                    elevation = 2.dp,
                    shape = CircleShape,
                    color = Color.Black,
//                    border = BorderStroke(0.1.dp, Color.Black)
                ) {
                    FloatingActionButton(
                        contentColor = Color.White,
                        backgroundColor = Color(0, 150, 150),
                        onClick = { showAddServerDialog = true }
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    )

}

@Composable
fun TopBar(topText: String, onNavigationIconClick: () -> Unit){
    TopAppBar(
        title = {
            Text(
                text = topText,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigationIconClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },

        backgroundColor = Color(49,62,79),
        contentColor = Color.LightGray
    )
}


@Composable
fun BottomBar(items: List<BottomNavItem>, navController: NavController, onItemCLick: (BottomNavItem) -> Unit) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(contentColor = Color.LightGray, elevation = 10.dp, backgroundColor = Color(49,62,79)) {
        items.forEach { item->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem( icon = {
                Icon(imageVector = item.icon,"")
            },
                label = { Text(text = item.name) }, selected = selected,
                onClick = {
                    onItemCLick(item)
                }
            )
        }

    }
}
@Composable
fun MenuDrawer(navController: NavController, closeDrawer: () -> Unit){
    Column(){
        MenuDrawerHeader()
        MenuDrawerContent(items = listOf(
            MenuItem(
                id = "settings",
                title = "Settings",
                contentDescription = "Settings button",
                icon = Icons.Default.Settings
            ),
            MenuItem(
                id = "info",
                title = "Info",
                contentDescription = "Info button",
                icon = Icons.Default.Info
            ),
            MenuItem(
                id = "logout",
                title = "Log out",
                contentDescription = "Logout button",
                icon = Icons.Filled.Logout
            ),
        ),
//          onItemCLick = {/*Ovdje navigaciju konfigurisati sa when: it.id="nesto" -> navigateToblabla*/})
            onItemCLick = {when (it.id){
                                "settings" -> {navController.navigate(Screen.ProfileScreen.route)
                                    closeDrawer()
                                }
                                "info" -> {navController.navigate(Screen.ProfileScreen.route)
                                    closeDrawer()
                                }
                                "logout" -> {navController.navigate(Screen.LoginScreen.route)
                                    closeDrawer()
                                }
                            }

            })
    }
}
@Composable
fun MenuDrawerHeader(){
    val logo = painterResource(id = R.drawable.main_logo)
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)){
        Image(painter = logo, contentDescription = "App logo")
        Text(text = "Remote Server Manager", fontSize = 14.sp, color = Color.Gray)
//        Divider(Modifier.padding(vertical = 5.dp))
    }

}
@Composable
fun MenuDrawerContent(items: List<MenuItem>, modifier: Modifier = Modifier, onItemCLick: (MenuItem) -> Unit){
    LazyColumn(
        modifier
            .fillMaxSize()
    ) {
        items(items){ item ->
            Row(modifier = modifier
                .clickable { onItemCLick(item) }
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = item.icon, contentDescription = item.contentDescription, tint = Color.DarkGray)
                Text(text = item.title, modifier = modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp), fontWeight = FontWeight.SemiBold, color = Color.DarkGray
                )
            }
            Divider(modifier = modifier.padding(horizontal = 16.dp))
        }

    }

}

@Composable
fun AddNewServerDialog(
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    serverViewModel: ServerViewModel,
    alertViewModel: AlertViewModel,
    user: User
) {
    val nameState = remember { mutableStateOf("") }
    val typeState = remember { mutableStateOf("") }
    val ownerIdState = remember { mutableStateOf(user.id) }
    val storageState = remember { mutableStateOf("") }
    val ramState = remember { mutableStateOf("") }
    val osState = remember { mutableStateOf("") }
    val statusState = remember { mutableStateOf(false) }
    val firewallState = remember { mutableStateOf(false) }


//    emailState.value = user.email
//    usernameState.value = user.username
//    phoneNumberState.value = user.phone
//    passwordState.value = user.password

    Dialog(
        onDismissRequest = onDismissClick
    ) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = 16.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                androidx.compose.material3.Text(
                    text = "Add new server",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
                TextField(
                    shape = RoundedCornerShape(10.dp),
                    value = nameState.value,
                    onValueChange = { nameState.value = it },
                    label = { androidx.compose.material3.Text(text = "Server name", fontSize = 14.sp) },
                    modifier = Modifier.padding(horizontal = 16.dp),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    shape = RoundedCornerShape(10.dp),
                    value = typeState.value,
                    onValueChange = { typeState.value = it },
                    label = { androidx.compose.material3.Text(text = "Server yype", fontSize = 14.sp) },
                    modifier = Modifier.padding(horizontal = 16.dp),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    shape = RoundedCornerShape(10.dp),
                    value = storageState.value,
                    onValueChange = { storageState.value = it },
                    label = { androidx.compose.material3.Text(text="Storage type", fontSize = 14.sp) },
                    modifier = Modifier.padding(horizontal = 16.dp),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    shape = RoundedCornerShape(10.dp),
                    value = ramState.value,
                    onValueChange = { ramState.value = it },
                    label = { androidx.compose.material3.Text(text = "RAM type", fontSize = 14.sp) },
                    modifier = Modifier.padding(horizontal = 16.dp),
                    maxLines = 1
                )
//                Spacer(modifier = Modifier.height(8.dp))
//                TextField(
//                    shape = RoundedCornerShape(10.dp),
//                    value = statusState.value.toString(),
//                    onValueChange = { statusState.value = statusState.value },
//                    label = { androidx.compose.material3.Text(text = "Enabled", fontSize = 14.sp) },
//                    modifier = Modifier.padding(horizontal = 16.dp),
//                    maxLines = 1
//                )
                // Ovdje treba ram amount popravit u string jer je u bazi Int
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    shape = RoundedCornerShape(10.dp),
                    value = osState.value,
                    onValueChange = { osState.value = it},
                    label = { androidx.compose.material3.Text(text = "Operating System", fontSize = 14.sp) },
                    modifier = Modifier.padding(horizontal = 16.dp),
                    maxLines = 1
                )
//                TextField(
//                    shape = RoundedCornerShape(10.dp),
//                    value = firewallState.value.toString(),
//                    onValueChange = { firewallState.value = firewallState.value},
//                    label = { androidx.compose.material3.Text(text = "Firewall", fontSize = 14.sp) },
//                    modifier = Modifier.padding(horizontal = 16.dp),
//                    maxLines = 1
//                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                    Text(text = "Active",fontSize = 16.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp), color = Color.Gray)
                    Switch(checked = statusState.value, onCheckedChange = {statusState.value = it})
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                    Text(text = "Firewall",fontSize = 16.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp), color = Color.Gray)
                    Switch(checked = firewallState.value, onCheckedChange = {firewallState.value = it})
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Button(
                        onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                try {
                                    serverViewModel.upsertServer(Server(id = null, name = nameState.value, type = typeState.value, owner_id = ownerIdState.value!!, cpu = "Xeon" ,ram = ramState.value, ram_amount = "", storage = storageState.value, storage_amount = "", gpu = "N/A", gpu_vram_amount = "",os = osState.value, status = statusState.value, firewall = firewallState.value, ip = "1.2.3.4", dns = "Dynamic"))
                                    serverViewModel.generateAlert()
                                    onConfirmClick()
                                }catch (e: Exception){
                                    println("splovilo")
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(49,62,79)),
//                        enabled = nameState.value.isNotBlank() &&
//                                typeState.value.isNotBlank() &&
//                                storageState.value.isNotBlank() &&
//                                ramState.value.isNotBlank() &&
//                                osState.value.isNotBlank()

                    ) {
                        androidx.compose.material3.Text("Confirm", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Normal)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onDismissClick,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(89,136,154)),
                    ) {
                        androidx.compose.material3.Text(text = "Cancel", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Normal)
                    }
                }
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun DialogPreview() {
//    RSMTempTheme {
//        AddNewServerDialog(
//            onConfirmClick = { /*TODO*/ },
//            onDismissClick = { /*TODO*/ },
//            serverViewModel = null,
//            alertViewModel = ,
//            user =
//        )
//    }
//}