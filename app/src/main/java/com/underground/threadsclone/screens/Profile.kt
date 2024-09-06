package com.underground.threadsclone.screens

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.underground.threadsclone.navigation.Routes
import com.underground.threadsclone.viewModel.AuthViewModel

@Composable
fun Profile(navHostController:NavHostController){

     val authViewModel:AuthViewModel = viewModel()
     val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    LaunchedEffect(firebaseUser){
        if(firebaseUser == null){
            navHostController.navigate(Routes.Login.route) {
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    Text(text = "Profile", modifier = Modifier.clickable {
            authViewModel.logout()
    })

}