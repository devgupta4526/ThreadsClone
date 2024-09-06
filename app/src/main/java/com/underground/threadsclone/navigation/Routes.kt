package com.underground.threadsclone.navigation

sealed class Routes(val route : String){

    object Home : Routes("home")
    object Notification: Routes("notification")
    object Profile : Routes("profile")
    object Search :  Routes("search")
    object Splash : Routes("splash")
    object AddThreads : Routes("addThreads")
    object BottomNav : Routes("bottomNav")
    object Login : Routes("login")
    object Register : Routes("register")
}
