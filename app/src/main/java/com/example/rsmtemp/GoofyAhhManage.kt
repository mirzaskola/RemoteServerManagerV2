package com.example.rsmtemp

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rsmtemp.ui.theme.RSMTempTheme
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

//@Composable
//fun ServerScreen(modifier: Modifier = Modifier) {
//    val navController = rememberNavController()
//    val scaffoldStateServer = rememberScaffoldState()
//    val scopeServer = rememberCoroutineScope()
//    Scaffold(
//        scaffoldState = scaffoldStateServer,
//        topBar = {
//            TopBar(topText = "Server 1", onNavigationIconClick = {
//                scopeServer.launch {
//                    scaffoldStateServer.drawerState.open()
//                }
//            })
//        },
//        content = { paddingValues ->
//            Box(modifier = modifier.padding(paddingValues)) {
//                ServerContent(modifier = modifier.verticalScroll(rememberScrollState()))
//            }
//        },
//        bottomBar = { BottomBar(
//            items = listOf(
//                BottomNavItem(name = "Dashboard", route = Screen.MainScreenStr.route, icon = Icons.Default.Home),
//                BottomNavItem(name = "Alerts", route = Screen.AlertScreenStr.route, icon = Icons.Default.Notifications),
//                BottomNavItem(name = "Profile", route = Screen.ProfileScreenStr.route, icon = Icons.Default.Person)
//            ),
//            navController = navController,
//            onItemCLick = {
//                navController.navigate(it.route)
//            }
//        )
//        },
//
//        )
//}
//@Composable
//fun Content(){
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Text(text = "Overview", fontSize = 16.sp, fontWeight = FontWeight.Normal, modifier = Modifier.padding(10.dp), color = Color.Gray)
//        Divider(Modifier.padding(horizontal = 10.dp))
//        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//            Row(Modifier.padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
//                HardwareCard(title = "Xeons ssdfsdfs", description = "", id = R.drawable.cpu)
//                HardwareCard(title = "16GB DDR4", description = "", id = R.drawable.ram)
//                HardwareCard(title = "Samsung 970", description = "", id = R.drawable.storage)
//                HardwareCard(title = "RTX 3080", description = "", id = R.drawable.gpu)
//
//            }
//
//        }
//        Column() {
//            Text(text = "rarw")
//        }
//    }
//}

@Composable
fun ServerContentAhh(serverViewModel: ServerViewModel){
    val serversByOwner by serverViewModel.getAllServers().collectAsState(initial = emptyList())
    serversByOwner.forEach {
        server -> println(server.name)
    }
    Column(modifier = Modifier
//        .verticalScroll(rememberScrollState())
        .fillMaxSize()) {
        OverviewAhh()
        ServerDetailsAhh(items = listOf(
            ManageServerItem(
                id = "server_name",
                title = "Name",
                contentDescription = "Contact support",
                buttonText = "",
                value = "adogegaj.com"
            ),
            ManageServerItem(
                id = "server_type",
                title = "Type",
                contentDescription = "Settings",
                buttonText = "",
                value = "Web Server"
            ),
            ManageServerItem(
                id = "server_os",
                title = "OS",
                contentDescription = "Phone number",
                buttonText = "",
                value = "Windows Server 2016"
            ),
            ManageServerItem(
                id = "server_status",
                title = "Status",
                contentDescription = "Phone number",
                buttonText = "",
                value = "Active"
            ),
            ManageServerItem(
                id = "server_firewall",
                title = "Firewall",
                contentDescription = "Phone number",
                buttonText = "",
                value = "Enabled"
            ),
            ManageServerItem(
                id = "server_dns_type",
                title = "DNS",
                contentDescription = "Phone number",
                buttonText = "",
                value = "Dynamic"
            ),
            ManageServerItem(
                id = "server_ip",
                title = "IP",
                contentDescription = "Phone number",
                buttonText = "",
                value = "168.38.11.38"
            ),
            ManageServerItem(
                id = "cpu_usage",
                title = "CPU usage",
                contentDescription = "Phone number",
                buttonText = "",
                value = ""
            ),
            ManageServerItem(
                id = "ram_usage",
                title = "RAM usage",
                contentDescription = "Phone number",
                buttonText = "",
                value = ""
            ),
            ManageServerItem(
                id = "storage_usage",
                title = "Disk usage",
                contentDescription = "Phone number",
                buttonText = "",
                value = ""
            ),
            ManageServerItem(
                id = "gpu_usage",
                title = "GPU usage",
                contentDescription = "Phone number",
                buttonText = "",
                value = ""
            )
        ),
            onItemCLick = {/*Ovdje do some shit kad klik za it.nesto*/}
        )
        ActionsAhh()
    }
}
@Composable
fun OverviewAhh(){
    Text(text = "Overview", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(10.dp), color = Color.Gray)
    Divider(Modifier.padding(horizontal = 10.dp))
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.Center) {
            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                HardwareCardAhh(id = R.drawable.cpu, title = "Xeon", description = "CPU type")
            }
            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {

                HardwareCardAhh(id = R.drawable.ram, title = "DDR4", description = "RAM type")
            }
            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {

                HardwareCardAhh(id = R.drawable.storage, title = "Samsung 980 Pro", description = "Storage type")
            }
            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {

                HardwareCardAhh(id = R.drawable.gpu, title = "RTX 3080 ti", description = "GPU type")
            }
        }
    }
}
@Composable
fun ServerDetailsAhh(items: List<ManageServerItem>, onItemCLick: (ManageServerItem) -> Unit){
    val cpuUsage = remember { mutableStateOf("") }
    val ramUsage = remember { mutableStateOf("") }
    val storageUsage = remember { mutableStateOf("") }
    val gpuUsage = remember { mutableStateOf("") }

    DisposableEffect(Unit) {
        val timer = Timer()
        timer.scheduleAtFixedRate(0L, 1000L) {
            val randomCpu = (0..100).random()
            val randomRam = (0..100).random()
            val randomDisk = (0..100).random()
            val randomGpu = (0..100).random()
            cpuUsage.value = "$randomCpu%"
            ramUsage.value = "$randomRam%"
            storageUsage.value = "$randomDisk%"
            gpuUsage.value = "$randomGpu%"
        }

        onDispose {
            timer.cancel()
        }
    }

    Text(text = "Details", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(10.dp), color = Color.Gray)
    Divider(Modifier.padding(horizontal = 10.dp))
    //OVDJE PITATI NAIDU KAKO DOCKOVATI STVARI DA SE NE MICU
    Box(Modifier.height(320.dp)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())) {
            Column() {
                items.forEach {item->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
    //                        Icon(imageVector = Icons.Default.Stop, contentDescription = item.contentDescription, tint = Color.Gray)
                            Text(
                                text = item.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                                color = Color.Gray
                            )
                        }
                        Row() {
                            Text(
                                text = when (item.id) {
                                    "cpu_usage" -> cpuUsage.value
                                    "ram_usage" -> ramUsage.value
                                    "storage_usage" -> storageUsage.value
                                    "gpu_usage" -> gpuUsage.value
                                    else -> item.value
                                },
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
            // Ovdje ostala dugmad
        }
    }
}
@Composable
fun ActionsAhh(){
    Text(text = "Actions", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(10.dp), color = Color.Gray)
    Divider(Modifier.padding(horizontal = 10.dp))
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /* Start button onClick logic */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(49,62,79)),
                modifier = Modifier
                    .weight(1f)
                    .padding(6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Start", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Normal)
                }
            }

            Button(
                onClick = { /* Stop button onClick logic */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(89,136,154)),
                modifier = Modifier
                    .weight(1f)
                    .padding(6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Stop,
                        contentDescription = "Stop",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Stop", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Normal)
                }
            }

            Button(
                onClick = { /* Reboot button onClick logic */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(89,136,154)),
                modifier = Modifier
                    .weight(1f)
                    .padding(6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reload",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Reboot", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Normal)
                }
            }
        }
    }
}

@Composable
fun HardwareCardAhh(@DrawableRes id: Int, modifier: Modifier = Modifier, title: String, description: String){
    val image = painterResource(id = id)
    Column(modifier = modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = image, contentDescription = description, modifier = Modifier
            .size(60.dp)
            .clip(RectangleShape))
        Text(text = title, softWrap = true, maxLines = 3, modifier = modifier.padding(top = 8.dp), overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center, fontSize = 15.sp)

    }
}
@Preview(showBackground = true)
@Composable
fun ServerContentAhhPreview() {
    RSMTempTheme {
//        ServerContentAhh()
    }
}