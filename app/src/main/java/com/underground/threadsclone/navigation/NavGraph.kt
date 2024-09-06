package com.underground.threadsclone.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.underground.threadsclone.screens.AddThread
import com.underground.threadsclone.screens.BottomNav
import com.underground.threadsclone.screens.Home
import com.underground.threadsclone.screens.Login
import com.underground.threadsclone.screens.Notification
import com.underground.threadsclone.screens.Profile
import com.underground.threadsclone.screens.Register
import com.underground.threadsclone.screens.Search
import com.underground.threadsclone.screens.Splash


@Composable
fun NavGraph(navController: NavHostController){

    NavHost(navController = navController, startDestination = Routes.Splash.route){

        composable(Routes.Splash.route){
            Splash(navController)
        }
        composable(Routes.Notification.route){
            Notification()
        }

        composable(Routes.Home.route){
            Home(navController)
        }

        composable(Routes.Search.route){
            Search(navController)
        }

        composable(Routes.Profile.route){
            Profile(navController)
        }
        composable(Routes.AddThreads.route){
            AddThread(navController)
        }

        composable(Routes.Login.route){
            Login(navController)
        }
        composable(Routes.Register.route){
            Register(navController)
        }
        
        composable(Routes.BottomNav.route){
            BottomNav(navController = navController)
        }
    }

}