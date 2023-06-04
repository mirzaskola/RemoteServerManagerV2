package com.example.rsmtemp

import androidx.annotation.StringRes
// Ovo je sluzilo ko proba za navigaciju
enum class NavigationUrls(val title: String) {
//    Home(title = R.string.title_activity_main_screen),
//    Manage(title = R.string.title_activity_server),
//    Profile(title = R.string.title_activity_profile),
//    Login(title = R.string.title_activity_login),
//    Alert(title = R.string.title_activity_alerts)
    DashboardScreen(title = "dashboard"),
    ManageScreen(title = "manage_servers"),
    ProfileScreen(title = "alerts"),
    LoginScreen(title = "login"),
    AlertScreen(title = "alerts")
}