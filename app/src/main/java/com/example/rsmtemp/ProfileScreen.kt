package com.example.rsmtemp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.rsmtemp.data.User
import com.example.rsmtemp.ui.theme.RSMTempTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileScreen {
}
@Composable
fun ProfileScreenContent(navController: NavController, userViewModel: UserViewModel, user: User) {
    val defaultUser = User(1, "", "", "", "")
    val loggedInUser: User = userViewModel.getUserById(user.id!!).collectAsState(initial = defaultUser).value
//    var user by remember { mutableStateOf<User?>(userState.value) }
//
//    if (user == null) {
//        user = userState.value
//    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)

    ) {
        ProfileScreenHeader(loggedInUser, userViewModel = userViewModel)
        AccountDetails(items = listOf(ProfileItem(
                                    id = "user_email",
                                    title = "Email",
                                    contentDescription = "Contact support",
                                    icon = Icons.Outlined.Email,
                                    buttonText = "",
                                    value = loggedInUser.email
                                ),
                                ProfileItem(
                                    id = "username",
                                    title = "Username",
                                    contentDescription = "Settings",
                                    icon = Icons.Outlined.Person,
                                    buttonText = "",
                                    value = loggedInUser.username
                                ),
                                ProfileItem(
                                    id = "phone",
                                    title = "Phone number",
                                    contentDescription = "Phone number",
                                    icon = Icons.Default.Phone,
                                    buttonText = "",
                                    value = loggedInUser.phone
                                )
                ),
                onItemCLick = {/*Ovdje do some shit kad klik za it.nesto*/}

        )
        SettingsDetails(items = listOf(ProfileItem(
                                        id = "change_password",
                                        title = "Password",
                                        contentDescription = "Contact support",
                                        icon = Icons.Outlined.Shield,
                                        buttonText = "",
                                        value = "Change"
                                    ),
                                        ProfileItem(
                                            id = "language",
                                            title = "Language",
                                            contentDescription = "Settings button",
                                            icon = Icons.Default.Language,
                                            buttonText = "",
                                            value = "English"
                                        ),
                                        ProfileItem(
                                            id = "devices",
                                            title = "Devices",
                                            contentDescription = "Settings button",
                                            icon = Icons.Default.Computer,
                                            buttonText = "",
                                            value = "Manage"
                                        )
                                    ),
                onItemCLick = {/*Ovdje do some shit kad klik za it.nesto*/navController.navigate(Screen.DashboardScreen.route)})
        HelpDetails(items = listOf(ProfileItem(
                                        id = "contact_support",
                                        title = "Contact Support",
                                        contentDescription = "Contact support",
                                        icon = Icons.Outlined.QuestionAnswer,
                                        buttonText = "",
                                        value = ""
                                    )

                ),
                onItemCLick = {/*Ovdje do some shit kad klik za it.nesto*/navController.navigate(Screen.DashboardScreen.route)}
        )
        LoginDetails(navController = navController)


//        Text(text = "Settings", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(vertical = 10.dp), color = Color.Gray)
//        Divider()
//        ProfileScreenDetails()
//        Spacer(modifier = Modifier.height(16.dp))
//        Text(text = "Help", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(vertical = 10.dp), color = Color.Gray)
//        Divider()
//        ProfileScreenDetails()
//        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun ProfileScreenHeader(user: User, userViewModel: UserViewModel) {
    val showDialog = remember { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 10.dp)
        ) {
                Image(
                    painter = painterResource(R.drawable.profile_picture),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column() {
                    user.username?.let {
                        Text(
                            text = it,
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                        )
                    }
                    Text(
                        text = "Nezz sta ovdje da stavim",
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Gray)
                    )
                }
            }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 10.dp)) {
            Button(
                onClick = { showDialog.value = true },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(89,136,154)),
                modifier = Modifier
                    .padding(6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Edit profile", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Normal)
                }
            }
        }
    }
    if(showDialog.value){
        EditProfileDialog(userViewModel = userViewModel, user = user,
            onConfirmClick = {  ->
                // Handle the entered values
                showDialog.value = false // Close the dialog
            },
            onDismissClick = {
                showDialog.value = false // Close the dialog
            }
        )
    }
}

@Composable
fun AccountDetails(items: List<ProfileItem>, modifier: Modifier = Modifier, onItemCLick: (ProfileItem) -> Unit) {
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = "Account", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(vertical = 10.dp), color = Color.Gray)
    Divider()
    LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        items(items){item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = item.icon, contentDescription = item.contentDescription, tint = Color.Gray)
                    Text(
                        text = item.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                        color = Color.Gray
                    )
                }
                Row {
                    ClickableText(
                        text = buildAnnotatedString { append(item.value)  },
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color.Gray),
//                                text = item.value,
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.Normal,
//                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
//                                color = Color.Gray

                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            onItemCLick(item)
                        }
                    )
                }
            }

        }
        // Ovdje ostala dugmad
    }
}
@Composable
fun SettingsDetails(items: List<ProfileItem>, modifier: Modifier = Modifier, onItemCLick: (ProfileItem) -> Unit) {
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = "Settings", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(vertical = 10.dp), color = Color.Gray)
    Divider()
    LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        items(items){item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = item.icon, contentDescription = item.contentDescription, tint = Color.Gray)
                    Text(
                        text = item.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                        color = Color.Gray
                    )
                }
//                Row() {
//                    Text(
//                        text = item.value,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
//                        color = Color.Gray
//                    )
//                }
                Row {
                    ClickableText(
                        // Ovo je android studio sam generisao da rijesi nullable string
                        text = buildAnnotatedString { append(item.value) },
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray),
//                                text = item.value,
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.Normal,
//                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
//                                color = Color.Gray

                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            onItemCLick(item)
                        }
                    )
                }
            }

        }
        // Ovdje ostala dugmad
    }
}
@Composable
fun LoginDetails(navController: NavController) {
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = "Login", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(vertical = 10.dp), color = Color.Gray)
    Divider()
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(Screen.LoginScreen.route) },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Switch account",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.Gray
                    )
                }
                Row() {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = Color.Gray
//                        tint = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                    )
                }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate(Screen.LoginScreen.route) },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout button", tint = Color.Gray)
                    Text(
                        text = "Log out",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.Black
                    )
                }
                Row() {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = Color.Gray
//                        tint = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                    )
                }
        }
    }
}

@Composable
fun HelpDetails(items: List<ProfileItem>, modifier: Modifier = Modifier, onItemCLick: (ProfileItem) -> Unit) {
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = "Help", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(vertical = 10.dp), color = Color.Gray)
    Divider()
    LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        items(items){item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = item.icon, contentDescription = item.contentDescription, tint = Color.Gray)
                    Text(
                        text = item.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                        color = Color.Gray
                    )
                }
                Row() {
                    item.value?.let {
                        Text(
                            text = it,
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


@Composable
fun EditProfileDialog(
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    user: User,
    userViewModel: UserViewModel
) {
    val emailState = remember { mutableStateOf("") }
    val usernameState = remember { mutableStateOf("") }
    val phoneNumberState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    emailState.value = user.email
    usernameState.value = user.username
    phoneNumberState.value = user.phone
    passwordState.value = user.password

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
                Text(
                    text = "Edit profile info",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )
                TextField(
                    shape = RoundedCornerShape(10.dp),
                    value = emailState.value,
                    onValueChange = { emailState.value = it },
                    label = { Text(text = "Email", fontSize = 14.sp) },
                    modifier = Modifier.padding(horizontal = 16.dp),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    shape = RoundedCornerShape(10.dp),
                    value = usernameState.value,
                    onValueChange = { usernameState.value = it },
                    label = { Text(text = "Username", fontSize = 14.sp) },
                    modifier = Modifier.padding(horizontal = 16.dp),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    shape = RoundedCornerShape(10.dp),
                    value = phoneNumberState.value,
                    onValueChange = { phoneNumberState.value = it },
                    label = { Text(text="Phone Number", fontSize = 14.sp) },
                    modifier = Modifier.padding(horizontal = 16.dp),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    shape = RoundedCornerShape(10.dp),
                    value = passwordState.value,
                    onValueChange = { passwordState.value = it },
                    label = { Text(text = "New password", fontSize = 14.sp) },
                    modifier = Modifier.padding(horizontal = 16.dp),
                    maxLines = 1
                )
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
                                    user.username = usernameState.value
                                    user.password = passwordState.value
                                    user.email = emailState.value
                                    user.phone = phoneNumberState.value

                                    userViewModel.upsertUser(user)
                                    onConfirmClick()
                                }catch (e: Exception){
                                    println("splovilo")
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(49,62,79)),
                        enabled = emailState.value.isNotBlank() &&
                                usernameState.value.isNotBlank() &&
                                phoneNumberState.value.isNotBlank()
                    ) {
                        Text("Confirm", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Normal)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onDismissClick,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(89,136,154)),
                    ) {
                        Text(text = "Cancel", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Normal)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileItem() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Outlined.Message, contentDescription = null, tint = Color.Gray, modifier = Modifier.padding(top = 2.dp))
            Text(
                text = "Status",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                color = Color.Gray
            )
        }
        Row() {
            Text(
                text = "Status",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                color = Color.Gray
            )
        }
    }
}









//@Composable
//fun LogoutButton(onLogoutClick: () -> Unit) {
//    Button(
//        onClick = onLogoutClick,
//        modifier = Modifier.fillMaxWidth(),
//        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
//        elevation = ButtonDefaults.elevation(0.dp),
//        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(
//                text = "Log Out",
//                fontWeight = FontWeight.Bold
//            )
//            Icon(
//                imageVector = Icons.Default.ChevronRight,
//                contentDescription = null,
//                tint = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
//            )
//        }
//    }
//}

@Composable
fun EditProfileButton(onEditButtonClick: () -> Unit) {
    Button(
        onClick = onEditButtonClick ,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Edit Profile")
    }
}
@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {
    RSMTempTheme {
//        ProfileScreenHeader()
    }
}