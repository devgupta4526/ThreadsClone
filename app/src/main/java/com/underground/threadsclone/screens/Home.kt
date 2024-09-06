package com.underground.threadsclone.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.underground.threadsclone.item_view.ThreadItem
import com.underground.threadsclone.item_view.ThreadItemView
import com.underground.threadsclone.viewModel.HomeViewModel

//@Composable
//fun Home(navHostController: NavHostController) {
//
//    val homeViewModel: HomeViewModel = viewModel()
//    val fetchThreadAndUsers by homeViewModel.threadsAndUsers.observeAsState()
//    val context = LocalContext.current
//
//    LazyColumn {
//        items(fetchThreadAndUsers ?: emptyList()) { pairs ->
//            ThreadItem(
//                thread = pairs.first,
//                users = pairs.second,
//                navHostController = navHostController,
//                userId = FirebaseAuth.getInstance().currentUser!!.uid
//            )
//        }
//    }
//
//}

@Composable
fun Home(navHostController: NavHostController){

    val context = LocalContext.current
    val homeViewModel:HomeViewModel = viewModel()
    val threadsAndUsers by homeViewModel.threadsAndUsers.observeAsState(null)



    LazyColumn {
        items(threadsAndUsers ?: emptyList()){pairs ->
            ThreadItem(
                thread = pairs.first,
                users = pairs.second,
                navHostController,
                FirebaseAuth.getInstance().currentUser!!.uid)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeView() {
//    Home()
}