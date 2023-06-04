package com.example.rsmtemp

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CrisisAlert
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rsmtemp.data.Alert
import com.example.rsmtemp.data.User
import com.example.rsmtemp.ui.theme.RSMTempTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AlertScreen {
}
@Composable
fun AlertScreenContent(modifier: Modifier = Modifier, alertViewModel: AlertViewModel, serverViewModel: ServerViewModel, user: User) {

    val servers by serverViewModel.getAllByOwnerId(user.id!!).collectAsState(emptyList())
    val alerts by alertViewModel.getAllAlerts().collectAsState(initial = emptyList())
    var allAlerts: List<Alert> by remember { mutableStateOf(emptyList()) }

//    LaunchedEffect(servers){
//        CoroutineScope(Dispatchers.Main).launch {
//            var counter: Int = -1
//            servers.forEachIndexed { index, server ->
////                val alert: List<Alert> = alertViewModel.getAllAlertsByServerId(server.id).first()
//                allAlerts += alert
//                counter += 1
//
//            }
//        }
//    }
    LaunchedEffect(servers, alerts) {
        val tempAlerts = mutableListOf<Alert>()

        servers.forEach { server ->
            val alertsForServer = alertViewModel.getAllAlertsByServerId(server.id!!).first()

            // Compare items from `alertsForServer` and `alerts` lists and perform any required operations

            // Example comparison: Find alerts that have the same ID in both lists
            val commonAlerts = alertsForServer.filter { alert ->
                alerts.any { it.id == alert.id }
            }

            // Accumulate the common alerts in the temporary list
            tempAlerts.addAll(commonAlerts)
        }

        // Update the `allAlerts` list outside the effect
        allAlerts = tempAlerts
    }


//    val alertsByServerId = alertViewModel.getAllAlertsByServerId(counter).collectAsState(initial = emptyList()).value

    Column(modifier = modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())

        ) {
            Column(verticalArrangement = Arrangement.Center) {
                allAlerts.forEach { alert ->
                    AlertScreenCard(errorType = alert.type, serverName = alert.server_name, errorDetails = alert.description, alertViewModel = alertViewModel, alert = alert)
                }
            }
        }
    }
}

@Composable
fun AlertScreenCard(serverName: String, errorType: String, errorDetails: String, modifier: Modifier = Modifier, alertViewModel: AlertViewModel, alert: Alert){
    var showMenu by remember { mutableStateOf(false) }
    val successIcon : Painter = painterResource(id = R.drawable.success)
    val attentionIcon : Painter = painterResource(id = R.drawable.attention)
    val warningIcon : Painter= painterResource(id = R.drawable.warning)
    var image by remember { mutableStateOf(successIcon) }


    image = when(errorType){
        "success" -> successIcon
        "attention" -> attentionIcon
        "warning" -> warningIcon
        else -> {successIcon}
    }
    val alertText = when(errorType){
        "success" -> "Success"
        "attention" -> "Attention"
        "warning" -> "Warning"
        else -> {"Unknown Error"}
    }
    val description = when(image){
        successIcon -> "success icon"
        attentionIcon -> "attention icon"
        warningIcon -> "wanring icon"
        else -> {""}
    }


    Card(backgroundColor = Color.White, modifier = Modifier.fillMaxWidth()){
        Divider(Modifier.padding(horizontal = 10.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(modifier.padding(10.dp)) {
                    Image(painter = image, contentDescription = description,
                        modifier
                            .size(20.dp)
                            )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column() {
                        Text(text = alertText,  modifier = modifier.padding(start = 5.dp), fontWeight = FontWeight.Medium)
                        Text(text = "Server: $serverName", modifier = modifier.padding(start = 5.dp), fontSize = 14.sp, color = Color.Gray)
                        Text(text = errorDetails, modifier = modifier.padding(start = 5.dp), fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch{
                            try {
                                alertViewModel.deleteAlert(alert)
                                }
                            catch (e: Exception){
                                println("Nazalost nije deletano")
                            }
                        }
                    }
                ) {
                    Icon(Icons.Outlined.Close, contentDescription = "Delete notification", tint = Color.Gray)

                }

            }

        }
        Divider(Modifier.padding(horizontal = 10.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun AlertContentPreview() {
    RSMTempTheme {
//        AlertScreenContent()
    }
}