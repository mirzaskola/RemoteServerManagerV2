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
import androidx.compose.runtime.setValue
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
import com.example.rsmtemp.data.Server
import com.example.rsmtemp.ui.theme.RSMTempTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.reflect.typeOf


@Composable
fun ServerContent(serverId: String?, serverViewModel: ServerViewModel){
    var id = serverId!!.toInt()
    val defaultServer: Server = Server(1, "","",1, "", "", "", "", "", "", "", "",
        status = false,
        firewall = true,
        ip = "",
        dns = ""
    )

//    val allServers by serverViewModel.getAllServers().collectAsState(emptyList())
    val server by serverViewModel.getOneById(id).collectAsState(initial = defaultServer)


//    allServers.forEach { serverFromList ->
//        println(serverFromList)
//        if(serverFromList.id == id){
//            tempServer = serverFromList
//        }
//    }

    var status by remember { mutableStateOf(server.status) }
    var firewall by remember { mutableStateOf(server.firewall) }

    var stringStatus by remember { mutableStateOf("") }
    var stringFirewall by remember { mutableStateOf("") }

    status = server.status
    firewall = server.firewall

    stringStatus = if (status) {
        "Active"
    } else {
        "Inactive"
    }

    stringFirewall = if (firewall) {
        "Enabled"
    } else {
        "Disabled"
    }

    Column(modifier = Modifier
//        .verticalScroll(rememberScrollState())
        .fillMaxSize()) {
        Overview(server.cpu, server.ram, server.storage, server.gpu)
        ServerDetails(items = listOf(
            ManageServerItem(
                id = "server_name",
                title = "Name",
                contentDescription = "Contact support",
                buttonText = "",
                value = server.name
            ),
            ManageServerItem(
                id = "server_type",
                title = "Type",
                contentDescription = "Settings",
                buttonText = "",
                value = server.type
            ),
            ManageServerItem(
                id = "server_os",
                title = "OS",
                contentDescription = "Phone number",
                buttonText = "",
                value = server.os
            ),
            ManageServerItem(
                id = "server_status",
                title = "Status",
                contentDescription = "Phone number",
                buttonText = "",
                value = stringStatus
            ),
            ManageServerItem(
                id = "server_firewall",
                title = "Firewall",
                contentDescription = "Phone number",
                buttonText = "",
                value = stringFirewall
            ),
            ManageServerItem(
                id = "server_dns_type",
                title = "DNS",
                contentDescription = "Phone number",
                buttonText = "",
                value = server.dns
            ),
            ManageServerItem(
                id = "server_ip",
                title = "IP",
                contentDescription = "Phone number",
                buttonText = "",
                value = server.ip
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
            status = status,
            onItemCLick = {/*Ovdje do some shit kad klik za it.nesto*/}
        )
        Actions(server, serverViewModel, onChangeActiveState = { status = it })
    }
}
@Composable
fun Overview(cpu: String, ram: String, storage: String, gpu: String){
    Text(text = "Overview", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(10.dp), color = Color.Gray)
    Divider(Modifier.padding(horizontal = 10.dp))
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), horizontalArrangement = Arrangement.Center) {
            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                HardwareCard(id = R.drawable.cpu, title = cpu, description = "CPU type")
            }
            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {

                HardwareCard(id = R.drawable.ram, title = ram, description = "RAM type")
            }
            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {

                HardwareCard(id = R.drawable.storage, title = storage, description = "Storage type")
            }
            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {

                HardwareCard(id = R.drawable.gpu, title = gpu, description = "GPU type")
            }
        }
    }
}
@Composable
fun ServerDetails(items: List<ManageServerItem>, onItemCLick: (ManageServerItem) -> Unit, status: Boolean){
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
                            if(status == true){
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
                            else{
                                Text(
                                    text = when (item.id) {
                                        "cpu_usage" -> "0%"
                                        "ram_usage" -> "0%"
                                        "storage_usage" -> "0%"
                                        "gpu_usage" -> "0%"
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
            }
            // Ovdje ostala dugmad
        }
    }
}
@Composable
fun Actions(server: Server, serverViewModel: ServerViewModel, onChangeActiveState: (Boolean) -> Unit){
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
                onClick = { CoroutineScope(Dispatchers.Main).launch {
                    server.status = true
                    serverViewModel.upsertServer(server = server)
                    onChangeActiveState(true)
                }},
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
                onClick = { CoroutineScope(Dispatchers.Main).launch {
                    server.status = false
                    serverViewModel.upsertServer(server = server)
                    onChangeActiveState(false)
                }},
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
fun HardwareCard(@DrawableRes id: Int, modifier: Modifier = Modifier, title: String, description: String){
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
fun ServerContentPreview() {
    RSMTempTheme {
//        ServerContent()
    }
}