package com.underground.threadsclone.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.underground.threadsclone.item_view.ThreadItem
import com.underground.threadsclone.item_view.UserItem
import com.underground.threadsclone.viewModel.HomeViewModel
import com.underground.threadsclone.viewModel.SearchViewModel

@Composable
fun Search(navHostController:NavHostController){

    val searchViewModel: SearchViewModel = viewModel()
    val fetchThreadAndUsers by searchViewModel.users.observeAsState()
    val context = LocalContext.current

    Column {
        LazyColumn {
            items(fetchThreadAndUsers ?: emptyList()) { pairs ->
                UserItem(
                    users = pairs,
                    navHostController = navHostController
                )
            }
        }
    }
    
}