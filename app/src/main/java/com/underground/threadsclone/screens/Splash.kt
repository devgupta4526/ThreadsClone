package com.underground.threadsclone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.underground.threadsclone.R
import com.underground.threadsclone.navigation.Routes
import kotlinx.coroutines.delay


@Composable
fun Splash(navHostController: NavHostController) {


    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

        val (image) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier.size(32.dp).constrainAs(image){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

            }
        )


    }
    LaunchedEffect(true) {
        delay(3000)

        if(FirebaseAuth.getInstance().currentUser!= null)
            navHostController.navigate(Routes.BottomNav.route){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        else
            navHostController.navigate(Routes.Login.route){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
    }


}