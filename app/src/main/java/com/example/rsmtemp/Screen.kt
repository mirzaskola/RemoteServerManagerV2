package com.example.rsmtemp

//sealed class Screen(@StringRes val route: Int)
open class Screen(val route: String){
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }
    //BOTTOM NAV DUGMAD
    object DashboardScreen : Screen(route = "dashboard")
    object AlertScreen : Screen(route = "alerts")
    object ProfileScreen : Screen(route = "my_profile")
    // DUGMAD ZA MANAGE
    object ManageScreen : Screen(route = "manage_servers")
    // LOGIN SCREEN RUTA
    object LoginScreen : Screen(route = "login")




//    object MainScreen : Screen(route = R.string.title_activity_main_screen)
//    object ManageScreen : Screen(route = R.string.title_activity_server)
//    object AlertScreen : Screen(route = R.string.title_activity_alerts)
//    object ProfileScreen : Screen(route = R.string.title_activity_profile)
//    object LoginScreen : Screen(route = R.string.title_activity_login)
}
